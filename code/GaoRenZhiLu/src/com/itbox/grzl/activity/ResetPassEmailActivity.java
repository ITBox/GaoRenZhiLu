package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPassEmailActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.reset_email_et)
	EditText mETResetEmail;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_reset_email);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("邮箱找回密码");
	}
	
	 @OnClick({R.id.text_left, R.id.reset_phone, R.id.reset_comfign})
		@Override
		public void onClick(View v) {
	    	switch (v.getId()) {
			case R.id.text_left:
				finish();
				break;
			case R.id.reset_phone:
				startActivity(ResetPassPhoneActivity.class);
				finish();
				break;
			case R.id.reset_comfign:
				String email = mETResetEmail.getText().toString();
		        if(StringUtil.isBlank(email)) {
		        	ToastUtils.showToast(mActThis, "邮箱不为空");
		        } else{
		        	if (StringUtil.checkEmail(email)){
		        		
		        	} else {
		        		ToastUtils.showToast(mActThis, "邮箱不符合规定");
		        	}
		        }
				break;
			default:
				break;
			}
			super.onClick(v);
		}
}
