package com.itbox.fx.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

/**
 * @author hyh 2013-7-1 下午1:04:16
 *
 * 异常统一处理
 */
public class AppException implements UncaughtExceptionHandler {
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	private static final String CAUGHT_TITLE = "Caught-Exception";
	private static final String UNCAUGHT_TITLE = "Uncaught-Exception";
	
	/**
	 * 捕获异常统一处理
	 * 
	 * @param e
	 */
	public static void handle(Throwable e) {
        if(AppState.isDeveloping()){
			Log.e("System.err", "---------------Caught Error---------------");
			e.printStackTrace();
            storeLog(e, CAUGHT_TITLE);
        }else{
			//e.printStackTrace();
        }
	}

	/**
	 * 未捕获异常统一处理
	 * 
	 * @param t
	 * @param e
	 */
	public void uncaughtException(Thread t, Throwable e) {
		//对未捕获异常进行处理
        if(AppState.isDeveloping()){
			Log.e("System.err", "---------------Uncaught Error---------------");
			e.printStackTrace();
            storeLog(e, UNCAUGHT_TITLE);
        }else{
			//e.printStackTrace();
        }
		// 关闭当前页面.
//		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	/**
	 * 存储异常
	 * @param e
	 */
	private static void storeLog(Throwable e,String title){
		try {
			File file = new File(getParentFile(),title +".log");
			//如果文件存在,启用追加模式
			FileOutputStream fos = new FileOutputStream(file, file.exists());
			if(!file.exists()){
				writeLogHeader(fos);
			}			
			writeLogContent(e, fos, title);			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	

	/**
	 * 异常日志,头部添加设备信息
	 * @param fos
	 */
	private static void writeLogHeader(FileOutputStream fos){
		//未捕获异常,头部添加设备信息
		Field[] fileds = Build.class.getDeclaredFields();
		
		for (Field f : fileds) {
			try {
				f.setAccessible(true);// 暴力反射 获取私有字段.
				String result;
				result = f.getName() + ":" + f.get(null); 
				fos.write(result.getBytes());
				fos.write("\n".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 输出异常内容
	 * @param fos
	 */
	private static void writeLogContent(Throwable e,FileOutputStream fos, String title){
		try {
			//异常分割线
			String time = formatter.format(System.currentTimeMillis());
			fos.write("--------------------".getBytes());
			fos.write((title + " At:" + time).getBytes());			
			fos.write("--------------------".getBytes());
			fos.write("\n".getBytes());
			//异常内容
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			fos.write(sw.toString().getBytes());
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static File getParentFile(){
		File file = null;
		if(AppState.isDeveloping() && isSDCardExist()){
			file = getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getParentFile();
		}else{
			file = getAppContext().getFilesDir();
		}
		return file;
	}
	
	/** 判断SD卡是否存在  */
	public static boolean isSDCardExist(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	private static Context getAppContext(){
		return AppContext.getInstance();
	}
	
	@SuppressWarnings("unused")
	private static void log(String log){
        L.i(log);
	}
}
