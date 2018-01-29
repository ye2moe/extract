package com.ye2moe.io.util.thread.extra;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.ye2moe.io.util.extract.AbstractExtract;
import com.ye2moe.io.util.extract.Extract;

public class ExtraThread implements Runnable{

	Extract extract;
	List<ZipEntry> entrys;
	String destFolder;
	ZipFile zipFile ;
 
	public ExtraThread(Extract extract, List<ZipEntry> entrys, String destFolder, ZipFile zipFile) {
		super();
		this.extract = extract;
		this.entrys = entrys;
		this.destFolder = destFolder;
		this.zipFile = zipFile;
	}

	public void run() {
		AbstractExtract.unZipEachEntrys(entrys,zipFile ,destFolder);
		extract.update();
	}

}
