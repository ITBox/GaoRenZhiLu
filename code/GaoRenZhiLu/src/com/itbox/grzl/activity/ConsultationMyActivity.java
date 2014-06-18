package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.EventListAdapter;
import com.itbox.grzl.bean.EventSearchGet;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.MyEventItem;
import com.itbox.grzl.enumeration.EventState;
import com.itbox.grzl.enumeration.EventType;
import com.zhaoliewang.grzl.R;

/**
 * 我的咨询页面
 * 
 * @author baoboy
 * @date 2014-5-24下午11:22:30
 */
public class ConsultationMyActivity extends BaseLoadActivity<EventSearchGet> {

	private static final int REQUEST_SELECT_AREA = 1;

	@InjectView(R.id.lv_list)
	protected ListView mListView;
	@InjectView(R.id.tv_empty)
	protected View mEmptyView;

	private CursorAdapter mAdapter;
	private String mUserDistrict;
	private EventType mType;
	private EventState mState;
	private boolean isMy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_my);

		ButterKnife.inject(this);

		isMy = true;
		initView();
	}

	private void initView() {
		setTitle("我的咨询");
		showLeftBackButton();
		mListView.setEmptyView(mEmptyView);
		mAdapter = new EventListAdapter(this, null);
		initLoad(mListView, mAdapter, EventSearchGet.class);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (RESULT_OK == resultCode && null != data) {
			mUserDistrict = Integer.toString(data.getIntExtra(
					SelectAddrActivity.Extra.DistrictCode, 0));
			startSearch();
		}
	}

	private void startSearch() {
		showProgressDialog("正在搜索...");
		loadFirstData();
	}

	@Override
	protected void loadData(final int page) {
		ConsultationEngine.getMyConsultation(AppContext.getInstance()
				.getAccount().getUserid().toString(), "", "1", "0", 1, null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		EventSearchGet bean = new EventSearchGet();
		bean.loadFromCursor((Cursor) mAdapter.getItem(position));
		Intent intent = new Intent(this, EventDetialActivity.class);
		intent.putExtra("activityid", bean.getActivityId());
		startActivity(intent);
	}
}
