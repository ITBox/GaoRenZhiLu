package com.itbox.grzl.activity;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.common.util.DialogMessage2;
import com.itbox.grzl.engine.RegistResetEngine;

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
	@InjectView(R.id.reset_email_psw_et)
	EditText mETResetPswEmail;
	
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
		        	if (StringUtil.checkEmail(email)) {
		        		RegistResetEngine.checkAccount(email, new GsonResponseHandler<CheckAccount>(CheckAccount.class) {
		        			@Override
		        			public void onSuccess(CheckAccount object) {
		        				super.onSuccess(object);
		        				if (object.getUserEmail() == 1) {
//		        					mETResetEmail.setVisibility(View.GONE);
//		        					mETResetPswEmail.setVisibility(View.VISIBLE);
		        					DialogMessage2 dialogMessage2 = DialogMessage2.newIntance();
		        					dialogMessage2.show(getSupportFragmentManager(), "resetEmail");
		        				} else if (object.getUserEmail() == 0) {
		        					showToast("此邮箱未注册过，不能修改密码");
		        				}
		        			}
		        		});
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
    
	private void reset(String password) {
		RegistResetEngine.resetPass(AppContext.getInstance().getAccount().getUserid()+"", password, new ResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, content);
			}
			
			@Override
			public void onFailure(Throwable e, int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onFailure(e, statusCode, content);
			}
		});
	}
}
