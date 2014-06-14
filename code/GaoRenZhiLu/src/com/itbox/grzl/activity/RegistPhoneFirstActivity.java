package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.StringUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.bean.Register;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author youzh 2014年5月2日 下午5:43:48
 */
public class RegistPhoneFirstActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.regist_phone_et)
	EditText mETRegistPhone;
	@InjectView(R.id.regist_clause)
	TextView mTVRegistClause;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_regist_phone_first);
		ButterKnife.inject(mActThis);
		initViews();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("手机注册");
	}
    @OnClick(R.id.regist_clause)
    public void registClause(){
    	startActivity(LicenceActivity.class);
    }
    
	@OnClick({R.id.text_left, R.id.regist_get_authCode})
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			finish();
			break;
		case R.id.regist_get_authCode:
			String mPhone = mETRegistPhone.getText().toString();
			if (StringUtil.isBlank(mPhone)) {
				ToastUtils.showToast(mActThis, "手机号不为空");
			} else {
				if (StringUtil.checkPhone(mPhone)) {
					checkIsRegistServer(mPhone);
				} else {
					ToastUtils.showToast(mActThis, "手机号不符合规定");
				}
			}
			break;
		}
	}

	private void checkIsRegistServer(final String mPhone) {
		// TODO Auto-generated method stub
		
		Net.request("userphone", mPhone, Api.getUrl(Api.User.CheckAccount), new GsonResponseHandler<CheckAccount>(CheckAccount.class){
			public void onSuccess(CheckAccount object) {
				if (object.getUserPhone() == 0) {
					sendAuthCode(mPhone);
				} else {
					ToastUtils.makeCustomPosition(mActThis, "该手机号已被注册", R.id.alternate_view_group);
				}
			}
		}
		);
	}
	
	private void sendAuthCode(final String mPhone) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("userphone", mPhone);
		params.put("type", String.valueOf(1));
		Net.request(params, Api.getUrl(Api.User.SendVerifyCode), new GsonResponseHandler<Register>(Register.class) {
			@Override
			public void onSuccess(Register object) {
				super.onSuccess(object);
				int result = object.getResult();
				if(result == 1){
					Intent intent = new Intent(mActThis, RegistPhoneSecondActivity.class);
					intent.putExtra("registPhone", mPhone);
					startActivity(intent);
				} else if (result == 5){
					ToastUtils.showToast(mActThis, "此号码操作频繁");
				}
			}
		});
	};
}
