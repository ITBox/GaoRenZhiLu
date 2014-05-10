package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.ToastUtils;
import com.itbox.fx.widget.CellView;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.bean.Register;
import com.loopj.android.http.RequestParams;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author youzh 2014年5月2日 下午5:44:25
 */
public class RegistEmailActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.regist_email_email)
	CellView mLLEmailEmail;
	@InjectView(R.id.regist_email_pass)
	CellView mLLEmailPass;
	@InjectView(R.id.regist_email_name)
	CellView mLLEmailName;
	@InjectView(R.id.regist_email_sex)
	CellView mLLEmailSex;
	@InjectView(R.id.regist_email_birthday)
	CellView mLLEmailBirthday;
	@InjectView(R.id.regist_email_area)
	CellView mLLEmailArea;
	@InjectView(R.id.regist_email_type)
	CellView mLLEmailType;
	@InjectView(R.id.regist_email_regist)
	Button mSubmit;
	
	private long birthdayMils;
	private String birthday;
	private int sex;
	private int provinceCode = 100000;
	private int cityCode = 110000;
	private int districtCode = 110101;
	private int type;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_regist_email);
		ButterKnife.inject(mActThis);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("邮箱注册");
		
		mLLEmailSex.setOnClickListener(this);
		mLLEmailBirthday.setOnClickListener(this);
		mLLEmailArea.setOnClickListener(this);
		mLLEmailType.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
	}

	@OnClick(R.id.text_left)
	public void cancelAct() {
		RegistEmailActivity.this.finish();
	}

	@OnClick(R.id.regist_email_clause)
	public void clickClause() {

	}

	@OnClick(R.id.regist_email_regist)
	public void registEmailAccount() {
		// 验证邮箱是否注册过
//		showProgressDialog("注册中...");
		Net.request("useremail", mLLEmailEmail.getRightText(), Api.getUrl(Api.User.CheckAccount), new GsonResponseHandler<CheckAccount>(CheckAccount.class) {
			@Override
			public void onSuccess(CheckAccount object) {
				super.onSuccess(object);
				int userEmail = object.getUserEmail();
				if (userEmail == 0) {
					registEmailAll();
				} else {
					dismissProgressDialog();
					ToastUtils.makeCustomPosition(mActThis, "该邮箱已被注册", R.id.alternate_view_group);
				}
			}

			@Override
			public void onFailure(Throwable e, int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onFailure(e, statusCode, content);
			}
		});
	}
	/**
	 * 邮箱注册
	 */
	private void registEmailAll() {
		RequestParams params = new RequestParams();
		params.put("username", mLLEmailName.getRightText());
		params.put("userpassword", mLLEmailPass.getRightText());
		params.put("userprovince", "100000");
		params.put("usercity", "110000");
		params.put("userdistrict", "110101");
		params.put("useremail", mLLEmailEmail.getRightText());
		params.put("usersex", "1");
		params.put("usertype", "1");
		Net.request(params, Api.getUrl(Api.User.Register), new GsonResponseHandler<Register>(Register.class){
			@Override
			public void onSuccess(Register object) {
				super.onSuccess(object);
				if (object.getResult() > 0){
					// 注册成功
				} else {
					// 
				}
				
			}
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}
		});
	}
	private static final int REQUEST_SELECT_SEX = 0;
	private static final int REQUEST_SELECT_BIRTHDAY = 1;
	private static final int REQUEST_SELECT_AREA = 2;
	private static final int REQUEST_SELECT_TYPE = 3;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_email_sex:
			Intent sexIntent = new Intent(this, SelectButton3Activity.class);
			sexIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "男");
			sexIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "女");
			startActivityForResult(sexIntent, REQUEST_SELECT_SEX);
			break;
		case R.id.regist_email_birthday:
			Intent birIntent = new Intent(this, SelectDateActivity.class);
			birIntent.putExtra(SelectDateActivity.Extra.DefaultTimeMillis, birthdayMils);
			startActivityForResult(birIntent, REQUEST_SELECT_BIRTHDAY);
			break;
		case R.id.regist_email_area:
			Intent intent = new Intent(this, SelectAddrActivity.class);
			startActivityForResult(intent, REQUEST_SELECT_AREA);
			break;
		case R.id.regist_email_clause:
			startActivity(LicenceActivity.class);
			break;
		case R.id.regist_phone_et:
			startActivity(RegistPhoneFirstActivity.class);
			finish();
			break;
		case R.id.regist_email_type:
			Intent typeIntent = new Intent(this, SelectButton3Activity.class);
			typeIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "老师");
			typeIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "学生");
			startActivityForResult(typeIntent, REQUEST_SELECT_TYPE);
			break;
		case R.id.regist_email_regist:{
			registEmailAll();
		}
		break;
		
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_SELECT_SEX:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(SelectButton3Activity.Extra.SelectedItem, SelectButton3Activity.Extra.Selected_cancle);
				if(intExtra != SelectButton3Activity.Extra.Selected_cancle){
					sex = intExtra+1;
					mLLEmailSex.setRightText(data.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		case REQUEST_SELECT_BIRTHDAY:
			if (RESULT_OK == resultCode && null != data) {
				birthdayMils = data.getLongExtra(SelectDateActivity.Extra.SelectedTime,0);
				String birStr = data.getStringExtra(SelectDateActivity.Extra.SelectedTimeStr);
				birthday = birStr;
				mLLEmailBirthday.setRightText(birStr.substring(0, 10));
			}
			break;
		case REQUEST_SELECT_AREA:
			if (RESULT_OK == resultCode && null != data) {

				provinceCode = data.getIntExtra(SelectAddrActivity.Extra.ProvinceCode, 0);
				cityCode = data.getIntExtra(SelectAddrActivity.Extra.CityCode, 0);
				districtCode = data.getIntExtra(SelectAddrActivity.Extra.DistrictCode, 0);
				String addrName = data.getStringExtra(SelectAddrActivity.Extra.ProvinceName) + " " + data.getStringExtra(SelectAddrActivity.Extra.CityName)
						+ " " + data.getStringExtra(SelectAddrActivity.Extra.DistrictName);
				mLLEmailArea.setRightText(addrName);
			}
			break;
		case REQUEST_SELECT_TYPE:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(SelectButton3Activity.Extra.SelectedItem, SelectButton3Activity.Extra.Selected_cancle);
				if(intExtra != SelectButton3Activity.Extra.Selected_cancle){
					type = intExtra+1;
					mLLEmailType.setRightText(data.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		default:
			break;
		}
	}
}
