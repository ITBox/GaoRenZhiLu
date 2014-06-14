package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserSetAboutActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_about);
		ButterKnife.inject(mActThis);
		initViews();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("设置");
		mTVTopMedium.setText("关于高人指路");
	}

	@OnClick(R.id.text_left)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;

		default:
			break;
		}
		super.onClick(v);
	}
}
