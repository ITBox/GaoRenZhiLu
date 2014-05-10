package com.itbox.grzl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.R;
import com.itbox.grzl.activity.ConsultationSearchActivity;
import com.itbox.grzl.activity.QuickAskActivity;

/**
 * 咨询页面
 * 
 * @author baoyz
 * 
 *         2014-5-2 下午6:13:07
 * 
 */
public class ConsultationFragment extends BaseFragment {

	@InjectView(R.id.gv_jobtype)
	protected GridView mGridView;
	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_consultation, null);

		ButterKnife.inject(this, view);

		initView();

		return view;
	}

	private void initView() {
		// 设置标题
		mTitleTv.setText("我要咨询");

		initAdapter();
	}

	private void initAdapter() {
//		mGridView.setAdapter(new ArrayAdapter<String>(getActivity(),
//				R.layout.item_grid_online_study, R.id.textview,
//				ConsultationEngine.JOB_TYPES));
	}

	@OnClick({R.id.bt_quick_ask, R.id.et_search})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_quick_ask:
			startActivity(new Intent(getActivity(), QuickAskActivity.class));
			break;
		case R.id.et_search:
			startActivity(new Intent(getActivity(), ConsultationSearchActivity.class));
			break;
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}
}
