package com.ye2moe.io.task;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.ye2moe.io.bean.TaskBean;
import com.ye2moe.io.util.extract.AbstractExtract;
/**
 * 解压工作
 * @author ye2moe
 *
 */
public class ExtractTask extends Task {

	public ExtractTask(List<TaskBean> beans, CountDownLatch countDownLatch) {
		super(beans, countDownLatch); 
	}

	public void run() {
		String[] fs;
		for (TaskBean bean : beans) {
			fs = bean.getFilePaths(); 
			AbstractExtract.extract(new File(fs[0]),fs[1]);
		}
		countDownLatch.countDown();
	}

}
