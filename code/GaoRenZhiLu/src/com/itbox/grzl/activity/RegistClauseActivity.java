package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.zhaoliewang.grzl.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * 服务条款
 * @author youzh
 * 2014年5月10日 下午11:45:48
 */
public class RegistClauseActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_regist_clause);
		ButterKnife.inject(mActThis);
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("高人指路服务条款");
	}
}
