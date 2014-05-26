package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.DateUtil;
import com.itbox.fx.util.Utils;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.common.db.AreaListDB;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 * @author youzh
 *
 */
public class UserInfoMoreActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.more_my_name_et)
	EditText mEtUserInfoName;

	private Account account;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_info_more);
		ButterKnife.inject(mActThis);
		account = AppContext.getInstance().getAccount();
		initViews();
		initDatas();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("个人资料");
		mTVTopMedium.setText("更多资料");
	}

	private void initDatas() {
	}
	
    @Override
    protected boolean onBack() {
    	// TODO Auto-generated method stub
    	return true;
    }
    
	@OnClick({ R.id.text_left, R.id.more_my_name_iv, R.id.more_my_shenfenzheng_iv,})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.more_my_name_iv:
			Utils.showKeyboard(mEtUserInfoName);
			String userName = mEtUserInfoName.getText().toString();
			if (!TextUtils.isEmpty(userName)) {
				mEtUserInfoName.setSelection(userName.length());
			}
			break;
		
		case R.id.more_my_shenfenzheng_iv:
			break;
	
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 20:
			break;
		}
	}

}
