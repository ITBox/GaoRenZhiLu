package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.R;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.ConsultationEngine.ConsultationInfo;
import com.itbox.grzl.engine.ConsultationEngine.Result;

/**
 * 马上提问页面
 * 
 * @author baoyz
 * 
 *         2014-5-3 下午5:34:52
 * 
 */
public class QuickAskActivity extends BaseActivity {

	@InjectView(R.id.et_content)
	protected EditText mContentEt;
	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.et_title)
	protected EditText mTitleEt;

	private int jobtype;
	private ConsultationInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_ask);

		ButterKnife.inject(this);

		initView();

		info = new ConsultationInfo();
	}

	private void initView() {
		mTitleTv.setText("马上提问");
	}

	@OnClick(R.id.bt_ok)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_ok:
			commit();
			break;
		case R.id.bt_upload:
			// 上传图片
			break;
		case R.id.bt_type:
			// 类型
			break;
		}
	}

	/**
	 * 提交问题
	 */
	private void commit() {
		info.setContents(mContentEt.getText().toString());
		info.setJobtype(jobtype + 1);
		info.setTitle(mTitleEt.getText().toString());
		info.setUserId(14);
		info.setPhoto("photo");
		showLoadProgressDialog();
		// 提交
		ConsultationEngine.probleming(info,
				new GsonResponseHandler<Result>(Result.class) {
					@Override
					public void onFinish() {
						super.onFinish();
						dismissProgressDialog();
					}
					
					@Override
					public void onFailure(Throwable e, int statusCode,
							String content) {
						super.onFailure(e, statusCode, content);
						ToastUtils.showToast(mActThis, content);
					}

					@Override
					public void onSuccess(Result bean) {
						super.onSuccess(bean);
						if (bean.isSuccess()) {
							ToastUtils.showToast(mActThis, "提问成功");
							finish();
						}else{
							ToastUtils.showToast(mActThis, "提问失败");
						}
					}
				});
	}
}
