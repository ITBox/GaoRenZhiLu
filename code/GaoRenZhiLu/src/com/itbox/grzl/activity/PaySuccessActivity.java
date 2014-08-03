package com.itbox.grzl.activity;

import android.os.Bundle;

import com.zhaoliewang.grzl.R;

public class PaySuccessActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay_success);
		setTitle("支付成功");
		showLeftBackButton();
	}
}