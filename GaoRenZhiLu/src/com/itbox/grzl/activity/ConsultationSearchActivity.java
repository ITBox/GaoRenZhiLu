package com.itbox.grzl.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.R;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.ConsultationEngine.GetTeacher;

/**
 * 咨询搜索页面
 * @author baoyz 
 * 
 * 2014-5-3 下午3:54:06
 *
 */
public class ConsultationSearchActivity extends BaseActivity {
	
	@InjectView(R.id.text_medium)
	protected TextView mTitleTt;
	@InjectView(R.id.lv_list)
	protected ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_search);
		
		ButterKnife.inject(this);
		
		initView();
	}
	
	private void initView(){
		mTitleTt.setText("咨询搜索");
		
		GetTeacher info = new GetTeacher();
		info.setOrderby(1);
		info.setPagesize(10);
		info.setPageindex(1);
		info.setTeachertype(1);
		info.setJobtype(1);
		ConsultationEngine.getTeacher(info , null);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
