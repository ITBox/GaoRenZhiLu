package com.itbox.grzl.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.core.AppException;
import com.itbox.fx.core.AppTime;
import com.itbox.grzl.common.AppTimeEngine;
import com.itbox.grzl.common.db.AreaSQLHelper;
import com.itbox.grzl.common.db.CitiesSQLHelper;



import com.itbox.grzl.common.util.FileUtils;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by huiyh on 2014/4/2.
 */
public class SplashActivity extends BaseActivity {
	private static final int DELAY_TIME = 1500;
	private static final int REQUEST_SHOW_GUIDE = 0;

	private int loading = 2;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppTimeEngine listener = new AppTimeEngine();
		AppTime.setOnTimeSetChangedListener(listener);
		listener.onTimeSetChanged();
		releaseDB();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadCompleted();
			}
		}, DELAY_TIME);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_SHOW_GUIDE:
			if (resultCode == RESULT_OK) {
				startNextActivity();
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private boolean isLogined() { // TODO 判断是否有登录信息
		return false;
	}

	private void startNextActivity() {
		if (isLogined()) {
			startActivity(MainActivity.class);
			finish();
		} else {
			startActivity(LoginActicity.class);
			finish();
		}
	}
	
	private void releaseDB() {
		new Thread() { // 拷贝地址信息数据库
			public void run() {
				try {
					File dbFile = getDatabasePath(AreaSQLHelper.DB_AREAE);
					if (!dbFile.exists()) {//
						InputStream is = getAssets().open(AreaSQLHelper.DB_AREAE);
						File file = FileUtils.copyFile(is, dbFile);// 释放文件到当前应用程序的目录
						if (file == null || !file.exists()) {// 拷贝失败,程序直接退出
							android.os.Process.killProcess(android.os.Process.myPid());// 关闭程序
							return;
						}
					}
					dbFile = getDatabasePath(CitiesSQLHelper.DB_CITIES);
					if (!dbFile.exists()) {//
						InputStream is = getAssets().open(CitiesSQLHelper.DB_CITIES);
						File file = FileUtils.copyFile(is, dbFile);// 释放文件到当前应用程序的目录
						if (file == null || !file.exists()) {// 拷贝失败,程序直接退出
							android.os.Process.killProcess(android.os.Process.myPid());// 关闭程序
							return;
						}
					}
					
					loadCompleted();
				} catch (IOException e) {// 拷贝失败,程序直接退出.
					AppException.handle(e);
					android.os.Process.killProcess(android.os.Process.myPid());// 关闭程序
				}
			};

		}.start();
	}
	
	private void loadCompleted() {
		--loading;
		if (0 == loading) {
			if (hasShowGuide()) {
				startActivityForResult(GuideActivity.class, REQUEST_SHOW_GUIDE);
			} else {
				startNextActivity();
			}
		}
	}
	
	private boolean hasShowGuide(){
		SharedPreferences appConfig = AppContext.getAppPreferences();
		boolean hasShowGuide = appConfig.getBoolean(GuideActivity.HAS_SHOW_GUIDE, true);
		return hasShowGuide;
	}
}
