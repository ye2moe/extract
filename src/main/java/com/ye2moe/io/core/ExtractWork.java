package com.ye2moe.io.core;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.ye2moe.io.bean.TaskBean;
import com.ye2moe.io.distribute.Distribute;
import com.ye2moe.io.distribute.extract.CountAverageDistribute;
import com.ye2moe.io.task.ExtractTask;
import com.ye2moe.io.task.Task;
import com.ye2moe.io.util.scan.ExtractScanCondition;
import com.ye2moe.io.util.scan.Scan;

/**
 * 
 * @author ye2moe
 *
 */
public class ExtractWork extends Work {

	protected CountDownLatch countDownLatch;
	protected Scan scan;
	protected Task task;
	protected Distribute distribute;
	protected List<List<TaskBean>> taskBeans;
	protected List<TaskBean> scans;
	private static Logger logger = Logger.getLogger(ExtractWork.class);

	public ExtractWork(String path) {
		super();
		scan = new Scan(path, new ExtractScanCondition());
		distribute = new CountAverageDistribute();
	}

	/**
	 * 扫描文件夹
	 */
	@Override
	protected void scan() {
		scans = scan.scan();
	}

	/**
	 * 分配任务
	 */
	@Override
	protected void distrbute() {
		taskBeans = distribute.allot(scans,taskCount);
		countDownLatch = new CountDownLatch(taskCount);
	}

	/**
	 * 分配工作
	 */
	@Override
	protected void work() {
		int workIndex = 1;
		for (List<TaskBean> tb : taskBeans) {
			task = new ExtractTask(tb, countDownLatch);
			dispatchWork(task, workIndex++);
		} 
	}

}
