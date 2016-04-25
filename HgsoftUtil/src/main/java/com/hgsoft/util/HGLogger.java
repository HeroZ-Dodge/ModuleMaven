package com.hgsoft.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class HGLogger {
	private static final boolean LOG_SWITCH = true;// 日志开关
	private static final String LOG_TAG = "HGLogger";// 日志标志
	public String LOG_SAVE_PATH = "sdcard/android/data/HGLogger/Log/";// 日志保存路径
	
	private static HGLogger logger;
	
	public static HGLogger getInstance(Context context){
		if (logger == null) {
			logger = new HGLogger();
			logger.init(context);
		}
		return logger;
	}
	
	private void init(Context context){
		LOG_SAVE_PATH="sdcard/android/data/"+context.getPackageName()+"/log/";
	}
	/**
	 * 打印logcat日志(info)
	 * TODO
	 * @Title:error
	 * @param msg 
	 *
	 * @author weiliu
	 */
	public static void info(String msg) {
		if (LOG_SWITCH) {
			Log.i(LOG_TAG, msg);
		}
	}

	/**
	 * 打印logcat日志(debug)
	 * TODO
	 * @Title:error
	 * @param msg 
	 *
	 * @author weiliu
	 */
	public static void debug(String msg) {
		if (LOG_SWITCH) {
			Log.d(LOG_TAG, msg);
		}
	}

	/**
	 * 打印logcat日志(verbose)
	 * TODO
	 * @Title:error
	 * @param msg 
	 *
	 * @author weiliu
	 */
	public static void verbose(String msg) {
		if (LOG_SWITCH) {
			Log.v(LOG_TAG, msg);
		}
	}

	/**
	 * 打印logcat日志(error)
	 * TODO
	 * @Title:error
	 * @param msg 
	 *
	 * @author weiliu
	 */
	public static void error(String msg) {
		if (LOG_SWITCH) {
			Log.e(LOG_TAG, msg);
		}
	}

	/**
	 * 一般日志
	 * 
	 * @param logStr
	 */
	public void addNormalLog(String logStr) {
		addLog("NormalLog", logStr);
	}

	/**
	 * 通信Socket日志
	 * 
	 * @param logStr
	 */
	public void addSocketLog(String logStr) {
		addLog("SocketLog", logStr);
	}

	/**
	 * 车道操作日志
	 * 
	 * @param logStr
	 */
	public void addLaneLog(String logStr) {
		addLog("LaneLog", logStr);
	}

	/**
	 * 错误日志
	 * 
	 * @param logStr
	 */
	public void addErrorLog(String logStr) {
		addLog("ErrorLog", logStr);
	}

	/**
	 * 卡操作日志
	 * 
	 * @param logStr
	 */
	public void addCardLog(String logStr) {
		addLog("ICReadLog", logStr);
	}

	/**
	 * 添加日志到相应文件
	 * 
	 * @param tag
	 * @param logStr
	 */
	private void addLog(final String tag, final String logStr) {
		if (LOG_SWITCH) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					File file = checkLogFileIsExist(tag);
					if (file == null)
						return;
					FileOutputStream fos = null;
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss SSS");
						fos = new FileOutputStream(file, true);
						fos.write((sdf.format(new Date()) + ":" + logStr).getBytes("gbk"));
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
			}).start();
		}
	}

	private File checkLogFileIsExist(String pre) {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}
		File file = new File(LOG_SAVE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		if (pre == null) {
			pre = LOG_TAG;
		}
		file = new File(LOG_SAVE_PATH + pre + "-" + dateStr + ".txt");
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
	public void deleteLogFiles() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				FileUtil.delAllFile(LOG_SAVE_PATH);
			}
		}).start();
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
