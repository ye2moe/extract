package com.ye2moe.io.util.scan;

import java.util.List;

import com.ye2moe.io.bean.TaskBean;

public class DefaultScanCondition implements ScanCondition {

	public boolean isAccept(Object f) {  
		return true;
	}

	public List<TaskBean>  getTaskBeans() { 
		return null;
	}

	public void addTask(Object o) { 
		
	}

}
