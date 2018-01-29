package com.ye2moe.io.util.extract;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * 解压
 * 
 * @author ye2moe
 *
 */
public abstract class AbstractExtract {
	private static Logger logger = Logger.getLogger(AbstractExtract.class);

	public void allot() {

	}

	public void dispatchWork(Runnable task, int workerIndex) {
		Thread workerThread = new Thread(task);
		workerThread.setName(task.getClass().getName() + ":" + workerIndex);
		workerThread.start();
		logger.info(workerThread.getName() + ":开始工作！");
	}

	public static boolean isPrint = true;
	/*
	 * public static void extract(String srcFile, String destFolder) { if
	 * (srcFile.toLowerCase().endsWith(".rar")) unRarFile(srcFile, destFolder); else
	 * if (srcFile.toLowerCase().endsWith(".zip")) unZipFile(srcFile, destFolder); }
	 */

	public static void extract(File srcFile, String destFolder) {
		if (srcFile.getName().toLowerCase().endsWith(".rar"))
			unRarFile(srcFile, destFolder);
		else if (srcFile.getName().toLowerCase().endsWith(".zip"))
			unZipFile(srcFile, destFolder);
	}

	public static void unZipFile(String zipFile, String destFolder) {
		unZipFile(new File(zipFile), destFolder);
	}

	public static void unZipFile(File zipFile, String destFolder) {
		BufferedOutputStream dest = null;
		ZipInputStream zis;
		try {
			zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				printExtractingMsg(entry.getName());
				int count;
				byte data[] = new byte[4];
				if (entry.isDirectory()) {
					new File(destFolder + "/" + entry.getName()).mkdirs();
					continue;
				} else {
					int di = entry.getName().lastIndexOf('/');
					if (di != -1) {
						new File(destFolder + "/" + entry.getName().substring(0, di)).mkdirs();
					}
				}
				FileOutputStream fos = new FileOutputStream(destFolder + "/" + entry.getName());
				dest = new BufferedOutputStream(fos);
				while ((count = zis.read(data)) != -1)
					dest.write(data, 0, count);
				dest.flush();
				dest.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压集合 ZipEntry 文件
	 * 
	 * @param entry
	 * @param destFolder
	 * @throws IOException
	 */
	public static void unZipEachEntrys(List<ZipEntry> entrys, ZipFile zipFile, String destFolder) {
		try {
			for (ZipEntry entry : entrys) {
				unZipEachEntry(entry, zipFile, destFolder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压单个ZipEntry 文件
	 * 
	 * @param entry
	 * @param destFolder
	 * @throws IOException
	 */
	private static void unZipEachEntry(ZipEntry entry, ZipFile zipFile, String destFolder) throws IOException {
		printExtractingMsg(entry.getName());
		byte[] _byte = new byte[1024];
		File _file = new File(destFolder + File.separator + entry.getName());
		if (entry.isDirectory()) {
			_file.mkdirs();
		} else {
			File _parent = _file.getParentFile();
			if (!_parent.exists()) {
				_parent.mkdirs();
			}
			InputStream _in = zipFile.getInputStream(entry);
			OutputStream _out = new FileOutputStream(_file);
			int len = 0;
			while ((len = _in.read(_byte)) > 0) {
				_out.write(_byte, 0, len);
			}
			_in.close();
			_out.flush();
			_out.close();
		}
	}

	public static void unRarFile(String srcRarPath, String destFolder) {
		unRarFile(new File(srcRarPath), destFolder);
	}

	public static void unRarFile(File srcRarPath, String dstDirectoryPath) {
		File dstDiretory = new File(dstDirectoryPath);
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive a = null;
		try {
			a = new Archive(srcRarPath);
			if (a != null) {
				// a.getMainHeader().print(); // 打印文件信息.
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {
					// 防止文件名中文乱码问题的处理
					String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
					printExtractingMsg(fileName);
					if (fh.isDirectory()) { // 文件夹
						File fol = new File(dstDirectoryPath + File.separator + fileName);
						fol.mkdirs();
					} else { // 文件
						File out = new File(dstDirectoryPath + File.separator + fileName.trim());
						try {
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);

							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}
				a.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printExtractingMsg(String fileName) {
		if (isPrint)
			logger.info("Extracting: " + fileName);
	}
}
