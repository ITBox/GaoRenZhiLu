package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.TeacherWithdrawalsAdapter;
import com.itbox.grzl.bean.TeacherWithdrawals;
import com.itbox.grzl.engine.TeacherEngine;

/**
 * 提现记录界面
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherWithdrawalsListActivity extends BaseLoadActivity<TeacherWithdrawals> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private TeacherWithdrawalsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_withdrawals);

		ButterKnife.inject(this);
 
		initView();
	}

	private void initView() {
		mTitleTv.setText("提现明细");

		mAdapter = new TeacherWithdrawalsAdapter(getContext(), null);
		initLoad(mListView, mAdapter, TeacherWithdrawals.class);
	}

	@Override
	protected void loadData(int page) {
		TeacherEngine.getWithdrawals(page, AppContext.getInstance().getAccount()
				.getUserid(), null);
	}

}
