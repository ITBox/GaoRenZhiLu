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

	@OnClick(R.id.regist_get_authCode)
	@Override
	public void onClick(View v) {
		super.onClick(v);
		String mPhone = mETRegistPhone.getText().toString();
        if(StringUtil.isBlank(mPhone)) {
        	ToastUtils.showToast(mActThis, "手机号不为空");
        } else{
        	
        }
	}
}
