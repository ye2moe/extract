package com.ye2moe.io.task;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.ye2moe.io.bean.TaskBean;

/**
 * 具体执行任务类
 * @author ye2moe
 *
 */
public abstract class Task implements Runnable {

	public Task(List<TaskBean> beans, CountDownLatch countDownLatch) {
		this.beans = beans;
		this.countDownLatch = countDownLatch;
	}
	
	public void sort() {
		//
	}

	protected List<TaskBean> beans;
	protected CountDownLatch countDownLatch;
}
