package com.itbox.grzl.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.itbox.grzl.AppContext;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.ProgressBar;

public class CheckUpdateVersion {
	
    /**
     * 比较本地版本号，跟服务器的版本号
     * @param version
     * @return
     */
	public static boolean compareVersion(String version) {
		if(!version.equals(getApkVersionName())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getApkVersionCode() {
		int versionCode = -1;
		try {
			versionCode = AppContext.getInstance().getPackageManager()
					.getPackageInfo(AppContext.getInstance().getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 获取应用版本NAME
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getApkVersionName() {
		String versionCode = null;
		try {
			versionCode = AppContext.getInstance().getPackageManager()
					.getPackageInfo(AppContext.getInstance().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	/**
	 * 下载APK
	 * @param resPath
	 * @param savePath
	 * @param pb
	 * @return
	 */
	public static File download(String resPath, String savePath, ProgressBar pb) {
		try {
			URL url = new URL(resPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			int code = conn.getResponseCode();
			// 请求成功
			if (code == 200) {
				// 设置进度条最大值
				pb.setMax(conn.getContentLength());
				int total = 0;
				// 创建输入输出流
				File file = new File(savePath);
				if (file.exists())
					file.delete();
				file.getParentFile().mkdirs();
				file.createNewFile();
				OutputStream out = new FileOutputStream(file);
				InputStream in = conn.getInputStream();
				byte[] buf = new byte[1024 * 8];
				int len;
				// 开始拷贝文件
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
					// 设置进度
					total += len;
					pb.setProgress(total);
				}
				out.flush();
				out.close();
				return file;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 安装apk
	 * 
	 * @param file
	 */
	public static void installApk(File file) {
		// 打开系统的安装程序安装
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AppContext.getInstance().startActivity(intent);
	}
}
