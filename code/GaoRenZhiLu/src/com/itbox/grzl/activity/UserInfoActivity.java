package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.DateUtil;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfoActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.userinfo_photo)
	CircleImageView mUserInfoPhoto;
	@InjectView(R.id.userinfo_name)
	TextView mUserInfoName;
	@InjectView(R.id.userinfo_place)
	TextView mUserInfoPlace;
	@InjectView(R.id.userinfo_xingzuo)
	TextView mUserInfoXingzuo;
	@InjectView(R.id.userinfo_yearold)
	TextView mUserInfoYearOld;
	@InjectView(R.id.userinfo_yue)
	TextView mUserInfoYuE;
	@InjectView(R.id.more_my_name_et)
	EditText mEtUserInfoName;
	
	private Account account;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_info);
		ButterKnife.inject(mActThis);
		account = AppContext.getInstance().getAccount();
		initViews();
		initDatas();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("个人资料");
	}
	private void initDatas() {
		loader.displayImage(account.getUseravatarversion(), mUserInfoPhoto, photoOptions);
		mUserInfoName.setText(account.getUsername());
		mUserInfoYearOld.setText(DateUtil.getAge(account.getUserbirthday())+"岁");
	}


	@OnClick({ R.id.more_my_name_iv, R.id.more_my_city_iv, R.id.more_my_birthday_iv, R.id.more_my_sex_iv, R.id.more_my_phone_iv, R.id.more_my_email_iv, R.id.more_my_intro_rl, R.id.more_my_moreinfo_rl })
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
	}
}
