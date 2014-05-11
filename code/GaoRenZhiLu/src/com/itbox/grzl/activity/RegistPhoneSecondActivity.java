package com.itbox.grzl.activity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Register;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistPhoneSecondActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.regist_authcode_et)
	EditText mETRegistAuthCode;
	@InjectView(R.id.regist_next_bt)
	Button mBTRegistNext;
	@InjectView(R.id.regist_auth_again_tv)
	TextView mTVRegistAgain;
	private String registPhone;
    private int seconds = 60;
    private Timer timer = new Timer();
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_regist_phone_second);
		ButterKnife.inject(mActThis);
		initViews();
		registPhone = getIntent().getStringExtra("registPhone");
	}
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("手机注册");
	}
	@OnClick(R.id.regist_next_bt)
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			RegistPhoneSecondActivity.this.finish();
			break;
		case R.id.regist_next_bt:
			String authCode = mETRegistAuthCode.getText().toString();
			if (StringUtil.isBlank(authCode)){
				ToastUtils.showToast(mActThis, "验证码不为空");
			} else {
				mTVRegistAgain.setVisibility(View.VISIBLE);
				RequestParams params = new RequestParams();
				params.put("userphone", registPhone);
				params.put("verifycode", authCode);
				params.put("type", String.valueOf(1));
				Net.request(params, Api.getUrl(Api.User.CheckVerifyCode), new GsonResponseHandler<Register>(Register.class){
					@Override
					public void onSuccess(Register object) {
						super.onSuccess(object);
						int result = object.getResult();
						if (result == 0) {
							ToastUtils.showToast(mActThis, "获取验证码失败");
						} else if (result == 1){
							mTVRegistAgain.setVisibility(View.VISIBLE);
							MyTimerTask task = new MyTimerTask();
							timer.schedule(task, 1000, 1000);
						}
					}
				});
			}
			break;

		default:
			break;
		}
		
	}
	
	 private class MyTimerTask extends TimerTask {

			@Override
			public void run() {
				// TODO Auto-generated method stub
	            runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						seconds --;
						mBTRegistNext.setEnabled(false);
						mTVRegistAgain.setText(seconds + "s后重新获取");
						if(seconds < 1){
							mBTRegistNext.setText("获取验证码");
							mBTRegistNext.setEnabled(true);
							mTVRegistAgain.setVisibility(View.GONE);
//							if (timer != null) {
								cancel();
								seconds = 60;
//							}
						}
					}
				});
			}
	    }
}
