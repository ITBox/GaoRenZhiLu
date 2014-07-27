package com.itbox.grzl.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.UserProblem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.UserEngine;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

/**
 * 电话咨询详情
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class ConsultationPhoneDetailActivity extends BaseActivity {

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
		setContentView(R.layout.activity_consultation_phone_detail);

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
		// 打电话
		UserEngine.getUserList(mBean.getUserid(),
				new GsonResponseHandler<Account>(Account.class) {
					@Override
					public void onSuccess(Account user) {
						Intent phoneIntent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ user.getUserphone()));
						startActivity(phoneIntent);
					}

					@Override
					public void onFinish() {
						dismissProgressDialog();
					}
				});
	}
}
