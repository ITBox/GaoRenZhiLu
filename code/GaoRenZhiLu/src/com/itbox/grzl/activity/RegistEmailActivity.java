package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CheckAccount;
import com.itbox.grzl.bean.Register;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.common.util.DialogMessage;
import com.loopj.android.http.RequestParams;







import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
	EditText mLLEmailEmail;
	@InjectView(R.id.regist_email_pass)
	EditText mLLEmailPass;
	@InjectView(R.id.regist_email_name)
	EditText mLLEmailName;
	@InjectView(R.id.regist_email_sex)
	EditText mLLEmailSex;
	@InjectView(R.id.regist_email_birthday)
	EditText mLLEmailBirthday;
	@InjectView(R.id.regist_email_area)
	EditText mLLEmailArea;
	@InjectView(R.id.regist_email_type)
	EditText mLLEmailType;
	
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
	}

	@OnClick(R.id.text_left)
	public void cancelAct() {
		showExitDialog();
	}
	
	@Override
	protected boolean onBack() {
		showExitDialog();
		return true;
	}
	
	private void showExitDialog() {
		DialogMessage dialog = DialogMessage.newIntance();
		dialog.show(mActThis.getSupportFragmentManager(), "exitRegist");
	}
	@OnClick(R.id.regist_email_regist)
	public void registEmailAccount() {
		// 验证邮箱是否注册过
		showProgressDialog("注册中...");
		Net.request("useremail", mLLEmailEmail.getText().toString(), Api.getUrl(Api.User.CheckAccount), new GsonResponseHandler<CheckAccount>(CheckAccount.class) {
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
		params.put("username", mLLEmailName.getText().toString());
		params.put("userpassword", mLLEmailPass.getText().toString());
		params.put("userprovince", provinceCode+"");
		params.put("usercity", cityCode+"");
		params.put("userdistrict", districtCode+"");
		params.put("useremail", mLLEmailEmail.getText().toString());
		params.put("usersex", sex+"");
		params.put("userbirthday", birthday);
		params.put("usertype", type+"");
		Net.request(params, Api.getUrl(Api.User.Register), new GsonResponseHandler<Register>(Register.class){
			@Override
			public void onSuccess(Register object) {
				super.onSuccess(object);
				if (object.getResult() > 0){
					// 注册成功
					ToastUtils.showToast(mActThis, "注册成功");
					finish();
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
//	private static final int REQUEST_SELECT_SEX = 0;
//	private static final int REQUEST_SELECT_BIRTHDAY = 1;
//	private static final int REQUEST_SELECT_AREA = 2;
//	private static final int REQUEST_SELECT_TYPE = 3;
	
	@OnClick({R.id.regist_email_sex,R.id.regist_email_birthday,R.id.regist_email_area,R.id.regist_email_type,R.id.regist_phone,R.id.regist_email_clause})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_email_sex:
			Intent sexIntent = new Intent(this, SelectButton3Activity.class);
			sexIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "男");
			sexIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "女");
			startActivityForResult(sexIntent, Contasts.REQUEST_SELECT_SEX);
			break;
		case R.id.regist_email_birthday:
			Intent birIntent = new Intent(this, SelectDateActivity.class);
			birIntent.putExtra(SelectDateActivity.Extra.DefaultTimeMillis, birthdayMils);
			startActivityForResult(birIntent, Contasts.REQUEST_SELECT_BIRTHDAY);
			break;
		case R.id.regist_email_area:
			Intent intent = new Intent(this, SelectAddrActivity.class);
			startActivityForResult(intent, Contasts.REQUEST_SELECT_AREA);
			break;
		case R.id.regist_email_clause:
			startActivity(LicenceActivity.class);
			break;
		case R.id.regist_phone:
			startActivity(RegistPhoneFirstActivity.class);
			finish();
			break;
		case R.id.regist_email_type:
			Intent typeIntent = new Intent(this, SelectButton3Activity.class);
			typeIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "导师");
			typeIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "学生");
			startActivityForResult(typeIntent, Contasts.REQUEST_SELECT_TYPE);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case Contasts.REQUEST_SELECT_SEX:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(SelectButton3Activity.Extra.SelectedItem, SelectButton3Activity.Extra.Selected_cancle);
				if(intExtra != SelectButton3Activity.Extra.Selected_cancle){
					sex = intExtra+1;
					mLLEmailSex.setText(data.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		case Contasts.REQUEST_SELECT_BIRTHDAY:
			if (RESULT_OK == resultCode && null != data) {
				birthdayMils = data.getLongExtra(SelectDateActivity.Extra.SelectedTime,0);
				String birStr = data.getStringExtra(SelectDateActivity.Extra.SelectedTimeStr);
				birthday = birStr;
				mLLEmailBirthday.setText(birStr.substring(0, 10));
			}
			break;
		case Contasts.REQUEST_SELECT_AREA:
			if (RESULT_OK == resultCode && null != data) {

				provinceCode = data.getIntExtra(SelectAddrActivity.Extra.ProvinceCode, 0);
				cityCode = data.getIntExtra(SelectAddrActivity.Extra.CityCode, 0);
				districtCode = data.getIntExtra(SelectAddrActivity.Extra.DistrictCode, 0);
				String addrName = data.getStringExtra(SelectAddrActivity.Extra.ProvinceName) + " " + data.getStringExtra(SelectAddrActivity.Extra.CityName)
						+ " " + data.getStringExtra(SelectAddrActivity.Extra.DistrictName);
				mLLEmailArea.setText(addrName);
			}
			break;
		case Contasts.REQUEST_SELECT_TYPE:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(SelectButton3Activity.Extra.SelectedItem, SelectButton3Activity.Extra.Selected_cancle);
				if(intExtra != SelectButton3Activity.Extra.Selected_cancle){
					type = intExtra+1;
					mLLEmailType.setText(data.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		default:
			break;
		}
	}
}
