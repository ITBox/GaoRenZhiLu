package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.net.GsonResponseHandler;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.adapter.TeacherCommentAdapter;
import com.itbox.grzl.bean.TeacherCommentGet;
import com.itbox.grzl.engine.TeacherEngine;
import com.itbox.grzl.engine.TeacherEngine.TeacherCommentItem;

/**
 * 我得评论页面
 * 
 * @author baoboy
 * @date 2014-5-28下午11:20:05
 */
public class TeacherCommentListActivity extends
		BaseLoadActivity<TeacherCommentGet> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private TeacherCommentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_comment);

		ButterKnife.inject(this);

		initView();

	}

	private void initView() {
		mTitleTv.setText("我的评价");
		showLeftBackButton();
		
		startActivity(EventTeacherActivity.class);

		mAdapter = new TeacherCommentAdapter(getContext(), null);
		initLoad(mListView, mAdapter, TeacherCommentGet.class);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			// 刷新
			loadFirstData();
		}
	}

	@Override
	protected void loadData(final int page) {
		TeacherEngine
				.getCommentList(new GsonResponseHandler<TeacherCommentItem>(
						TeacherCommentItem.class) {
					@Override
					public void onSuccess(TeacherCommentItem bean) {
						// 保存到数据库
						if (bean != null) {
							saveData(page, bean.getTeacherCommentItem());
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
