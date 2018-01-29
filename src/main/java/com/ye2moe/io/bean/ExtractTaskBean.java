package com.ye2moe.io.bean;

public class ExtractTaskBean extends TaskBean {
	String[] files;
	long size;

	
	
	@Override
	public String[] getFilePaths() { 
		return files;
	}
	
	public int compareTo(ExtractTaskBean o) {
		return (int) (this.size - o.size);
	}
 
	public void setFiles(String[] files) {
		this.files = files;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int compareTo(TaskBean o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
