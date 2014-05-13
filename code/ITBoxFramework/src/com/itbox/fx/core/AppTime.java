package com.itbox.fx.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;

public class AppTime extends BroadcastReceiver {
	private static final String KEY_TIME_DEVIATION = "time_deviation";

	private static AppTime mInstance;
	private static OnTimeSetChangedListener mListener;

	private static long deviationMills;
	private static long startMills;
	private static long endMills;

	private boolean isRegistered;
	private Context context;
	static {
		mInstance = new AppTime(Application.getInstance());
	}

	public static AppTime getInstance() {
		return mInstance;
	}

	public static void setOnTimeSetChangedListener(OnTimeSetChangedListener listener) {
		mListener = listener;
	}

	public AppTime(Context context) {
		super();
		this.register(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (mListener != null) {
			setStartMillis();
			mListener.onTimeSetChanged();
		}
	}

	private IntentFilter getFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		return filter;
	}

	public AppTime register(Context context) {
		if (!isRegistered) {
			this.context = context;
			context.registerReceiver(this, getFilter());
			isRegistered = true;
		}
		return this;
	}

	public AppTime unRegister() {
		if (isRegistered) {
			context.unregisterReceiver(this);
			isRegistered = false;
		}
		return this;
	}

	/**
	 * <font color="red" size="4">必须是System.currentTimeMillis()</font>
	 */
	private static void setStartMillis() {
		startMills = System.currentTimeMillis();
	}

	/**
	 * <font color="red" size="4">必须是System.currentTimeMillis()</font>
	 */
	private static void setEndMillis() {
		endMills = System.currentTimeMillis();
	}

	public static synchronized long getTimeMillis() {
		if(deviationMills == 0){
			deviationMills = Application.getAppPreferences().getLong(KEY_TIME_DEVIATION, 1);
		}
		long clientTime = System.currentTimeMillis();
		long svrTime = clientTime + deviationMills;
		return svrTime;
	}
	
	public static void refreshTimeDeviation(long svrTimeMills) {
		setEndMillis();

		if (-1 != svrTimeMills) {

			deviationMills = svrTimeMills - (startMills / 2) - (endMills / 2);
			L.e("deviationMills:　"+deviationMills);
			Editor edit = Application.getAppPreferences().edit();
			edit.putLong(KEY_TIME_DEVIATION, deviationMills);
			edit.commit();

		}
	}

	public interface OnTimeSetChangedListener {
		public void onTimeSetChanged();
	}
}
