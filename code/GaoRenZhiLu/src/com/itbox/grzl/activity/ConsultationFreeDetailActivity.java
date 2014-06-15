package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.bean.UserProblem;
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
		tv_name.setText(mBean.getUserid());
		tv_time.setText(mBean.getCreatetime());
		tv_title.setText(mBean.getTitle());
	}

	@OnClick(R.id.bt_ok)
	public void onClick(View v) {
		// 我来解答
	}

	private void cancel() {
		showProgressDialog("正在取消...");
	}
}
