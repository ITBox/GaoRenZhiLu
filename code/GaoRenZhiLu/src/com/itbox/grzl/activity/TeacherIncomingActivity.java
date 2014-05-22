package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Update;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.TeacherIncomingAdapter;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.TeacherIncoming;
import com.itbox.grzl.constants.AccountTable;
import com.itbox.grzl.engine.TeacherEngine;
import com.itbox.grzl.engine.TeacherEngine.UserPayDetailItem;

/**
 * 收入明细界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherIncomingActivity extends BaseLoadActivity<TeacherIncoming> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.text_right)
	protected TextView mRightTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.tv_total)
	protected TextView mTotalTv;
	@InjectView(R.id.tv_balance)
	protected TextView mBalanceTv;

	private TeacherIncomingAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_incoming);

		ButterKnife.inject(this);

		initView();
		initData();
	}

	private void initData() {
		TeacherEngine.getUserList(new GsonResponseHandler<Account>(
				Account.class) {
			@Override
			public void onSuccess(Account user) {
				super.onSuccess(user);
				if (user != null) {
					// 更新用户资金信息
					Account account = AppContext.getInstance().getAccount();
					account.setUsertotalamount(user.getUsertotalamount());
					account.setUserbalance(user.getUserbalance());
					// 更细数据库
					new Update(Account.class).set(
							AccountTable.COLUMN_USERTOTALAMOUNT + "=?, "
									+ AccountTable.COLUMN_USERBALANCE + "=?",
							new Object[] { user.getUsertotalamount(),
									user.getUserbalance() }).execute();
					initMoney();
				}
			}
		});
	}

	private void initView() {
		mTitleTv.setText("收入明细");
		mRightTv.setVisibility(View.VISIBLE);
		mRightTv.setText("申请提现");
		initMoney();

		mAdapter = new TeacherIncomingAdapter(getContext(), null);
		initLoad(mListView, mAdapter, TeacherIncoming.class);
	}

	private void initMoney() {
		mTotalTv.setText(AppContext.getInstance().getAccount()
				.getUsertotalamount()
				+ "元");
		mBalanceTv.setText(AppContext.getInstance().getAccount()
				.getUserbalance()
				+ "元");
	}

	@OnClick(R.id.text_right)
	public void onClick(View v) {
		// 进入申请提现页面
		startActivityForResult(TeacherWithdrawalsAddActivity.class, 0);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		loadFirstData();
	}

	@Override
	protected void loadData(final int page) {
		TeacherEngine.getIncoming(page, AppContext.getInstance().getAccount()
				.getUserid(), new GsonResponseHandler<UserPayDetailItem>(
				UserPayDetailItem.class) {
			@Override
			public void onSuccess(UserPayDetailItem bean) {
				// 保存到数据库
				if (bean != null) {
					saveData(page, bean.getUserPayDetailItem());
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
