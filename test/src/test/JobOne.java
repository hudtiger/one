package test;

import java.util.ArrayList;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import lombok.Data;

public class JobOne implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();

		// JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		JobDataMap dataMap = context.getMergedJobDataMap();
		String data = dataMap.getString("data");
		System.err.println("Instance " + key + " data: " + data);

	}
}

/*
 * injecting注入
 */
//@Data
//public class JobOne implements Job {
//	String jobSays;
//    float myFloatValue;
//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		JobKey key = context.getJobDetail().getKey();
//
//		// JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//		JobDataMap dataMap = context.getMergedJobDataMap();
//		System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
//	}
//}