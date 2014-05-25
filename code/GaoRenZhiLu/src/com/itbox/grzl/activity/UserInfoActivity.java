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
	private long birthdayMils;
	private String birthday;
	private int sex;
	private int provinceCode = 100000;
	private int cityCode = 110000;
	private int districtCode = 110101;

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
		mUserInfoYearOld.setText(DateUtil.getAge(account.getUserbirthday()) + "岁");
		mUserInfoPlace.setText(new AreaListDB().getAreaByCode(Integer.parseInt(account.getUsercity())).getAreaName());
		mUserInfoXingzuo.setText(DateUtil.getConstellation(account.getUserbirthday()));
		mUserInfoYuE.setText(account.getBuycount() + "购买");

		mEtUserInfoName.setText(account.getUsername());
		mUserInfoCity.setText(getUserPlace(account.getUserprovince()) + getUserPlace(account.getUsercity()) + getUserPlace(account.getUserdistrict()));
		if (!TextUtils.isEmpty(account.getUserbirthday())) {
			mUserInfoBirthday.setText(account.getUserbirthday().substring(0, 10));
		} else {
			mUserInfoBirthday.setText(account.getUserbirthday());
		}
		mUserInfoSex.setText(account.getUsersex());
		mEtUserInfoPhone.setText(account.getUserphone());
		mEtUserInfoEmail.setText(account.getUseremail());
		mUserInfoIntro.setText(account.getUserintroduction());

	}

	private String getUserPlace(String place) {
		return new AreaListDB().getAreaByCode(Integer.parseInt(place)).getAreaName();
	}
	
    @Override
    protected boolean onBack() {
    	// TODO Auto-generated method stub
    	return true;
    }
    
	@OnClick({ R.id.text_left, R.id.more_my_name_iv, R.id.more_my_city_iv, R.id.more_my_birthday_iv, R.id.more_my_sex_iv, R.id.more_my_phone_iv, R.id.more_my_email_iv, R.id.more_my_intro_rl, R.id.more_my_moreinfo_rl })
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
		case R.id.more_my_phone_iv:
			Utils.showKeyboard(mEtUserInfoPhone);
			String userPhone = mEtUserInfoPhone.getText().toString();
			if (!TextUtils.isEmpty(userPhone)) {
				mEtUserInfoPhone.setSelection(userPhone.length());
			}
			break;
		case R.id.more_my_email_iv:
			Utils.showKeyboard(mEtUserInfoEmail);
			String userEmail = mEtUserInfoEmail.getText().toString();
			if (!TextUtils.isEmpty(userEmail)) {
				mEtUserInfoEmail.setSelection(userEmail.length());
			}
			break;
		case R.id.more_my_sex_iv:
			Intent sexIntent = new Intent(this, SelectButton3Activity.class);
			sexIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "男");
			sexIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "女");
			startActivityForResult(sexIntent, Contasts.REQUEST_SELECT_SEX);
			break;
		case R.id.more_my_birthday_iv:
			Intent birIntent = new Intent(this, SelectDateActivity.class);
			birIntent.putExtra(SelectDateActivity.Extra.DefaultTimeMillis, birthdayMils);
			startActivityForResult(birIntent, Contasts.REQUEST_SELECT_BIRTHDAY);
			break;
		case R.id.more_my_city_iv:
			Intent intent = new Intent(this, SelectAddrActivity.class);
			startActivityForResult(intent, Contasts.REQUEST_SELECT_AREA);
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
		case Contasts.REQUEST_SELECT_SEX:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(SelectButton3Activity.Extra.SelectedItem, SelectButton3Activity.Extra.Selected_cancle);
				if (intExtra != SelectButton3Activity.Extra.Selected_cancle) {
					sex = intExtra + 1;
					mUserInfoSex.setText(data.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		case Contasts.REQUEST_SELECT_BIRTHDAY:
			if (RESULT_OK == resultCode && null != data) {
				birthdayMils = data.getLongExtra(SelectDateActivity.Extra.SelectedTime, 0);
				String birStr = data.getStringExtra(SelectDateActivity.Extra.SelectedTimeStr);
				birthday = birStr;
				mUserInfoBirthday.setText(birStr.substring(0, 10));
			}
			break;
		case Contasts.REQUEST_SELECT_AREA:
			if (RESULT_OK == resultCode && null != data) {

				provinceCode = data.getIntExtra(SelectAddrActivity.Extra.ProvinceCode, 0);
				cityCode = data.getIntExtra(SelectAddrActivity.Extra.CityCode, 0);
				districtCode = data.getIntExtra(SelectAddrActivity.Extra.DistrictCode, 0);
				String addrName = data.getStringExtra(SelectAddrActivity.Extra.ProvinceName) + " " + data.getStringExtra(SelectAddrActivity.Extra.CityName) + " " + data.getStringExtra(SelectAddrActivity.Extra.DistrictName);
				mUserInfoCity.setText(addrName);
			}
			break;
		}
	}

}
