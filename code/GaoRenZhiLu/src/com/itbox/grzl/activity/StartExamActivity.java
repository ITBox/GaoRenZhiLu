package com.itbox.grzl.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.ExamInscribe;
import com.itbox.grzl.engine.ExamEngine;
import com.itbox.grzl.fragment.ExamInscribeFragment;

/**
 * 开始测评界面
 * 
 * @author byz
 * @date 2014-5-10下午11:03:09
 */
public class StartExamActivity extends BaseActivity {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.bt_pre)
	protected Button mPreBt;
	@InjectView(R.id.bt_next)
	protected Button mNextBt;

	private int mIndex;

	private List<ExamInscribe> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_exam);

		ButterKnife.inject(this);

		// 获取测试题
		mList = ExamEngine.getExamInscribes();

		initView();
	}

	private void initView() {
		mTitleTv.setText("单选题");

		// 添加第一题
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_exam_inscribe,
				ExamInscribeFragment.newInstance(mList.get(mIndex)));
		ft.commit();
	}

	@OnClick({ R.id.bt_next, R.id.bt_pre })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			// 下一题
			mIndex++;
			if (mIndex < mList.size()) {
				jump();
			} else {
				mIndex--;
				showToast("提交");
				ExamEngine.submit(mList, new ResponseHandler());
			}
			break;
		case R.id.bt_pre:
			// 上一题
			mIndex--;
			if (mIndex < 0) {
				mIndex = 0;
				showToast("已经是第一题");
			} else {
				jump();
			}
			break;
		}
	}

	/**
	 * 跳转题目
	 * 
	 * @param index
	 */
	private void jump() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_exam_inscribe,
				ExamInscribeFragment.newInstance(mList.get(mIndex)));
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
}
