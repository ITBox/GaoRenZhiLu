package com.itbox.fx.widget;

import com.itbox.fx.R;
import com.itbox.fx.core.AppContext;
import com.itbox.fx.core.AppException;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * 数据加载对话框
 * 
 * @author hyh creat_at：2013-7-16-下午1:49:01
 */
public class LoadingDialog extends Dialog {

	private TextView messageTv;
	private ImageView progressIv;
	private AnimationDrawable aDraw;

	public LoadingDialog(Context context) {
		super(context, R.style.custom_dialog);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_loading, null);
		setContentView(view);
		progressIv = (ImageView) findViewById(R.id.dialog_iv_progress);
		aDraw = (AnimationDrawable) progressIv.getDrawable();
		messageTv = (TextView) view.findViewById(R.id.dialog_message);

	}

	public void setMessage(int msgResID) {
		messageTv.setText(AppContext.getRes().getString(msgResID));
	}

	public void setMessage(String message) {
		messageTv.setText(message);
	}

	@Override
	public void show() {
		try {
			progressIv.post(new Runnable() {
				@Override
				public void run() {
					aDraw.start();
				}
			});
			super.show();
		} catch (Exception e) {
			AppException.handle(e);
		}
	}

	@Override
	public void dismiss() {
		try {
			progressIv.post(new Runnable() {
				@Override
				public void run() {
					if (null != aDraw && aDraw.isRunning()) {
						aDraw.stop();
					}
				}
			});
			if (isShowing()) {
				super.dismiss();
			}
		} catch (Exception e) {
			AppException.handle(e);
		}
	}

}
