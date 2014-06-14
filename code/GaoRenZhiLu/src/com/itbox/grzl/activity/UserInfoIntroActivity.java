package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 * @author youzh
 *
 */
public class UserInfoIntroActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.userinfo_intro_et)
	EditText mEtUserInfoIntro;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_intro);
		ButterKnife.inject(mActThis);
		String intro = getIntent().getStringExtra("intro");
		initViews();
		mEtUserInfoIntro.setText(intro);
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("个人资料");
		mTVTopMedium.setText("修改简介");
	}

	@OnClick(R.id.text_left)
	public void cancel() {
		UserInfoIntroActivity.this.finish();
	}

	@OnClick(R.id.userinfo_intro_save)
	public void save() {
		Intent data = new Intent();
		data.putExtra("userIntro", mEtUserInfoIntro.getText().toString());
		setResult(RESULT_OK, data);
		UserInfoIntroActivity.this.finish();
	}
}
