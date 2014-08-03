package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

public class PayFailActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay_success);
		setTitle("支付失败");
		showLeftBackButton();

		ButterKnife.inject(this);
	}

	@OnClick(R.id.bt_retry)
	public void onRetryClick(View v) {
		finish();
	}
}
