package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.Const;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.EventCommentAdapter;
import com.itbox.grzl.bean.EventCommentGet;
import com.itbox.grzl.bean.EventDetailGet;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.ActivityDetail;
import com.itbox.grzl.engine.EventEngine.ActivityUserCommentItem;
import com.loopj.android.http.RequestParams;

/**
 * 活动搜索页面
 * 
 * @author baoboy
 * @date 2014-5-24下午11:22:30
 */
public class EventDetialActivity extends BaseLoadActivity<EventCommentGet> {

	private EventDetailGet mBean;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.bt_join)
	protected Button bt_join;
	@InjectView(R.id.bt_like)
	protected Button bt_like;

	private EventCommentAdapter mAdapter;
	private HeaderView mHeaderView;
	private String mActivityId;

	public class HeaderView {
		@InjectView(R.id.iv_head)
		protected ImageView iv_head;
		@InjectView(R.id.tv_time)
		protected TextView tv_time;
		@InjectView(R.id.tv_person_count)
		protected TextView tv_person_count;
		@InjectView(R.id.tv_address)
		protected TextView tv_address;
		@InjectView(R.id.tv_type)
		protected TextView tv_type;
		@InjectView(R.id.tv_title)
		protected TextView tv_title;
		@InjectView(R.id.tv_location)
		protected TextView tv_location;

		@OnClick(R.id.tv_location)
		public void onClick(View v) {
			goMap();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detial);

		mActivityId = getIntent().getStringExtra("activityid");
		if (mActivityId == null) {
			finish();
			return;
		}

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		setTitle("活动详情");
		showLeftBackButton();

		// 顶部详情
		// 添加头
		View header = View.inflate(getApplicationContext(),
				R.layout.item_top_event_detail, null);
		mListView.getRefreshableView().addHeaderView(header);

		mHeaderView = new HeaderView();
		ButterKnife.inject(mHeaderView, header);

		mAdapter = new EventCommentAdapter(this, null);
		initLoad(mListView, mAdapter, EventCommentGet.class);
		mListView.setMode(Mode.PULL_FROM_END);

		// 获取详情信息
		EventEngine.getEventDetail(mActivityId,
				new GsonResponseHandler<ActivityDetail>(ActivityDetail.class) {
					@Override
					public void onSuccess(ActivityDetail bean) {
						mBean = bean.getActivity();
						initDetail();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						showToast(content);
					}
				});
	}

	/**
	 * 初始化详情
	 */
	private void initDetail() {
		mHeaderView.tv_address.setText(mBean.getAddress());
		mHeaderView.tv_location.setText(mBean.getAddress());
		mHeaderView.tv_person_count.setText(mBean.getPeoplecount() + "人");
		mHeaderView.tv_time.setText(mBean.getBegintime() + " - "
				+ mBean.getEndtime());
		mHeaderView.tv_title.setText(mBean.getTitle());
		mHeaderView.tv_type.setText(mBean.getTypeName());

		bt_join.setText(mBean.isJoin() ? "已报名" : "我要报名");
		bt_like.setText("感兴趣()");
	}

	@OnClick({ R.id.bt_join, R.id.bt_like, R.id.bt_comment })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_join:
			// 参加
			join();
			break;
		case R.id.bt_like:
			// 感兴趣
			like();
			break;
		case R.id.bt_comment:
			// 活动交流
			break;
		}
	}

	/**
	 * 感兴趣
	 */
	private void like() {
		if (!mBean.isInterest()) {
		}
	}

	/**
	 * 报名
	 */
	private void join() {
		if (!mBean.isJoin()) {
			showProgressDialog("正在报名...");
			EventEngine.joinEvent(mBean.getActivityId(),
					new GsonResponseHandler<RespResult>(RespResult.class) {
						@Override
						public void onFinish() {
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(RespResult result) {
							if (result.isSuccess()) {
								showToast("报名成功");
								bt_join.setText("已报名");
							} else {
								showToast("报名失败");
							}
						}

						@Override
						public void onFailure(Throwable e, int statusCode,
								String content) {
							showToast(content);
						}
					});
		}
	}

	/**
	 * 打开地图
	 */
	private void goMap() {
		Intent intent = new Intent(this, EventAddrMapActivity.class);
		intent.putExtra(Const.Extra.Snippet, mBean.getAddress());
		intent.putExtra(Const.Extra.LatitudeE6,
				Float.parseFloat(mBean.getLatitude()));
		intent.putExtra(Const.Extra.LongitudeE6,
				Float.parseFloat(mBean.getLongitude()));
		startActivity(intent);
	}

	@Override
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
