package com.itbox.grzl.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.util.DateUtil;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.AreaData;
import com.itbox.grzl.common.db.AreaListDB;
import com.itbox.grzl.engine.UserEngine;
import com.zhaoliewang.grzl.R;

/**
 * 个人资料
 * 
 * @author youzh
 * 
 */
public class OtherUserInfoActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.text_right)
	TextView mTVTopSave;
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
	@InjectView(R.id.more_my_city)
	TextView mUserInfoCity;
	@InjectView(R.id.more_my_birthday)
	TextView mUserInfoBirthday;
	@InjectView(R.id.more_my_sex)
	TextView mUserInfoSex;
	@InjectView(R.id.more_my_phone_et)
	EditText mEtUserInfoPhone;
	@InjectView(R.id.more_my_email_et)
	EditText mEtUserInfoEmail;
	@InjectView(R.id.more_my_intro_tv)
	TextView mUserInfoIntro;

	private Account account;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_other_user_info);
		ButterKnife.inject(mActThis);

		showLoadProgressDialog();
		initViews();
		UserEngine.getUserList(getIntent().getStringExtra("userid"),
				new GsonResponseHandler<Account>(Account.class) {
					@Override
					public void onSuccess(Account user) {
						account = user;
						initDatas();
					}

					@Override
					public void onFinish() {
						dismissProgressDialog();
					}
				});
	}

	private void initViews() {
		showLeftBackButton();
	}

	private void initDatas() {
		setTitle(account.getUsername());
		loader.displayImage(account.getUseravatarversion(), mUserInfoPhoto,
				photoOptions);
		mUserInfoName.setText(account.getUsername());
		mUserInfoYearOld.setText(DateUtil.getAge(account.getUserbirthday())
				+ "岁");
		mUserInfoPlace.setText(getUserPlace(account.getUsercity()));
		mUserInfoXingzuo.setText(DateUtil.getConstellation(account
				.getUserbirthday()));
		mUserInfoYuE.setText(account.getBuycount() + "购买");

		mEtUserInfoName.setText(account.getUsername());
		mUserInfoCity.setText(getUserPlace(account.getUserprovince())
				+ getUserPlace(account.getUsercity())
				+ getUserPlace(account.getUserdistrict()));
		if (!TextUtils.isEmpty(account.getUserbirthday())) {
			mUserInfoBirthday.setText(account.getUserbirthday()
					.substring(0, 10));
		} else {
			mUserInfoBirthday.setText(account.getUserbirthday());
		}
		if (account.getUsersex().equals("1")) {
			mUserInfoSex.setText("男");
		} else {
			mUserInfoSex.setText("女");
		}
		mEtUserInfoPhone.setText(account.getUserphone());
		mEtUserInfoEmail.setText(account.getUseremail());
		mUserInfoIntro.setText(account.getUserintroduction());
	}

	private String getUserPlace(String place) {
		AreaData area = new AreaListDB().getAreaByCode(Integer.parseInt(place));
		if (area != null) {
			return area.getAreaName();
		}
		return "暂无";
	}
}
