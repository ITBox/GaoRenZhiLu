package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.R;

public class PayActivity extends BaseActivity {
	
	@InjectView(R.id.ll_select_time)
	protected View ll_select_time;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay);
		
		ButterKnife.inject(this);

		if ("picture".equals(getIntent().getStringExtra("type"))) {
			ll_select_time.setVisibility(View.GONE);
		}
	}
}
