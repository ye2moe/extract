package com.ye2moe.io.distribute.extract;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ye2moe.io.bean.TaskBean;
import com.ye2moe.io.distribute.Distribute;

public class CountAverageDistribute implements Distribute {

	private static Logger logger = Logger.getLogger(CountAverageDistribute.class);

	public List<List<TaskBean>> allot(List<TaskBean> extractFiles, int maxNum) {
		logger.info("allot");
		int chunkSizePerThread = extractFiles.size() / maxNum;
		int lowerBound = 0;
		int upperBound = 0;
		int taskCount = calcTaskCount(extractFiles,maxNum);
		List<List<TaskBean>> beanss = new ArrayList<List<TaskBean>>();
		for (int i = taskCount - 1; i >= 0; i--) {
			lowerBound = i * chunkSizePerThread;
			if (i == taskCount - 1) {
				upperBound = extractFiles.size();
			} else {
				upperBound = lowerBound + chunkSizePerThread;
			}
			beanss.add(extractFiles.subList(lowerBound, upperBound));  
		}
		return beanss;
	}

	private int calcTaskCount(List<TaskBean> extractFiles, int maxNum) { 
		return maxNum;
	}
 
}
