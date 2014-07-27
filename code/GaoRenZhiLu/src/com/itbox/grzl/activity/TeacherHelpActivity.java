package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TeacherHelpActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.help1)
	RelativeLayout mRLHelp1;
	@InjectView(R.id.help2)
	RelativeLayout mRLHelp2;
	@InjectView(R.id.help3)
	RelativeLayout mRLHelp3;
	@InjectView(R.id.help4)
	RelativeLayout mRLHelp4;
	@InjectView(R.id.help5)
	RelativeLayout mRLHelp5;
	@InjectView(R.id.help1_tv)
	TextView mTvHelp1;
	@InjectView(R.id.help2_tv)
	TextView mTvHelp2;
	@InjectView(R.id.help3_tv)
	TextView mTvHelp3;
	@InjectView(R.id.help4_tv)
	TextView mTvHelp4;
	@InjectView(R.id.help5_tv)
	TextView mTvHelp5;
	@InjectView(R.id.help_iv1)
	ImageView mIvHelp1;
	@InjectView(R.id.help_iv2)
	ImageView mIvHelp2;
	@InjectView(R.id.help_iv3)
	ImageView mIvHelp3;
	@InjectView(R.id.help_iv4)
	ImageView mIvHelp4;
	@InjectView(R.id.help_iv5)
	ImageView mIvHelp5;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_teacher_help);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("设置");
		mTVTopMedium.setText("帮助");
	}
	@OnClick({R.id.text_left, R.id.help1, R.id.help2, R.id.help3, R.id.help4, R.id.help5})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.help1:
			visibility(mTvHelp1, mIvHelp1);
			break;
		case R.id.help2:
			visibility(mTvHelp2, mIvHelp2);
			break;
		case R.id.help3:
			visibility(mTvHelp3, mIvHelp3);
			break;
		case R.id.help4:
			visibility(mTvHelp4, mIvHelp4);
			break;
		case R.id.help5:
			visibility(mTvHelp5, mIvHelp5);
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	private void visibility(TextView tv, ImageView iv) {
		if (tv.getVisibility() == View.GONE) {
			tv.setVisibility(View.VISIBLE);
			iv.setImageResource(R.drawable.arrow_up);
		} else {
			tv.setVisibility(View.GONE);
			iv.setImageResource(R.drawable.arrow_down);
		}
	}
}
