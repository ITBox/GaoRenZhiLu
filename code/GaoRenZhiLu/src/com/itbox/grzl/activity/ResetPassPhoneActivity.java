package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.bean.Register;
import com.loopj.android.http.RequestParams;

import android.graphics.Color;
import android.os.Bundle;
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
	@InjectView(R.id.reset_1)
	TextView mReset1;
	@InjectView(R.id.reset_2)
	TextView mReset2;
	@InjectView(R.id.reset_3)
	TextView mReset3;
	@InjectView(R.id.reset_email)
	Button mBTResetEmail;
	
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
	        if(StringUtil.isBlank(mPhone)) {
	        	ToastUtils.showToast(mActThis, "手机号不为空");
	        } else{
	        	if (StringUtil.checkPhone(mPhone)){
	        		checkIsRegistServer(mPhone);
	        	} else {
	        		ToastUtils.showToast(mActThis, "手机号不符合规定");
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
		RequestParams params = new RequestParams();
		params.put("userphone", mPhone);
		params.put("type", String.valueOf(2));
		Net.request(params, Api.getUrl(Api.User.SendVerifyCode), new GsonResponseHandler<Register>(Register.class) {
			@Override
			public void onSuccess(Register object) {
				super.onSuccess(object);
				int result = object.getResult();
				if(result == 1) {
					initRest(1);
					
				} else if (result == 2) {
					ToastUtils.showToast(mActThis, "当前用户不存在");
				} else if (result == 5){
					ToastUtils.showToast(mActThis, "此号码操作频繁");
				}
			}
		});
	};
}
