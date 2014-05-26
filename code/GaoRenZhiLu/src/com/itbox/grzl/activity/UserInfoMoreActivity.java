package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.EditTextUtils;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Account;

import android.content.Intent;
import android.os.Bundle;
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
	@InjectView(R.id.more_my_bankcard)
	EditText mEtUserInfoBankCard;
	@InjectView(R.id.more_my_bankcard_name)
	EditText mEtUserInfoBankCardName;

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
    
	@OnClick({ R.id.text_left, R.id.more_my_name_iv, R.id.more_my_shenfenzheng_iv, R.id.more_my_bankcard_iv, R.id.more_my_bankcard_name_iv})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.more_my_name_iv:
			EditTextUtils.showKeyboard(mEtUserInfoName);
			EditTextUtils.setSelection(mEtUserInfoName);
			break;
		
		case R.id.more_my_shenfenzheng_iv:
			break;
		case R.id.more_my_bankcard_iv:// 银行卡号
			EditTextUtils.showKeyboard(mEtUserInfoBankCard);
		    EditTextUtils.setSelection(mEtUserInfoBankCard);
			break;
		case R.id.more_my_bankcard_name_iv:// 开户行名称
			EditTextUtils.showKeyboard(mEtUserInfoBankCardName);
			EditTextUtils.setSelection(mEtUserInfoBankCardName);
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
