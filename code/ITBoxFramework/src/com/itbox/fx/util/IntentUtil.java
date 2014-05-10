package com.itbox.fx.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * 启动常用意图
 * @author baoyz 
 * 
 * 2014-5-2 下午4:49:59
 *
 */
public class IntentUtil {
	public static void startWebActivity(Context context, String url) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		context.startActivity(intent);
	}

	public static void startEmailActivity(Context context, int toResId,
			int subjectResId, int bodyResId) {
		startEmailActivity(context, context.getString(toResId),
				context.getString(subjectResId), context.getString(bodyResId));
	}

	public static void startEmailActivity(Context context, String to,
			String subject, String body) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");

		if (!TextUtils.isEmpty(to)) {
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		}
		if (!TextUtils.isEmpty(subject)) {
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		}
		if (!TextUtils.isEmpty(body)) {
			intent.putExtra(Intent.EXTRA_TEXT, body);
		}

		final PackageManager pm = (PackageManager) context.getPackageManager();
		try {
			if (pm.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY).size() == 0) {
				intent.setType("text/plain");
			}
		} catch (Exception e) {
			Log.w("Exception encountered while looking for email intent receiver.",
					e);
		}

		context.startActivity(intent);
	}
}
