package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.EventCommentAdapter;
import com.itbox.grzl.bean.EventCommentGet;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.ActivityUserCommentItem;

/**
 * 论坛交流页面
 * 
 * @author baoboy
 * @date 2014-5-26下午11:59:32
 */
public class EventCommentActivity extends BaseLoadActivity<EventCommentGet> {

	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	private EventCommentAdapter mAdapter;
	private String mActivityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_comment);
		mActivityId = getIntent().getStringExtra("activityid");
		if (mActivityId == null) {
			finish();
			return;
		}

		ButterKnife.inject(this);

		// 清空评论数据库，防止显示别的评论
		new Delete().from(EventCommentGet.class).execute();

		initView();
	}

	private void initView() {
		setTitle("活动交流");
		showLeftBackButton();

		mAdapter = new EventCommentAdapter(getContext(), null);
		initLoad(mListView, mAdapter, EventCommentGet.class);
		mListView.setMode(Mode.PULL_FROM_END);
	}

	@OnClick(R.id.bt_mark)
	public void onClick(View v) {
		// 评论
		String content = mContentEt.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("请输入内容");
			return;
		}
		showProgressDialog("正在提交...");
		EventEngine.addEventComment(mActivityId, content,
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
							showToast("发布成功");
							mContentEt.setText("");
							loadFirstData();
						} else {
							showToast("发布失败");
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
		EventEngine.getEventComment(mActivityId, page,
				new GsonResponseHandler<ActivityUserCommentItem>(
						ActivityUserCommentItem.class) {
					@Override
					public void onSuccess(ActivityUserCommentItem bean) {
						// 保存到数据库
						saveData(page, bean.getActivityUserCommentItem());
					}

					@Override
					public void onFinish() {
						super.onFinish();
						loadFinish();
					}

				});
	}

}
