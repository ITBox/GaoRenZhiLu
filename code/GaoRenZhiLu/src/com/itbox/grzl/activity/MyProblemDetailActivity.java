package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.ProblemMsgAdapter;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.ProblemMsg;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.bean.UserProblem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.UserEngine;
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
	@InjectView(R.id.text_right)
	protected TextView mRightTv;
	@InjectView(R.id.tv_finish)
	protected TextView mFinishTv;
	@InjectView(R.id.ll_edit)
	protected View mEditView;
	@InjectView(R.id.bt_send)
	protected Button mSendBt;
	@InjectView(R.id.rb_score)
	protected RatingBar mScoreRb;

	private ProblemMsgAdapter mAdapter;
	private UserProblem mBean;
	private Account mAccount;

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

		mAccount = AppContext.getInstance().getAccount();

		if (!mBean.isSelf()) {
			mRightTv.setVisibility(View.VISIBLE);
			mRightTv.setText("完成对话");
		}

		mAdapter = new ProblemMsgAdapter(getContext(), null);
		initLoad(mListView, mAdapter, ProblemMsg.class);
		mListView.setMode(Mode.PULL_FROM_START);
		initStatus();
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loder, Cursor cursor) {
		super.onLoadFinished(loder, cursor);
		mListView.scrollBottom();
	}

	@OnClick(R.id.text_right)
	public void onFinishClick(View v) {
		showProgressDialog("正在结束对话...");
		ConsultationEngine.finishConsulation(mBean.getProblemId() + "",
				new GsonResponseHandler<RespResult>(RespResult.class) {
					@Override
					public void onFinish() {
						dismissProgressDialog();
					}

					@Override
					public void onSuccess(RespResult resp) {
						if (resp.isSuccess()) {
							showToast("结束对话成功");
							mBean.setFinish();
							initStatus();
						} else {
							showToast("结束对话失败");
						}
					}
				});
	}

	private void initStatus() {
		if (mBean.isFinish()) {
			mEditView.setVisibility(View.GONE);
			mFinishTv.setVisibility(View.VISIBLE);
			mRightTv.setVisibility(View.GONE);
			mScoreRb.setVisibility(View.GONE);
			if (mBean.isSelf() && !mBean.isRemark()) {
				mScoreRb.setVisibility(View.VISIBLE);
				mEditView.setVisibility(View.VISIBLE);
				mSendBt.setText("评价");
			}
		}

	}

	@OnClick(R.id.bt_send)
	public void onClick(View v) {
		String content = mContentEt.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("请输入内容");
			return;
		}
		showProgressDialog("正在提交...");
		if (mBean.isFinish()) {
			// 评价
			ConsultationEngine.comment(mBean.getTeacherid(), content,
					mScoreRb.getProgress() + "", mBean.getProblemId() + "",
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
								showToast("评论成功");
								mContentEt.setText("");
								mBean.setRemark();
								initStatus();
							} else {
								showToast("评论失败");
							}
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							showToast(content);
						}
					});
			return;
		}
		// 发送消息
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
