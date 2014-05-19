package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.TeacherWithdrawalsAdapter;
import com.itbox.grzl.bean.TeacherWithdrawals;
import com.itbox.grzl.engine.TeacherEngine;
import com.itbox.grzl.engine.TeacherEngine.UserWithdrawalsItem;

/**
 * 提现记录界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherWithdrawalsListActivity extends
		BaseLoadActivity<TeacherWithdrawals> {

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
	protected void loadData(final int page) {
		TeacherEngine.getWithdrawals(page, AppContext.getInstance()
				.getAccount().getUserid(),
				new GsonResponseHandler<UserWithdrawalsItem>(
						UserWithdrawalsItem.class) {
					@Override
					public void onSuccess(UserWithdrawalsItem bean) {
						// 保存到数据库
						if (bean != null) {
							saveData(page, bean.getUserWithdrawalsItem());
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
						loadFinish();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						showToast(content);
						// 还原页码
						restorePage();
					}
				});
	}

}
