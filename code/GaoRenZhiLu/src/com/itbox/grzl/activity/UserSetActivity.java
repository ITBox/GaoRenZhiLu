package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * 
 * @author youzh
 *
 */
public class UserSetActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.userset_version_tv)
	TextView mTVVersion;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_set);
		ButterKnife.inject(mActThis);
		initViews();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("设置");
	}

	@OnClick({R.id.text_left,R.id.userset_version, R.id.userset_help, R.id.userset_about,R.id.userset_logout})
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			UserSetActivity.this.finish();
			break;
		case R.id.userset_version:
			
			break;
		case R.id.userset_help:
			
			break;
		case R.id.userset_about:
			
			break;
		case R.id.userset_logout:
			new Delete().from(Account.class).execute();
			startActivity(LoginActicity.class);
			mActThis.finish();
			break;

		default:
			break;
		}
	}
}
