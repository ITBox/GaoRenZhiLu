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
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.adapter.EventListAdapter;
import com.itbox.grzl.bean.EventSearchGet;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.MyEventItem;
import com.itbox.grzl.enumeration.EventState;
import com.itbox.grzl.enumeration.EventType;
import com.itbox.grzl.widget.SearchBar;
import com.itbox.grzl.widget.SearchBar.OnSearchListener;

/**
 * 老师的活动（课程）
 * 
 * @author baoboy
 * @date 2014-5-29下午11:46:21
 */
public class EventTeacherActivity extends BaseLoadActivity<EventSearchGet>
		implements OnSearchListener {

	private static final int REQUEST_SELECT_AREA = 1;

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected ListView mListView;
	@InjectView(R.id.searchBar)
	protected SearchBar mSearchBar;
	private CursorAdapter mAdapter;
	private String mTitle;
	private String mUserDistrict;
	private EventType mType;
	private EventState mState;
	@InjectView(R.id.tv_empty)
	protected View mEmptyView;

	private UserListItem mTeacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_search);

		mTeacher = (UserListItem) getIntent().getSerializableExtra("teacher");
		if (mTeacher == null) {
			finish();
			return;
		}

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText(mTeacher.getUserrealname() + "的课程");
		mSearchBar.setOnSearchListener(this);
		showLeftBackButton();
		mListView.setEmptyView(mEmptyView);
		mAdapter = new EventListAdapter(this, null);
		initLoad(mListView, mAdapter, EventSearchGet.class,
				EventSearchGet.USERID + "=" + mTeacher.getUserid());
	}

	@OnClick({ R.id.tv_address, R.id.tv_type, R.id.tv_state })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_address:
			// 选择地区
			startActivityForResult(SelectAddrActivity.class,
					REQUEST_SELECT_AREA);
			break;
		case R.id.tv_type:
			// 选择类型
			new AlertDialog.Builder(this).setItems(EventType.getAllName(),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mType = EventType.getByIndex(which);
							startSearch();
						}
					}).show();
			break;
		case R.id.tv_state:
			// 选择状态
			new AlertDialog.Builder(this).setItems(EventState.getAllName(),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mState = EventState.getByIndex(which);
							startSearch();
						}
					}).show();
			break;
		}
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

	@Override
	public void onSearch(String keyword) {
		// 开始搜索
		mTitle = keyword;
		startSearch();
	}

	private void startSearch() {
		showProgressDialog("正在搜索...");
		loadFirstData();
	}

	@Override
	protected void loadData(final int page) {
		EventEngine.getUserEvent(mTeacher.getUserid(), mUserDistrict, mType,
				mState, true, page, new GsonResponseHandler<MyEventItem>(
						MyEventItem.class) {
					@Override
					public void onSuccess(MyEventItem item) {
						saveData(page, item.getActivityIdItem());
					}

					@Override
					public void onFinish() {
						dismissProgressDialog();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						saveData(1, null);
					}
				});
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
