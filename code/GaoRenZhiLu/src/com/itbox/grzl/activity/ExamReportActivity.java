package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.R;
import com.itbox.grzl.engine.ExamEngine;

/**
 * 测评报告界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class ExamReportActivity extends BaseActivity {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_report);

		ButterKnife.inject(this);

		initView();
		initData();
	}

	private void initView() {
		mTitleTv.setText("测评报告");

	}
	
	private void initData(){
		ExamEngine.getExamReport(0, null);
	}
}
