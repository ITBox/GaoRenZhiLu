package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.DialogUtil;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.TeacherWithdrawals;
import com.itbox.grzl.common.util.DialogMessage;
import com.itbox.grzl.engine.TeacherEngine;

/**
 * 提现记录详情界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherWithdrawalsInfoActivity extends BaseActivity {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.tv_id)
	protected TextView mIdTv;
	@InjectView(R.id.tv_price)
	protected TextView mPriceTv;
	@InjectView(R.id.tv_time)
	protected TextView mTimeTv;
	@InjectView(R.id.tv_status)
	protected TextView mStatusTv;
	@InjectView(R.id.tv_description)
	protected TextView mDescriptionTv;
	private TeacherWithdrawals mBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_info);

		mBean = getIntent().getParcelableExtra("bean");

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("提现详情");
		showLeftBackButton();
		mIdTv.setText(mBean.getTwId());
		mPriceTv.setText(mBean.getPrice() + "元");
		mTimeTv.setText(mBean.getCreatetime());
		mStatusTv.setText(mBean.getStatusName());
		mDescriptionTv.setText(mBean.getDescription());
	}

	@OnClick(R.id.bt_cancel)
	public void onClick(View v) {
		cancel();
	}

	private void cancel() {
		showProgressDialog("正在取消...");
		TeacherEngine.cancelWithdrawals(mBean.getTwId(), new ResponseHandler() {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onFailure(Throwable e, int statusCode, String content) {
				super.onFailure(e, statusCode, content);
				showToast("取消失败：" + content);
			}

			@Override
			public void onSuccess(String content) {
				super.onSuccess(content);
				showToast("取消成功");
				setResult(1);
				finish();
			}
		});
	}
}
