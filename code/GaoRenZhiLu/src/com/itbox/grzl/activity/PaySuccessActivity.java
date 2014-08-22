package com.itbox.grzl.activity;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.zhaoliewang.grzl.R;

public class PaySuccessActivity extends BaseActivity {

	@InjectView(R.id.tv_price)
	TextView tv_price;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay_success);
		ButterKnife.inject(this);
		double price = getIntent().getDoubleExtra("price", 0);
		tv_price.setText("黄浦天禧已收到您的付款" + price + "元");
		setTitle("支付成功");
		showLeftBackButton();
	}
}