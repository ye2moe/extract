package com.ye2moe.io.util.extract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.ye2moe.io.util.thread.extra.ExtraThread;

public class ZipExtract extends AbstractExtract implements Extract {
	ZipInputStream zis;
	List<ZipEntry> entrys;
	String destFolder;
	ZipFile zipFile;
	int extractTaskCount = 0;
	private static Logger logger = Logger.getLogger(ZipExtract.class);

	public void extract(String zipFile, String destFolder) {
		AbstractExtract.unZipFile(zipFile, destFolder);
	}

	public void extracts(String zipFile, String destFolder) {
		try {
			this.zipFile = new ZipFile(zipFile);
			entrys = new ArrayList<ZipEntry>();
			this.destFolder = destFolder;
			logger.info("======extracts:" + zipFile + "======");
			for (Enumeration entries = this.zipFile.entries(); entries.hasMoreElements();) {
				entrys.add((ZipEntry) entries.nextElement());
			}
			allot();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void allot() {
		if (entrys == null || entrys.size() <= 0)
			return;
		int taskCount = extractTaskCount = 4;
		int chunkSizePerThread = entrys.size() / taskCount;
		int lowerBound = 0;
		int upperBound = 0;
		ExtraThread et;
		for (int i = taskCount - 1; i >= 0; i--) {
			lowerBound = i * chunkSizePerThread;
			if (i == taskCount - 1) {
				upperBound = entrys.size();
			} else {
				upperBound = lowerBound + chunkSizePerThread;
			}
			et = new ExtraThread(this, entrys.subList(lowerBound, upperBound), destFolder, zipFile);
			dispatchWork(et, i);
		}
	}

	public void pack(String root, String dest) {

	}

	public synchronized void update() {
		extractTaskCount--;
		if (extractTaskCount <= 0) {
			logger.info("======extracts done:" + zipFile + "======");
			clean();
			extractTaskCount = 0;
		}
	}

	private void clean() {
		if (zis != null)
			try {
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		zis = null;
	}

	/**
	 * 解压集合 ZipEntry 文件
	 * 
	 * @param entry
	 * @param destFolder
	 * @throws IOException
	 */
	public static void unZipEachEntrys(List<ZipEntry> entrys, String destFolder) {
		try {
			for (ZipEntry entry : entrys) {
				unZipEachEntry(entry, destFolder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void unZipEachEntry(ZipEntry entry, String destFolder) throws IOException {
		printExtractingMsg(entry.getName());
		if (entry.isDirectory()) {
			new File(destFolder + "/" + entry.getName()).mkdirs();
			return;
		} else {
			int di = entry.getName().lastIndexOf('/');
			if (di != -1) {
				new File(destFolder + "/" + entry.getName().substring(0, di)).mkdirs();
			}
		}
		Files.write(Paths.get(destFolder + "/" + entry.getName()), entry.getExtra());
	}

}
