package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.DateUtil;
import com.itbox.fx.util.EditTextUtils;
import com.itbox.fx.util.ImageUtils;
import com.itbox.fx.util.ToastUtils;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.AreaData;
import com.itbox.grzl.bean.UpdateUserList;
import com.itbox.grzl.bean.UploadImageResult;
import com.itbox.grzl.bean.UserExtension;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.common.db.AreaListDB;
import com.itbox.grzl.common.util.FileUtils;
import com.itbox.grzl.engine.UserEngine;
import com.loopj.android.http.RequestParams;
import com.zhaoliewang.grzl.R;

/**
 * 个人资料
 * 
 * @author youzh
 * 
 */
public class UserInfoActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {
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
	@InjectView(R.id.bt_user_code)
	Button mUserCodeBt;

	@InjectView(R.id.more_my_moreinfo_rl)
	View mMore;

	private UserExtension userExtension;
	private Uri photoUri;
	private Account account;
	private long birthdayMils;
	private String birthday;
	private int sex;
	private int provinceCode = 100000;
	private int cityCode = 110000;
	private int districtCode = 110101;
	private Bitmap photoBit;

	// private ArrayList<Account> beforeAccount = new ArrayList<Account>();
	// private ArrayList<Account> afterAccount = new ArrayList<Account>();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_info);
		ButterKnife.inject(mActThis);
		account = AppContext.getInstance().getAccount();
		// beforeAccount.add(account);
		initViews();
		initDatas();

		getSupportLoaderManager().initLoader(0, null, this);
		getData();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopSave.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("个人资料");
		mTVTopSave.setText("保存");

		if (!AppContext.getInstance().getAccount().isTeacher()) {
			mMore.setVisibility(View.GONE);
		}
	}

	private void initDatas() {
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
			birthday = account.getUserbirthday();
			mUserInfoBirthday.setText(account.getUserbirthday()
					.substring(0, 10));
		} else {
			mUserInfoBirthday.setText(account.getUserbirthday());
		}
		if (account.getUsersex().equals("1")) {
			sex = 1;
			mUserInfoSex.setText("男");
		} else {
			sex = 0;
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

	// @Override
	// protected boolean onBack() {
	// // TODO Auto-generated method stub
	// return true;
	// }

	@OnClick({ R.id.userinfo_photo, R.id.text_left, R.id.text_right,
			R.id.more_my_name_rl, R.id.more_my_city_rl,
			R.id.more_my_birthday_rl, R.id.more_my_sex_rl,
			R.id.more_my_phone_rl, R.id.more_my_email_rl,
			R.id.more_my_intro_rl, R.id.more_my_moreinfo_rl , R.id.bt_user_code})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.text_right:
			postUserInfoMethod();
			break;
		case R.id.userinfo_photo:
			new AlertDialog.Builder(mActThis).setItems(
					new String[] { "拍照", "从图库选择" }, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch (which) {
							case 0:
								Intent intent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								photoUri = Contasts.photoUri(mActThis);
								intent.putExtra(
										android.provider.MediaStore.EXTRA_OUTPUT,
										photoUri);
								// 开启系统拍照的Activity
								mActThis.startActivityForResult(intent,
										Contasts.TAKE_PICTURE_FROM_CAMERA);
								break;
							case 1:
								Intent intent2 = new Intent(
										"android.intent.action.PICK");
								intent2.setType("image/*");
								mActThis.startActivityForResult(intent2,
										Contasts.TAKE_PICTURE_FROM_GALLERY);
								break;

							default:
								break;
							}
						}
					}).show();
			break;
		case R.id.more_my_name_rl:
			EditTextUtils.showKeyboard(mEtUserInfoName);
			String userName = mEtUserInfoName.getText().toString();
			if (!TextUtils.isEmpty(userName)) {
				mEtUserInfoName.setSelection(userName.length());
			}
			break;
		case R.id.more_my_phone_rl:
			EditTextUtils.showKeyboard(mEtUserInfoPhone);
			String userPhone = mEtUserInfoPhone.getText().toString();
			if (!TextUtils.isEmpty(userPhone)) {
				mEtUserInfoPhone.setSelection(userPhone.length());
			}
			break;
		case R.id.more_my_email_rl:
			EditTextUtils.showKeyboard(mEtUserInfoEmail);
			String userEmail = mEtUserInfoEmail.getText().toString();
			if (!TextUtils.isEmpty(userEmail)) {
				mEtUserInfoEmail.setSelection(userEmail.length());
			}
			break;
		case R.id.more_my_sex_rl:
			Intent sexIntent = new Intent(this, SelectButton3Activity.class);
			sexIntent.putExtra(SelectButton3Activity.Extra.Button0_Text, "男");
			sexIntent.putExtra(SelectButton3Activity.Extra.Button1_Text, "女");
			startActivityForResult(sexIntent, Contasts.REQUEST_SELECT_SEX);
			break;
		case R.id.more_my_birthday_iv:
			Intent birIntent = new Intent(this, SelectDateActivity.class);
			birIntent.putExtra(SelectDateActivity.Extra.DefaultTimeMillis,
					birthdayMils);
			startActivityForResult(birIntent, Contasts.REQUEST_SELECT_BIRTHDAY);
			break;
		case R.id.more_my_city_iv:
			Intent intent = new Intent(this, SelectAddrActivity.class);
			startActivityForResult(intent, Contasts.REQUEST_SELECT_AREA);
			break;
		case R.id.more_my_intro_rl:
			Intent intent2 = new Intent(mActThis, UserInfoIntroActivity.class);
			intent2.putExtra("intro", account.getUserintroduction());
			startActivityForResult(intent2, 20);
			break;
		case R.id.more_my_moreinfo_rl:
			startActivity(UserInfoMoreActivity.class);
			break;
		case R.id.bt_user_code:
			startActivity(UserIDCardActivity.class);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Intent intent = null;
		switch (requestCode) {
		case Contasts.TAKE_PICTURE_FROM_CAMERA:
			Intent intent = new Intent(mActThis, CropImgActivity.class);
			// intent.setDataAndType(photoUri, "image/jpeg");
			// intent.putExtra("imgUri", photoUri);
			intent.setData(photoUri);
			mActThis.startActivityForResult(intent,
					Contasts.CROP_CAMERA_PICTURE);
			break;
		case Contasts.TAKE_PICTURE_FROM_GALLERY:
			Intent intent2 = new Intent(mActThis, CropImgActivity.class);
			// intent2.setDataAndType(data.getData(), "image/jpeg");
			// intent2.putExtra("imgUri", photoUri);
			intent2.setData(data.getData());
			Log.i("youzh", "URI : " + data.getData());
			mActThis.startActivityForResult(intent2,
					Contasts.CROP_GALLERY_PICTURE);
			break;
		case Contasts.CROP_CAMERA_PICTURE:
			if (data != null) {
				String path = data.getStringExtra("cropPath");
				photoBit = FileUtils.getImageFromLocal(path);
				uplaodUserPhoto();
			}
			break;
		case Contasts.CROP_GALLERY_PICTURE:
			if (data != null) {
				String path = data.getStringExtra("cropPath");
				photoBit = FileUtils.getImageFromLocal(path);
				uplaodUserPhoto();
			}
			break;
		case 20:
			if (data != null) {
				String userIntro = data.getStringExtra("userIntro");
				if (!TextUtils.isEmpty(userIntro)) {
					mUserInfoIntro.setText(userIntro);
				}
			}
			break;
		case Contasts.REQUEST_SELECT_SEX:
			if (RESULT_OK == resultCode && null != data) {
				int intExtra = data.getIntExtra(
						SelectButton3Activity.Extra.SelectedItem,
						SelectButton3Activity.Extra.Selected_cancle);
				if (intExtra != SelectButton3Activity.Extra.Selected_cancle) {
					sex = intExtra + 1;
					mUserInfoSex
							.setText(data
									.getStringExtra(SelectButton3Activity.Extra.SelectedItemStr));
				}
			}
			break;
		case Contasts.REQUEST_SELECT_BIRTHDAY:
			if (RESULT_OK == resultCode && null != data) {
				birthdayMils = data.getLongExtra(
						SelectDateActivity.Extra.SelectedTime, 0);
				String birStr = data
						.getStringExtra(SelectDateActivity.Extra.SelectedTimeStr);
				birthday = birStr;
				mUserInfoBirthday.setText(birStr.substring(0, 10));
			}
			break;
		case Contasts.REQUEST_SELECT_AREA:
			if (RESULT_OK == resultCode && null != data) {
				provinceCode = data.getIntExtra(
						SelectAddrActivity.Extra.ProvinceCode, 0);
				cityCode = data.getIntExtra(SelectAddrActivity.Extra.CityCode,
						0);
				districtCode = data.getIntExtra(
						SelectAddrActivity.Extra.DistrictCode, 0);
				String addrName = data
						.getStringExtra(SelectAddrActivity.Extra.ProvinceName)
						+ " "
						+ data.getStringExtra(SelectAddrActivity.Extra.CityName)
						+ " "
						+ data.getStringExtra(SelectAddrActivity.Extra.DistrictName);
				mUserInfoCity.setText(addrName);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 修改个人资料
	 */
	private void postUserInfoMethod() {
		showProgressDialog("保存中...");
		RequestParams params = new RequestParams();
		params.put("userid", account.getUserid() + "");
		params.put("username", EditTextUtils.getText(mEtUserInfoName));
		params.put("userphone", EditTextUtils.getText(mEtUserInfoPhone));
		params.put("useremail", EditTextUtils.getText(mEtUserInfoEmail));
		params.put("useravatarversion", "");
		params.put("usersex", sex + "");
		params.put("userprovince", provinceCode + "");
		params.put("usercity", cityCode + "");
		params.put("userdistrict", districtCode + "");
		params.put("userintroduction", mUserInfoIntro.getText().toString());
		params.put("userbirthday", birthday);

		Net.request(params, Api.getUrl(Api.User.UP_USER_INFO),
				new GsonResponseHandler<UpdateUserList>(UpdateUserList.class) {
					@Override
					public void onSuccess(UpdateUserList object) {
						super.onSuccess(object);
						switch (object.getResult()) {
						case Contasts.RESULT_SUCCES:
							userinfo();
							account.save();
							mActThis.finish();
							break;
						case Contasts.RESULT_FAIL:
							ToastUtils.showToast(mActThis, "修改失败");
							break;
						case 2:
							ToastUtils.showToast(mActThis, "昵称重复");
							break;
						case 3:
							ToastUtils.showToast(mActThis, "手机号码重复");
							break;
						case 4:
							ToastUtils.showToast(mActThis, "邮箱重复");
							break;

						default:
							break;
						}
					}

					@Override
					public void onFinish() {
						dismissProgressDialog();
					}

				});
	}

	/**
	 * 上传头像
	 */
	private void uplaodUserPhoto() {
		showProgressDialog("头像上传中...");
		UserEngine.uploadImg(account.getUserid() + "", ImageUtils
				.bitmap2InputStream(photoBit), 1,
				new GsonResponseHandler<UploadImageResult>(
						UploadImageResult.class) {
					@Override
					public void onSuccess(UploadImageResult result) {
						super.onSuccess(result);
						if (result != null) {
							mUserInfoPhoto.setImageBitmap(photoBit);
							userinfo();
							account.setUseravatarversion(result.getReturnUrl());
							account.save();
							dismissProgressDialog();
							showToast("头像上传成功");
						} else {
							dismissProgressDialog();
							showToast("头像上传失败");
						}
					}

					@Override
					public void onFailure(Throwable e, int statusCode,
							String content) {
						super.onFailure(e, statusCode, content);
						dismissProgressDialog();
						showToast(content);
					}
				});
	}

	private void userinfo() {
		account.setUsername(EditTextUtils.getText(mEtUserInfoName));
		account.setUserphone(EditTextUtils.getText(mEtUserInfoPhone));
		account.setUseremail(EditTextUtils.getText(mEtUserInfoEmail));
		account.setUsersex(sex + "");
		account.setUserprovince(provinceCode + "");
		account.setUsercity(cityCode + "");
		account.setUserdistrict(districtCode + "");
		account.setUserintroduction(mUserInfoIntro.getText().toString());
		account.setUserbirthday(birthday);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new android.support.v4.content.CursorLoader(mActThis,
				ContentProvider.createUri(UserExtension.class, null), null,
				null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null && cursor.moveToNext()) {
			userExtension = new UserExtension();
			userExtension.loadFromCursor(cursor);
			if (userExtension.getUsercode().equals("0")
					|| userExtension.getUsercode().equals("3")) {
				mUserCodeBt.setVisibility(View.VISIBLE);
			}
		} else {
			getData();// 获取网络数据
		}
	}

	/**
	 * 获取用户更多资料
	 */
	private void getData() {
		Net.request("userid", AppContext.getInstance().getAccount().getUserid()
				+ "", Api.getUrl(Api.User.GET_USER_EXTENSION),
				new GsonResponseHandler<UserExtension>(UserExtension.class) {
					@Override
					public void onSuccess(UserExtension object) {
						super.onSuccess(object);
						object.save();
					}
				});
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}
}
