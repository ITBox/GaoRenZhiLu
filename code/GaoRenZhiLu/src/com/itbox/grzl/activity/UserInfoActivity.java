package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserInfoActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_info);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("个人信息");
	}
}
