package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Account;
import com.zhaoliewang.grzl.R;

/**
 * 更多设置
 * 
 * @author baoyz
 * @date 2014-10-20
 * 
 */
public class MoreSettingsActivity extends BaseActivity {

	@InjectView(R.id.more_my_consulation_set)
	View more_my_consult;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_more_settings);
		ButterKnife.inject(mActThis);

		Account account = AppContext.getInstance().getAccount();
		if (account != null && account.getUsertype() == 2) {
			more_my_consult.setVisibility(View.GONE);
		}
		
		showLeftBackButton();
		setTitle("个人设置");
	}

	@OnClick({ R.id.more_my_set, R.id.more_my_consulation_set,
			R.id.more_my_settings})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.more_my_set:	// 个人资料设置
			startActivity(UserInfoActivity.class);
			break;
		case R.id.more_my_consulation_set:	// 咨询设置
			startActivity(UserInfoMoreActivity.class);
			break;
		case R.id.more_my_settings:	// 功能设置
			startActivity(UserSetActivity.class);
			break;
		}
	}
}
