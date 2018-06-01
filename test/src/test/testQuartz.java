package test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import lombok.AllArgsConstructor;
import lombok.Data;

public class testQuartz {
	public static void main(String[] args) {
		TaskRepository.tasks.forEach(task -> TaskRepository.startTask(task));
	}
}

interface IDoneInterval {
	SimpleScheduleBuilder getBuilder(SimpleScheduleBuilder builder, int intervalInSeconds);
}

interface IDonePoint {
	CronScheduleBuilder getBuilder(String[] parms);
}

class TaskRepository {
	static List<TimerTask> tasks = null;
	static Map<String, IntervalUnit> startInterval = null;
	static Map<String, IDoneInterval> doneInterval = null;
	static Map<Integer, IDonePoint> donePoint = null;
	static {
		if (tasks == null) {
			tasks = new ArrayList<>();
			tasks.add(new TimerTask("task1", "gls", "now", null, -1, "5 s", 1, "test.JobOne", "{'name':'Micheal'}","")); // 立即执行
			tasks.add(new TimerTask("task2_3秒后执行", "gls", "3 s", null, -1, "5 s", -1, "test.JobOne",
					"{'name':'Micheal'}","")); // 5秒后执行,forever
			tasks.add(new TimerTask("task3_2018-05-25 9:00开始", "gls", "2018-05-25 9:00:00", null, -1, "5 s", -1, "test.JobOne",
					"{'name':'Micheal'}","")); // 2018-05-25 9:00:00执行,forever
		}
		if (startInterval == null) {
			startInterval = new HashMap<>();
			startInterval.put("ms", IntervalUnit.MILLISECOND);
			startInterval.put("s", IntervalUnit.SECOND);
			startInterval.put("m", IntervalUnit.MINUTE);
			startInterval.put("h", IntervalUnit.HOUR);
			startInterval.put("d", IntervalUnit.DAY);
			startInterval.put("w", IntervalUnit.WEEK);
			startInterval.put("M", IntervalUnit.MONTH);
		}
		if (doneInterval == null) {
			doneInterval = new HashMap<>();
			doneInterval.put("ms", (simpleSchedule, interval) -> simpleSchedule.withIntervalInMilliseconds(interval)); 
			doneInterval.put("s", (simpleSchedule, interval) -> simpleSchedule.withIntervalInSeconds(interval));//对应cronSchedule的inerval 0 0 ? * *
			doneInterval.put("m", (simpleSchedule, interval) -> simpleSchedule.withIntervalInMinutes(interval));//对应cronSchedule的0 inerval 0 ? * *
			doneInterval.put("h", (simpleSchedule, interval) -> simpleSchedule.withIntervalInHours(interval));//对应cronSchedule的0 0 inerval ? * *
		}
		if (donePoint == null) {
			//%s %s %s ? * * 分别对应 Seconds Minutes Hours Day-of-Month Month Day-of-Week Year (optional field)
			donePoint = new HashMap<>();
			donePoint.put(1,
					parms -> CronScheduleBuilder.cronSchedule(String.format("0 %s %s ? * *", parms[0], parms[1]))); // minute,hour
			donePoint.put(2, parms -> CronScheduleBuilder
					.cronSchedule(String.format("0 %s %s ? * %s", parms[0], parms[1], parms[2])));// minute,hour,daysOfWeek 如：“0 30 10-13 ? * WED,FRI”
			donePoint.put(3, parms -> CronScheduleBuilder
					.cronSchedule(String.format("0 %s %s %s * ?", parms[0], parms[1], parms[2]))); // minute,hour,daysOfMonth 如 “0 0/30 8-9 5,20 * ?”
		}
	}

	public static List<TimerTask> getTasks() {
		return tasks;
	}

	public static void startTask(TimerTask task) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			//添加joblistener
			scheduler.getListenerManager().addJobListener(new JobListener() {

				@Override
				public String getName() {
					return "Job status listener";
				}

				@Override
				public void jobToBeExecuted(JobExecutionContext context) {
					System.err.printf("Job[%s] will be executed.\r\n", context.getJobDetail().getKey());
				}

				@Override
				public void jobExecutionVetoed(JobExecutionContext context) {
					System.err.printf("Job[%s] is vetoed.\r\n", context.getJobDetail().getKey());
				}

				@Override
				public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
					System.err.printf("Job[%s] was executed.\r\n", context.getJobDetail().getKey());
				}
				
			});
//			scheduler.getListenerManager().addSchedulerListener(null);
//			scheduler.getListenerManager().addTriggerListener(null);
			scheduler.start();
			JobDetail job = JobBuilder.newJob((Class<? extends Job>) Class.forName(task.task))
					.withIdentity(task.taskName, task.taskGroup).usingJobData("data", task.data).build();

			TriggerBuilder trigger = TriggerBuilder.newTrigger().withIdentity("Trigger_".concat(task.taskName),
					"Trigger_".concat(task.taskGroup));
			// 取启动时间
			if ("now".equals(task.taskStart))
				trigger = trigger.startNow();
			else {
				String[] intervalStart = task.taskStart.split(" ");
				if (startInterval.get(intervalStart[1]) != null)
					trigger = trigger.startAt(DateBuilder.futureDate(Integer.valueOf(intervalStart[0]),
							startInterval.get(intervalStart[1])));
				else {
					SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					trigger = trigger.startAt(dateFmt.parse(task.taskStart));
				}
				
			}
			// 取执行规则
			if (task.scheduleType == -1) {
				String[] intervalSchedule = task.schedule.split(" ");
				SimpleScheduleBuilder scheduleBuilder = doneInterval.get(intervalSchedule[1])
						.getBuilder(SimpleScheduleBuilder.simpleSchedule(), Integer.valueOf(intervalSchedule[0]));
				if (task.times == -1)
					scheduleBuilder = scheduleBuilder.repeatForever();
				else
					scheduleBuilder = scheduleBuilder.withRepeatCount(task.times);
				trigger = trigger.withSchedule(scheduleBuilder);
			} else {
				trigger = trigger.withSchedule(donePoint.get(task.scheduleType).getBuilder(task.schedule.split(" ")));
			}
			//excludeDate
			if(!"".equals(task.excludeDate)){
				HolidayCalendar cal = new HolidayCalendar();
			    Arrays.asList(task.excludeDate.split(" ")).forEach(dateStr->cal.addExcludedDate(Date.valueOf(dateStr)));
				scheduler.addCalendar("holidays", cal, false, true);
				trigger = trigger.modifiedByCalendar("holidays");
			}
			
			scheduler.scheduleJob(job, trigger.build());

			// scheduler.shutdown();
		} catch (SchedulerException | ClassNotFoundException | ParseException se) {
			se.printStackTrace();
		}
	}
}

@Data
@AllArgsConstructor
class TimerTask {
	String taskName;
	String taskGroup;

	String taskStart; // 现在、多久以后(MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR)、某个时间点
	String taskEnd;

	Integer scheduleType; // Interval -1; Daily 0; DayOfWeek 1;DayOfMonth 2
	String schedule;

	Integer times;
	String task;
	String data;
	
	String excludeDate;
}
