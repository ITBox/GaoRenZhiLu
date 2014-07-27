package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.adapter.ProblemMsgAdapter;
import com.itbox.grzl.bean.EventCommentGet;
import com.itbox.grzl.bean.ProblemMsg;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.bean.UserProblem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.ConsultationEngine.UserProblemDetailItem;
import com.zhaoliewang.grzl.R;

/**
 * 我的详情页面
 * 
 * @author baoboy
 * @date 2014-5-26下午11:59:32
 */
public class MyProblemDetailActivity extends BaseLoadActivity<ProblemMsg> {

	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	private ProblemMsgAdapter mAdapter;
	private UserProblem mBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_problem_detail);
		mBean = getIntent().getParcelableExtra("bean");
		if (mBean == null) {
			finish();
			return;
		}

		ButterKnife.inject(this);

		// 清空评论数据库，防止显示别的评论
		new Delete().from(ProblemMsg.class).execute();

		initView();
	}

	private void initView() {
		setTitle("咨询详情");
		showLeftBackButton();

		mAdapter = new ProblemMsgAdapter(getContext(), null);
		initLoad(mListView, mAdapter, ProblemMsg.class);
		mListView.setMode(Mode.PULL_FROM_START);
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loder, Cursor cursor) {
		super.onLoadFinished(loder, cursor);
		mAdapter.notifyDataSetInvalidated();
	}

	@OnClick(R.id.bt_send)
	public void onClick(View v) {
		// 评论
		String content = mContentEt.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("请输入内容");
			return;
		}
		showProgressDialog("正在提交...");
		ConsultationEngine.sendMsg(mBean.getProblemId() + "", content,
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
							showToast("发送成功");
							mContentEt.setText("");
							loadFirstData();
						} else {
							showToast("发送失败");
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						showToast(content);
					}
				});
	}

	/**
	 * 从网络加载数据
	 */
	protected void loadData(final int page) {
		ConsultationEngine.getMsgList(mBean.getProblemId() + "", page,
				new LoadResponseHandler<UserProblemDetailItem>(this,
						UserProblemDetailItem.class) {
					@Override
					public void onSuccess(UserProblemDetailItem item) {
						saveData(page, item.getUserProblemDetailItem());
					}
				});
	}

}
