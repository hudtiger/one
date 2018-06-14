package com.gls.demo.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss"); // 定义每过3秒执行任务

	@Scheduled(fixedRate = 3000)
	public void reportCurrentTime() {
		System.out.println("每隔3秒执行一次, 现在时间：" + simpleDateFormat.format(new Date()));
	}

	@Scheduled(cron = "0 33 22 ? * *") // 每天的晚上十点33分执行一次
	public void fixTimeExecution() {
		System.out.println("在指定时间内执行一次： " + simpleDateFormat.format(new Date()) + "执行");
	}

}
