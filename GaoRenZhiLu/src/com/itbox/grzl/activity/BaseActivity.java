package com.itbox.grzl.activity;

import com.itbox.fx.util.DialogUtil;
import com.itbox.grzl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity  implements OnClickListener{
    protected FragmentActivity mActThis;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.mActThis = this;
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
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	protected void showToast(int stringResID){
		Toast.makeText(this, stringResID, Toast.LENGTH_SHORT).show();
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
}
