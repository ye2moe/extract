package com.ye2moe.io.util.scan;

import java.util.List;

import com.ye2moe.io.bean.TaskBean;

public interface ScanCondition {
	public boolean isAccept(Object o);

	public void addTask(Object o);

	public List<TaskBean> getTaskBeans();
}
