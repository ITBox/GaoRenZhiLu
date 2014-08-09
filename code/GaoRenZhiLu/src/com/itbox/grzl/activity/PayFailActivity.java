package com.itbox.grzl.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

public class PayFailActivity extends BaseActivity {

	@InjectView(R.id.tv_info)
	protected TextView tv_info;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay_fail);
		setTitle("支付失败");
		showLeftBackButton();

		ButterKnife.inject(this);

		String info = getIntent().getStringExtra("info");
		if (!TextUtils.isEmpty(info)) {
			tv_info.setText(tv_info.getText() + " 错误码：" + info);

		}

	}

	@OnClick(R.id.bt_retry)
	public void onRetryClick(View v) {
		finish();
	}
}
