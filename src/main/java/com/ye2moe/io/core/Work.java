package com.ye2moe.io.core;

import org.apache.log4j.Logger;

import com.ye2moe.io.task.Task;

public abstract class Work {
	private static Logger logger = Logger.getLogger(Work.class);
	protected int taskCount;

	public Work() {
		this.taskCount = 1;
	}

	public Work setMaxTaskNum(int taskCount) {
		this.taskCount = taskCount;
		return this;
	}

	public void start() {
		scan();
		distrbute();
		work();
	}

	protected abstract void scan();

	protected abstract void distrbute();

	protected abstract void work();

	protected void dispatchWork(final Task task, int workerIndex) {
		Thread workerThread = new Thread(task);
		workerThread.setName(task.getClass().getName() + ":" + workerIndex);
		workerThread.start();
		logger.info(workerThread.getName() + ":开始工作！");
	}
}
