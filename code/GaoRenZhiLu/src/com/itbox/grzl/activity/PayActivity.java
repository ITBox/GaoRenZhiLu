package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.R;

public class PayActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView backTextView;
	@InjectView(R.id.text_medium)
	TextView mediumTextView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay);
		ButterKnife.inject(this);
		mediumTextView.setText("购买咨询");
		backTextView.setVisibility(View.VISIBLE);

	}

	@OnClick(R.id.text_left)
	public void back() {
		finish();
	}
}
