package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.zhaoliewang.grzl.R;

/**
 * 更多收入
 * 
 * @author baoyz
 * @date 2014-10-20
 * 
 */
public class MoreShouruActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_more_shouru);
		ButterKnife.inject(mActThis);
		
		showLeftBackButton();
		setTitle("我的收入");
	}

	@OnClick({ R.id.more_my_shouru, R.id.more_my_tixian })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.more_my_shouru:
			startActivity(TeacherIncomingActivity.class);
			break;
		case R.id.more_my_tixian:
			startActivity(TeacherWithdrawalsListActivity.class);
			break;
		}
	}
}
