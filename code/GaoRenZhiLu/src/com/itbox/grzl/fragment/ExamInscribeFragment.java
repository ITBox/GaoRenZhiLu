package com.itbox.grzl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;

import com.itbox.grzl.R;
import com.itbox.grzl.bean.ExamInscribe;

/**
 * 测评试题页面
 * 
 * @author byz
 * @date 2014-5-10下午11:15:26
 */
public class ExamInscribeFragment extends BaseFragment {

	@InjectView(R.id.tv_content)
	protected TextView mContentTv;
	@InjectView(R.id.rb_option_a)
	protected RadioButton mOptionARb;
	@InjectView(R.id.rb_option_b)
	protected RadioButton mOptionBRb;
	private ExamInscribe mBean;

	public static ExamInscribeFragment newInstance(ExamInscribe bean) {
		ExamInscribeFragment f = new ExamInscribeFragment();
		// f.setArguments(new Bundle());
		// f.getArguments().putParcelable("bean", bean);
		f.setInscribe(bean);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragment_exam_inscribe, null);

		ButterKnife.inject(this, view);

		if (mBean != null) {
			mContentTv.setText(mBean.getInscribe());
			mOptionARb.setText(mBean.getOptionA());
			mOptionBRb.setText(mBean.getOptionB());
			if (ExamInscribe.SELECT_B.equals(mBean.getSelected())) {
				// 选择B
				mOptionBRb.setChecked(true);
			}
		}

		return view;
	}

	@OnCheckedChanged({ R.id.rb_option_a, R.id.rb_option_b })
	public void onCheckedChanged(RadioButton rb, boolean checked) {
		if (checked && mBean != null) {
			switch (rb.getId()) {
			case R.id.rb_option_a:
				mBean.setSelected(ExamInscribe.SELECT_A);
				break;
			case R.id.rb_option_b:
				mBean.setSelected(ExamInscribe.SELECT_B);
				break;
			}
		}
	}

	public void setInscribe(ExamInscribe bean) {
		mBean = bean;
	}
}
