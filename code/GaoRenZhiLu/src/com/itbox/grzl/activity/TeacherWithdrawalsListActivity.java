package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, TeacherWithdrawalsInfoActivity.class);
		TeacherWithdrawals bean = new TeacherWithdrawals();
		bean.loadFromCursor((Cursor) mAdapter.getItem(position - 1));
		intent.putExtra("bean", bean);
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			// 刷新
			loadFirstData();
		}
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
						// 还原页码
						restorePage();
					}
				});
	}

}
