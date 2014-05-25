package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.EventListAdapter;
import com.itbox.grzl.bean.EventSearchGet;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.engine.EventEngine.SearchItem;
import com.itbox.grzl.enumeration.EventState;
import com.itbox.grzl.enumeration.EventType;
import com.itbox.grzl.widget.SearchBar;
import com.itbox.grzl.widget.SearchBar.OnSearchListener;

/**
 * 活动搜索页面
 * 
 * @author baoboy
 * @date 2014-5-24下午11:22:30
 */
public class EventSearchActivity extends BaseLoadActivity<EventSearchGet>
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_search);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("活动搜索");
		mSearchBar.setOnSearchListener(this);

		mAdapter = new EventListAdapter(this, null);
		initLoad(mListView, mAdapter, EventSearchGet.class);
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
		EventEngine.searchEvent(mUserDistrict, mType, mState, mTitle, page,
				new GsonResponseHandler<SearchItem>(SearchItem.class) {
					@Override
					public void onSuccess(SearchItem item) {
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
}
