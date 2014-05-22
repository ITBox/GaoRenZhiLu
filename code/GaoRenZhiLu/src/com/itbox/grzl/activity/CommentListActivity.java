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

import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.CommentListAdapter;
import com.itbox.grzl.bean.CommentGet;
import com.itbox.grzl.engine.CommentEngine;
import com.itbox.grzl.engine.CommentEngine.CommentItem;

/**
 * 论坛列表界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentListActivity extends BaseLoadActivity<CommentGet> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.text_right)
	protected TextView mRightTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private CommentListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("行业论坛");
		// 右侧发布按钮
		mRightTv.setText("发布论坛");
		mRightTv.setVisibility(View.VISIBLE);

		mAdapter = new CommentListAdapter(getContext(), null);
		initLoad(mListView, mAdapter, CommentGet.class);
	}

	@OnClick(R.id.text_right)
	public void onClick(View v) {
		// 进入发布论坛页面
		startActivityForResult(CommentAddActivity.class, 0);
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
		CommentEngine.getComment(page, new GsonResponseHandler<CommentItem>(
				CommentItem.class) {
			@Override
			public void onSuccess(CommentItem bean) {
				// 保存到数据库
				if (bean != null) {
					saveData(page, bean.getCommentItem());
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				loadFinish();
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				// 还原页码
				restorePage();
			}
		});
	}
}
