package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.bean.UserProblem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

/**
 * 咨询详情
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class ConsultationFreeDetailActivity extends BaseActivity {

	@InjectView(R.id.tv_title)
	protected TextView tv_title;
	@InjectView(R.id.tv_name)
	protected TextView tv_name;
	@InjectView(R.id.tv_time)
	protected TextView tv_time;
	@InjectView(R.id.tv_content)
	protected TextView tv_content;
	@InjectView(R.id.iv_head)
	protected ImageView iv_head;

	private UserProblem mBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_free_detail);

		mBean = getIntent().getParcelableExtra("bean");

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		setTitle("咨询详情");
		showLeftBackButton();
		tv_content.setText(mBean.getContents());
		tv_name.setText(mBean.getUsername());
		tv_time.setText(mBean.getCreatetime());
		tv_title.setText(mBean.getTitle());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(mBean.getPhoto()), iv_head);

		ConsultationEngine.getProblemDetail(mBean.getProblemId() + "", null);
	}

	@OnClick(R.id.bt_ok)
	public void onClick(View v) {
		showLoadProgressDialog();
		// 我来解答
		ConsultationEngine.issolve(AppContext.getInstance().getAccount()
				.getUserid().toString(), mBean.getProblemId() + "",
				new GsonResponseHandler<RespResult>(RespResult.class) {
					@Override
					public void onSuccess(RespResult resp) {
						if (resp.isSuccess()) {
							showToast("您已经取得解答资格，请到我的咨询中进行解答");
							finish();
						} else if (resp.getResult() == 2) {
							showToast("别人已经解答");
						} else {
							showToast("解答失败");
						}
					}

					@Override
					public void onFinish() {
						dismissProgressDialog();
					}
				});
	}

	private void cancel() {
		showProgressDialog("正在取消...");
	}
}
