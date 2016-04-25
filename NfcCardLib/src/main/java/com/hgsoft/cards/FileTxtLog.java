package com.hgsoft.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.os.Environment;

public class FileTxtLog {

	/** 日志保存路径 */
	public static final String LOG_SAVE_PATH = "sdcard/android/data/NfcCardOP/Log/";
	/** 日志开关 */
	private static final boolean LOG_SWITCH = true;

	/** 日志标志 */
	private static final String LOG_TAG = "ETCAndroid";

	private static FileTxtLog mlog;

	public static FileTxtLog logInstance() {
		if (mlog == null) {
			mlog = new FileTxtLog();
		}
		return mlog;
	}
	
	/**
	 * 一般日志
	 * @param logStr
	 */
	public void addNormalLog(String logStr){
		addLog("Normal",logStr);
	}
	
	/**
	 * 通信日志
	 * @param logStr
	 */
	public void addSocketLog(String logStr){
		addLog("SocketLog",logStr);
	}
	
	/**
	 * 车道操作日志
	 * @param logStr
	 */
	public void addLaneLog(String logStr){
		addLog("LaneLog",logStr);
	}
	
	/**
	 * 错误日志
	 * @param logStr
	 */
	public void addErrorLog(String logStr){
		addLog("ErrorLog",logStr);
	}
	
	/**
	 * 卡操作日志
	 * @param logStr
	 */
	public void addCardLog(String logStr){
		addLog("ICReadFJLog",logStr);
	}

	/**
	 * 添加日志到相应文件
	 * @param tag
	 * @param logStr
	 */
	private void addLog(String tag,String logStr) {
		if (LOG_SWITCH) {
			File file = checkLogFileIsExist(tag);
			if (file == null)
				return;
			FileOutputStream fos = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				fos = new FileOutputStream(file, true);
				fos.write((sdf.format(new Date()) + " " + logStr)
						.getBytes("gbk"));
				fos.write("\r\n".getBytes("gbk"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null) {
						fos.close();
						fos = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
				file = null;
			}
		}
	}

	private File checkLogFileIsExist(String pre) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return null;
		}
		File file = new File(LOG_SAVE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		if (pre==null) {
			pre=LOG_TAG;
		}
		file = new File(LOG_SAVE_PATH +pre +"-"+dateStr+".txt");
		if (!isLogExist(file)) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sdf = null;
		return file;
	}

	/**
	 * 删除所有日志文件
	 */
	public void deleteLogFiles(){
		//FileUtil.delAllFile(LOG_SAVE_PATH);
	}
	
	/**
	 * 检查当天日志文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	private boolean isLogExist(File file) {
		File tempFile = new File(LOG_SAVE_PATH);
		File[] files = tempFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[0].getName().trim().equalsIgnoreCase(file.getName())) {
				return true;
			}
		}
		return false;
	}
}
