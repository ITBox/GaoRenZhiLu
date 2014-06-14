package com.itbox.grzl.activity;

import com.itbox.fx.util.DialogUtil;
import com.itbox.fx.util.ToastUtils;
import com.zhaoliewang.grzl.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity  implements OnClickListener{
    protected FragmentActivity mActThis;
    protected ImageLoader loader;
    protected DisplayImageOptions photoOptions;
    
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.mActThis = this;
		loader = ImageLoader.getInstance();
		photoOptions = new DisplayImageOptions.Builder()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.EXACTLY)
		.showImageForEmptyUri(R.drawable.ic_default_avater)
		.cacheInMemory(true)
	    .cacheOnDisc(true)
		.build();
	}
	
	protected BaseActivity getContext(){
		return this;
	}
	
	@Override
	public void onClick(View v) { }

	/**
	 * 显示进度条对话框
	 * @param msg
	 */
	protected void showProgressDialog(String msg) {
		DialogUtil.showProgressDialog(this, msg);
	}

	/**
	 * 显示正在加载对话框
	 */
	protected void showLoadProgressDialog() {
		DialogUtil.showProgressDialog(this,
				getString(R.string.loading));
	}

	/**
	 * 隐藏进度条对话框
	 */
	protected void dismissProgressDialog() {
		DialogUtil.dismissProgressDialog();
	}
	
	protected void showToast(String msg){
		ToastUtils.showToast(this, msg);
	}
	
	protected void showToast(int stringResID){
		ToastUtils.showToast(this, stringResID);
	}
	
	protected void startActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(this, activity);
		startActivity(intent);
	}
	
	protected void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
		Intent intent = new Intent(this, activity);
		startActivityForResult(intent, requestCode);
	}
	
	/** back键按下时的反应 */
	protected boolean onBack() {
		return false;
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 返回时提醒
		if (keyCode == KeyEvent.KEYCODE_BACK && onBack()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void showLeftBackButton(){
		View left = findViewById(R.id.text_left);
		if (left != null) {
			left.setVisibility(View.VISIBLE);
			left.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		TextView titleTv = (TextView) findViewById(R.id.text_medium);
		if (titleTv != null) {
			titleTv.setVisibility(View.VISIBLE);
			titleTv.setText(title);
		}
	}
}
