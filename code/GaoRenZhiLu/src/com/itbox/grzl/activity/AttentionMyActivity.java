package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.adapter.AttentionListAdapter;
import com.itbox.grzl.adapter.CommentListAdapter;
import com.itbox.grzl.bean.Attention;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.UserEngine;
import com.itbox.grzl.engine.UserEngine.UserAttention;
import com.zhaoliewang.grzl.R;

/**
 * 我的关注
 * 
 * @author baoyz
 * 
 * @date 2014-7-1 下午6:50:35
 */
public class AttentionMyActivity extends BaseLoadActivity<Attention> {

	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private AttentionListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention_list);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		setTitle("我的关注");
		showLeftBackButton();

		mAdapter = new AttentionListAdapter(getContext(), null);
		initLoad(mListView, mAdapter, Attention.class);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		// 刷新
		loadFirstData();
	}

	/**
	 * 从网络加载数据
	 */
	@Override
	protected void loadData(final int page) {
		UserEngine.getAttention(page, new LoadResponseHandler<UserAttention>(
				this, UserAttention.class) {
			@Override
			public void onSuccess(UserAttention object) {
				saveData(page, object.getUserAttention());
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Attention bean = new Attention();
//		bean.loadFromCursor((Cursor) mAdapter.getItem(position));
//		UserEngine.getUserList(userid, handler)
//		UserListItem teacher = new UserListItem();
//		teacher.loadFromCursor(cursor);
//		Intent intent = new Intent(ConsultationSearchActivity.this,
//				TeacherDetialActivity.class);
//		intent.putExtra("teacher", teacher);
//		startActivity(intent);
	}
}
