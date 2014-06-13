package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.bean.Register;
import com.itbox.grzl.engine.RegistResetEngine;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPassPhoneActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.reset_phone_et)
	EditText mETResetPhone;
	@InjectView(R.id.reset_code_et)
	EditText mETResetCode;
	@InjectView(R.id.reset_1)
	TextView mReset1;
	@InjectView(R.id.reset_2)
	TextView mReset2;
	@InjectView(R.id.reset_3)
	TextView mReset3;
	@InjectView(R.id.reset_email)
	Button mBTResetEmail;
	@InjectView(R.id.reset_get_authCode)
	Button mBTResetGet;
	private String phoneNum = "";
	private boolean isCode = false;
	private boolean isSavePsw = false;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_reset_phone);
		ButterKnife.inject(mActThis);
		initViews();
		initRest(0);
	}
	
	private void initRest(int num) {
		switch (num) {
		case 0:
			mReset1.setTextColor(Color.rgb(47, 136, 200));
			mReset2.setTextColor(Color.rgb(255, 255, 255));
			mReset3.setTextColor(Color.rgb(255, 255, 255));
			break;
		case 1:
			mReset1.setTextColor(Color.rgb(255, 255, 255));
			mReset2.setTextColor(Color.rgb(47, 136, 200));
			mReset3.setTextColor(Color.rgb(255, 255, 255));
			break;
		case 2:
			mReset1.setTextColor(Color.rgb(255, 255, 255));
			mReset2.setTextColor(Color.rgb(255, 255, 255));
			mReset3.setTextColor(Color.rgb(47, 136, 200));
			break;

		default:
			break;
		}
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("手机找回密码");
	}
	
    @OnClick({R.id.text_left, R.id.reset_email, R.id.reset_get_authCode})
	@Override
	public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.text_left:
			finish();
			break;
		case R.id.reset_email:
			startActivity(ResetPassEmailActivity.class);
			finish();
			break;
		case R.id.reset_get_authCode:
			String mPhone = mETResetPhone.getText().toString();
			if (isSavePsw) {
				resetPSW(mPhone);
			} else {
				if (isCode) {
					getAuthCode(mPhone);
				} else {
					if(StringUtil.isBlank(mPhone)) {
						showToast( "手机号不为空");
					} else{
						if (StringUtil.checkPhone(mPhone)){
							checkIsRegistServer(mPhone);
						} else {
							showToast("手机号不符合规定");
						}
					}
				}
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}
    
    private void checkIsRegistServer(final String mPhone) {
		
		Net.request("userphone", mPhone, Api.getUrl(Api.User.CheckAccount), new GsonResponseHandler<CheckAccount>(CheckAccount.class){
			public void onSuccess(CheckAccount object) {
				if (object.getUserPhone() == 0) {
					ToastUtils.makeCustomPosition(mActThis, "您的手机尚未注册", R.id.alternate_view_group);
				} else {
					sendAuthCode(mPhone);
				}
			}
		}
		);
	}
    
	private void sendAuthCode(final String mPhone) {
		// TODO Auto-generated method stub
		
		RegistResetEngine.sendAuthCode(mPhone, 2, new GsonResponseHandler<Register>(Register.class) {
			@Override
			public void onSuccess(Register object) {
				super.onSuccess(object);
				int result = object.getResult();
				if(result == 1) {
					initRest(1);
					phoneNum = mPhone;
					mETResetPhone.setText("");
					mETResetPhone.setHint("请输入验证码");
					mBTResetGet.setText("下一步");
					isCode = true;
				} else if (result == 2) {
					showToast("当前用户不存在");
				} else if (result == 5) {
					showToast("此号码操作频繁");
				}
			}
		});
	};
	
	private void getAuthCode(String code) {
		if (StringUtil.isBlank(code)){
			showToast("验证码不为空");
		} else {
		    RegistResetEngine.getAuthCode(phoneNum, code, 2, new GsonResponseHandler<Register>(Register.class){
				@Override
				public void onSuccess(Register object) {
					super.onSuccess(object);
					int result = object.getResult();
					if (result == 0) {
						showToast("验证码错误");
//						mBTRegistNext.setEnabled(false);
					} else if (result == 1) {
						initRest(2);
						mETResetPhone.setText("");
						mETResetPhone.setHint("请输入新密码");
						mBTResetGet.setText("保  存");
						isSavePsw = true;
					}
				}
			});
		}
	}
	
	private void resetPSW (String psw) {
		if (StringUtil.isBlank(psw)) {
			showToast("新密码不为空");
		} else {
			showProgressDialog("保存中...");
			RegistResetEngine.resetPass(AppContext.getInstance().getAccount().getUserid()+"", psw, new ResponseHandler() {
				@Override
				public void onSuccess(String content) {
					super.onSuccess(content);
					Log.i("youzh", content);
					if (content.equals("200")) {
						showToast("重置密码成功");
						mActThis.finish();
					} else {
						showToast("重置密码失败");
					}
				}
			});
		}
	}
	
	
}
