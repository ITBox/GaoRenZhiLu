package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.TeacherEngine;

/**
 * 申请提现界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherWithdrawalsAddActivity extends BaseActivity {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.et_price)
	protected EditText mPriceEt;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_add);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("申请提现");
		showLeftBackButton();
	}

	@OnClick(R.id.bt_add)
	public void onClick(View v) {
		// 申请提现
		String price = mPriceEt.getText().toString();
		String description = mContentEt.getText().toString();
		Double p = null;
		try {
			p = new Double(price);
		} catch (Exception e) {
		}
		if (p != null && p > 0) {
			showProgressDialog("正在提交...");
			TeacherEngine.addWithdrawals(p, description,
					new GsonResponseHandler<RespResult>(RespResult.class) {
						@Override
						public void onFinish() {
							super.onFinish();
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(RespResult result) {
							super.onSuccess(result);
							if (result.isSuccess()) {
								showToast("申请成功");
								finish();
							} else {
								showToast("申请失败");
							}
						}

						@Override
						public void onFailure(Throwable e, int statusCode,
								String content) {
							super.onFailure(e, statusCode, content);
							showToast(content);
						}
					});
		} else {
			showToast("请输入金额");
		}

	}
}
