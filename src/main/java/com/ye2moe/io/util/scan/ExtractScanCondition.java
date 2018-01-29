package com.ye2moe.io.util.scan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ye2moe.io.bean.ExtractTaskBean;
import com.ye2moe.io.bean.TaskBean;

public class ExtractScanCondition implements ScanCondition {

	private List<TaskBean> beans;

	public ExtractScanCondition() {
	}

	public boolean isAccept(Object o) {
		if (o instanceof File)
			return ((File) o).getName().endsWith("zip");
		return false;
	}

	public List<TaskBean> getTaskBeans() {
		return beans;
	}

	public void addTask(Object o) {
		if (!(o instanceof File))
			return;
		if (beans == null)
			beans = new ArrayList<TaskBean>();
		File f = (File) o;
		ExtractTaskBean bean = new ExtractTaskBean();
		bean.setFiles(new String[] { f.getAbsolutePath(), f.getParentFile().getAbsolutePath() });
		bean.setSize(f.length());
		beans.add(bean);
	}

}
