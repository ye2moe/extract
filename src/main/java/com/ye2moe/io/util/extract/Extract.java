package com.ye2moe.io.util.extract;

public interface Extract {

	/**
	 * 单线程解压
	 */
	public void extract(String zipFile, String destFolder);

	/**
	 * 多线程解压
	 */
	public void extracts(String zipFile, String destFolder);

	public void pack(String root, String dest);

	public void update();
}
