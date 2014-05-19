package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.TeacherIncomingAdapter;
import com.itbox.grzl.bean.TeacherIncoming;
import com.itbox.grzl.engine.TeacherEngine;
import com.itbox.grzl.engine.CommentEngine.CommentItem;
import com.itbox.grzl.engine.TeacherEngine.UserPayDetailItem;

/**
 * 收入明细界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class TeacherIncomingActivity extends BaseLoadActivity<TeacherIncoming> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private TeacherIncomingAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_incoming);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("收入明细");

		mAdapter = new TeacherIncomingAdapter(getContext(), null);
		initLoad(mListView, mAdapter, TeacherIncoming.class);
	}

	@Override
	protected void loadData(final int page) {
		TeacherEngine.getIncoming(page, AppContext.getInstance().getAccount()
				.getUserid(), new GsonResponseHandler<UserPayDetailItem>(
				UserPayDetailItem.class) {
			@Override
			public void onSuccess(UserPayDetailItem bean) {
				// 保存到数据库
				if (bean != null) {
					saveData(page, bean.getUserPayDetailItem());
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
				showToast(content);
				// 还原页码
				restorePage();
			}
		});
	}
}
