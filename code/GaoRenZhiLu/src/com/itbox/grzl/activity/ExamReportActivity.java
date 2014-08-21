package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.activeandroid.query.Delete;
import com.itbox.fx.net.GsonResponseHandler;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.adapter.ExamReportAdapter;
import com.itbox.grzl.bean.ExamReport;
import com.itbox.grzl.bean.TeacherCommentGet;
import com.itbox.grzl.engine.ExamEngine;
import com.itbox.grzl.engine.ExamEngine.UserTestingItem;

/**
 * 测评报告界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class ExamReportActivity extends BaseLoadActivity<ExamReport> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private ExamReportAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_report);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("测评报告");
		showLeftBackButton();

		mAdapter = new ExamReportAdapter(getContext(), null);
		initLoad(mListView, mAdapter, ExamReport.class);
	}

	@Override
	protected void loadData(final int page) {
		ExamEngine
				.getExamReport(page, new GsonResponseHandler<UserTestingItem>(
						UserTestingItem.class) {
					@Override
					public void onSuccess(UserTestingItem bean) {
						// 保存到数据库
						saveData(page, bean.getUserTestingItem());
					}

					@Override
					public void onFinish() {
						super.onFinish();
						mListView.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable e, int statusCode,
							String content) {
						super.onFailure(e, statusCode, content);
						if (statusCode == 400 && page == 1) {
							new Delete().from(ExamReport.class).execute();
						}
						// 还原页码
						restorePage();
					}
				});
	}

}
