package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.adapter.CommentListAdapter;
import com.itbox.grzl.bean.CommentGet;
import com.itbox.grzl.engine.CommentEngine;
import com.itbox.grzl.engine.UserEngine;
import com.itbox.grzl.engine.CommentEngine.CommentItem;

/**
 * 我的关注
 * 
 * @author baoyz
 * 
 * @date 2014-7-1 下午6:50:35
 */
public class AttentionMyActivity extends BaseLoadActivity<CommentGet> {

	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private CommentListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention_list);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		setTitle("行业论坛");
		showLeftBackButton();

		mAdapter = new CommentListAdapter(getContext(), null);
		initLoad(mListView, mAdapter, CommentGet.class);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		// 刷新
		loadFirstData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 进入论坛详情页面
		Intent intent = new Intent(this, CommentInfoActivity.class);
		CommentGet bean = new CommentGet();
		bean.loadFromCursor((Cursor) mAdapter.getItem(position - 1));
		intent.putExtra("bean", bean);
		startActivity(intent);
	}

	/**
	 * 从网络加载数据
	 */
	@Override
	protected void loadData(final int page) {
		UserEngine.getAttention(page, null);
	}
}
