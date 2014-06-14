package com.itbox.grzl.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.widget.HorizontalListView;
import com.itbox.grzl.Api;
import com.itbox.grzl.Const;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.adapter.JoinUserAdapter;
import com.itbox.grzl.bean.EventCommentGet;
import com.itbox.grzl.bean.EventDetailGet;
import com.itbox.grzl.bean.EventUser;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.ActivityDetail;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 活动详情页面
 * 
 * @author baoboy
 * @date 2014-5-24下午11:22:30
 */
public class EventDetialActivity extends BaseActivity {

	private EventDetailGet mBean;
	@InjectView(R.id.bt_join)
	protected Button bt_join;
	@InjectView(R.id.bt_like)
	protected Button bt_like;
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
	@InjectView(R.id.tv_introduction_content)
	protected TextView tv_introduction_content;
	@InjectView(R.id.tv_join_person_count)
	protected TextView tv_join_person_count;
	@InjectView(R.id.ll_bottom)
	protected LinearLayout ll_bottom;

	// 参与用户列表
	@InjectView(R.id.hlv_user)
	protected HorizontalListView hlv_user;

	private List<EventUser> mUserItem;
	private List<EventCommentGet> mCommentList;
	private String mActivityId;

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

		// 获取详情信息
		EventEngine.getEventDetail(mActivityId,
				new GsonResponseHandler<ActivityDetail>(ActivityDetail.class) {

					@Override
					public void onSuccess(ActivityDetail bean) {
						mBean = bean.getActivity();
						mUserItem = bean.getActivityUserItem();
						mCommentList = bean.getActivityUserCommentItem();
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
		tv_address.setText(mBean.getAddress());
		tv_location.setText(mBean.getAddress());
		tv_person_count.setText(mBean.getPeoplecount() + "人");
		tv_time.setText(mBean.getBegintime() + " - " + mBean.getEndtime());
		tv_title.setText(mBean.getTitle());
		tv_type.setText(mBean.getTypeName());
		tv_introduction_content.setText(mBean.getActivitydescription());

		bt_join.setText(mBean.isJoin() ? "已报名" : "我要报名");
		bt_like.setText("感兴趣(" + mBean.getActivityinterestcount() + ")");

		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(mBean.getActivitypicture()), iv_head);

		tv_join_person_count.setText("参与人员(" + mBean.getJoinnumber() + "/"
				+ mBean.getPeoplecount() + ")");

		// 参与用户
		if (mUserItem != null) {
			hlv_user.setAdapter(new JoinUserAdapter(getApplicationContext(),
					mUserItem));
		}
		// 用户评论
		if (mCommentList != null) {
			for (EventCommentGet comment : mCommentList) {
				View view = View.inflate(getApplicationContext(),
						R.layout.item_event_detail_comment, ll_bottom);
				((TextView) view.findViewById(R.id.tv_name)).setText(comment
						.getUsername());
				((TextView) view.findViewById(R.id.tv_content)).setText(comment
						.getCommentcontent());
				((TextView) view.findViewById(R.id.tv_time)).setText(comment
						.getCreateTime());
			}
		}
	}

	@OnClick({ R.id.bt_join, R.id.bt_like, R.id.bt_comment, R.id.tv_location })
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
			Intent intent = new Intent(this, EventCommentActivity.class);
			intent.putExtra("activityid", mBean.getActivityId());
			startActivity(intent);
			break;
		case R.id.tv_location:
			// 打开地图
			goMap();
			break;
		}
	}

	/**
	 * 感兴趣
	 */
	private void like() {
		if (!mBean.isInterest()) {
			mBean.setInterest(true);
			bt_like.setText("感兴趣(" + (mBean.getActivityinterestcount() + 1)
					+ ")");
			EventEngine.addInterestEvent(mBean.getActivityId(),
					new GsonResponseHandler<RespResult>(RespResult.class) {
						@Override
						public void onFailure(Throwable e, int statusCode,
								String content) {
							showToast(content);
						}
					});
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

}
