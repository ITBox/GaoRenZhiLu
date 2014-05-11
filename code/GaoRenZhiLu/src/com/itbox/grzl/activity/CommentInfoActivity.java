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

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.ExamInscribe;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.ExamEngine;
import com.itbox.grzl.fragment.ExamInscribeFragment;

/**
 * 论坛详情界面
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentInfoActivity extends BaseActivity {

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
		setContentView(R.layout.activity_exam_report);

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
			break;
		case R.id.bt_pre:
			// 上一题
			mIndex--;
			break;
		}
		jump();
	}

	/**
	 * 跳转题目
	 * 
	 * @param index
	 */
	private void jump() {
		if (mIndex < 0) {
			showToast("已经是第一页");
			mIndex = 0;
			return;
		}
		if (mIndex == mList.size()) {
			mIndex--;
			submit();
			return;
		}
		if (mIndex == (mList.size() - 1)) {
			mNextBt.setText("提交");
		} else {
			mNextBt.setText("下一题");
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
		// R.anim.slide_left_in, R.anim.slide_right_out);
		ft.replace(R.id.fragment_exam_inscribe,
				ExamInscribeFragment.newInstance(mList.get(mIndex)));
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		// ft.addToBackStack(null); // 可以返回上一个
		ft.commit();
	}

	private void submit() {
		showProgressDialog("正在提交");
		ExamEngine.submit(mList, new GsonResponseHandler<RespResult>(
				RespResult.class) {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onSuccess(RespResult result) {
				super.onSuccess(result);
				if (result.isSuccess()) {
					showToast("提交成功");
				} else {
					showToast("提交失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showToast(content);
			}
		});
	}
}
