package com.ye2moe.io.util.scan;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.ye2moe.io.bean.TaskBean;

public class Scan {
	private File f;
	private ScanCondition condition;

	private static Logger logger = Logger.getLogger(Scan.class);

	public Scan() {

	}

	public Scan(String f, ScanCondition condition) {
		this.f = new File(f);
		this.condition = condition;
	}

	public List<TaskBean> scan() {
		logger.info("scan");
		if (f == null || condition == null)
			return null;
		this._scan(this.f, this.condition);

		logger.info("scan 结果共计：" + condition.getTaskBeans().size());
		return condition.getTaskBeans();
	}

	public List<TaskBean> scan(String f, ScanCondition condition) {
		this.scan(new File(f), condition);
		return condition.getTaskBeans();
	}

	public List<TaskBean> scan(File file, ScanCondition condition) {
		this.f = file;
		this.condition = condition;
		this._scan(this.f, condition);
		return condition.getTaskBeans();
	}

	// 递归扫描
	private void _scan(File f, ScanCondition condition) {
		if (f.isDirectory())
			for (File fs : f.listFiles())
				_scan(fs, condition);
		else if (condition.isAccept(f))
			condition.addTask(f);
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public ScanCondition getCondition() {
		return condition;
	}

	public void setCondition(ScanCondition condition) {
		this.condition = condition;
	}

	/*
	 * public List<TaskBean> filter(ScanCondition condition) { return null; }
	 * 
	 * public Scan scan(File f) { this._scan(f); return this; }
	 */
}
