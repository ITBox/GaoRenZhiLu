package com.itbox.grzl.fragment;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.activity.CommentAddActivity;
import com.itbox.grzl.activity.CommentInfoActivity;
import com.itbox.grzl.adapter.CommentListAdapter;
import com.itbox.grzl.bean.CommentGet;
import com.itbox.grzl.engine.CommentEngine;
import com.itbox.grzl.engine.CommentEngine.CommentItem;

/**
 * 论坛主页
 * 
 * @author baoboy
 * @date 2014-5-24下午5:59:58
 */
public class CommentFragment extends BaseLoadFragment<CommentGet> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.text_right)
	protected TextView mRightTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private CommentListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comment, null);

		ButterKnife.inject(this, view);

		initView();

		return view;
	}

	private void initView() {
		mTitleTv.setText("行业论坛");
		// 右侧发布按钮
		mRightTv.setText("发布论坛");
		mRightTv.setVisibility(View.VISIBLE);

		mAdapter = new CommentListAdapter(getActivity(), null);
		initLoad(mListView, mAdapter, CommentGet.class);
	}

	@Override
	protected int getLoaderId() {
		return super.getLoaderId() + 1;
	}

	@OnClick(R.id.text_right)
	public void onClick(View v) {
		// 进入发布论坛页面
		startActivityForResult(CommentAddActivity.class, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadFirstData();
	}

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 进入论坛详情页面
		Intent intent = new Intent(getActivity(), CommentInfoActivity.class);
		CommentGet bean = new CommentGet();
		bean.loadFromCursor((Cursor) mAdapter.getItem(position - 1));
		intent.putExtra("bean", bean);
		startActivity(intent);
	}
}
