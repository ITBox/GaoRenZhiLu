package com.itbox.grzl.fragment;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.activity.EventSearchActivity;
import com.itbox.grzl.adapter.EventListAdapter;
import com.itbox.grzl.bean.EventAdd;
import com.itbox.grzl.bean.EventGet;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.ActivityIdItem;

/**
 * 活动主页
 * 
 * @author baoboy
 * @date 2014-5-24下午5:59:58
 */
public class EventFragment extends BaseLoadFragment<EventGet> {
	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.text_right)
	protected TextView mRightTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	private EventListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_event, null);

		ButterKnife.inject(this, view);

		initView();

		mAdapter = new EventListAdapter(getActivity(), null);
		initLoad(mListView, mAdapter, EventGet.class);

		return view;
	}

	private void initView() {
		mTitleTv.setText("活动首页");
		mRightTv.setVisibility(View.VISIBLE);
		mRightTv.setBackgroundResource(R.drawable.ic_search);
	}

	@OnClick(R.id.text_right)
	public void onClick(View v) {
		startActivity(EventSearchActivity.class);
	}

	private void testAddEvent() {
		EventAdd bean = new EventAdd();
		bean.setActivitydescription("test");
		bean.setActivityphone("110");
		bean.setActivitypicture("photo");
		bean.setAddress("北京市XXX");
		bean.setBegintime("2014-5-22");
		bean.setEndtime("2014-5-25");
		bean.setLatitude("116.403874");
		bean.setLongitude("39.914889");
		bean.setPeoplecount("20");
		bean.setTitle("EventTitle");
		bean.setTypeid("2");
		bean.setUsercity("110000");
		bean.setUserdistrict("110101");
		bean.setUserprovince("100000");
		EventEngine.addEvent(bean, null);
	}

	@Override
	protected void loadData(final int page) {
		EventEngine.searchEvent("", null, null, "", page,
				new GsonResponseHandler<ActivityIdItem>(ActivityIdItem.class) {
					@Override
					public void onSuccess(ActivityIdItem item) {
						saveData(page, item.getActivityIdItem());
					}
					@Override
					public void onFinish() {
						loadFinish();
					}
				});
	}
}
