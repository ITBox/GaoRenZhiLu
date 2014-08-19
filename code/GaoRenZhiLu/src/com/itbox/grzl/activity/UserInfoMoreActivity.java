package com.itbox.grzl.activity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.EditTextUtils;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.Job;
import com.itbox.grzl.bean.UpdateUserExtension;
import com.itbox.grzl.bean.UserExtension;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.enumeration.TeacherType;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 更多资料
 * @author youzh
 * 
 */
public class UserInfoMoreActivity extends BaseActivity implements LoaderCallbacks<Cursor> {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.text_right)
	TextView mTVTopSave;
	@InjectView(R.id.more_my_name_et)
	EditText mEtUserInfoName;
	@InjectView(R.id.more_my_shenfenzheng)
	TextView mTVShenfenzheng;
	@InjectView(R.id.more_my_bankcard)
	EditText mEtUserInfoBankCard;
	@InjectView(R.id.more_my_bankcard_name)
	EditText mEtUserInfoBankCardName;
	@InjectView(R.id.teacher_type)
	TextView mTeacherType;// 导师类型
	@InjectView(R.id.position_type)
	TextView mPositionType;// 职位类型
	@InjectView(R.id.more_my_zixunPhone_et)
	EditText mEtUserInfoZixunPhone;// 电话咨询
	@InjectView(R.id.more_my_zixunImg_et)
	EditText mEtUserInfoZixunImg;// 图文咨询
	@InjectView(R.id.more_my_zixunTime_tv)
	TextView mTVUserInfoZixunTime;// 咨询时段

	private UserExtension userExtension;
	private String time1;
	private String time2;
	private String[] jobsNames;
	private int[] jobsIds;
	private int teacherId;
	private int jobId;
//    private UserExtension newUserExtension = new UserExtension();
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_info_more);
		ButterKnife.inject(mActThis);
		initViews();
       
		ArrayList<Job> jobList = AppContext.getJobs();
		jobsNames = new String[jobList.size()];
		jobsIds = new int[jobList.size()];
		for (int i = 0; i < jobsNames.length; i++) {
			jobsNames[i] = jobList.get(i).getName();
			jobsIds[i] = jobList.get(i).getId();
		}
		
		getSupportLoaderManager().initLoader(0, null, this);
		
		getData();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopSave.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("个人资料");
		mTVTopMedium.setText("更多资料");
		mTVTopSave.setText("保存");
	}

	private void initDatas() {
		mEtUserInfoName.setText(userExtension.getUsernickname());
		if (userExtension.getUsercode().equals("0")) {
			mTVShenfenzheng.setTextColor(Color.rgb(235, 81, 77));// 红
			mTVShenfenzheng.setText("未审核");
		} else if (userExtension.getUsercode().equals("1")) {
			mTVShenfenzheng.setTextColor(Color.rgb(121, 185, 104));// 绿
			mTVShenfenzheng.setText("已审核");
		} else if (userExtension.getUsercode().equals("2")) {
			mTVShenfenzheng.setTextColor(Color.rgb(253, 108, 23));// 橘
			mTVShenfenzheng.setText("审核中");
		} else if (userExtension.getUsercode().equals("3")) {
			mTVShenfenzheng.setTextColor(Color.rgb(253, 108, 23));// 橘
			mTVShenfenzheng.setText("审核失败");
		} 
		if (TextUtils.isEmpty(userExtension.getTeachertype())) {
			teacherId = 1;
		} else {
			teacherId = Integer.parseInt(userExtension.getTeachertype());
		}
		mTeacherType.setText(TeacherType.getTeacherName(teacherId));
		if (TextUtils.isEmpty(userExtension.getJobtype())) {
			jobId = 1;
		} else {
			jobId = Integer.parseInt(userExtension.getJobtype());
			if (jobId < 1) {
				jobId = 1;
			}
		}
		mPositionType.setText(jobsNames[jobId - 1]);
		mEtUserInfoBankCard.setText(userExtension.getUserbank());
		mEtUserInfoBankCardName.setText(userExtension.getBankaddress());
		mEtUserInfoZixunPhone.setText(userExtension.getPhoneprice());
		mEtUserInfoZixunImg.setText(userExtension.getPictureprice());
		mTVUserInfoZixunTime.setText(userExtension.getStarttime() + "-" + userExtension.getEndtime());
		time1 = userExtension.getStarttime();
		time2 = userExtension.getEndtime();
	}

//	@Override
//	protected boolean onBack() {
//		userExtension.equals(obj);
//		return true;
//	}

	@OnClick({ R.id.text_left, R.id.text_right, R.id.more_my_name_iv, R.id.more_my_shenfenzheng_rl, R.id.more_my_bankcard_rl, R.id.more_my_bankcard_name_rl, R.id.teacher_type, R.id.position_type, R.id.more_my_zixunImg_rl, R.id.more_my_zixunPhone_rl, R.id.more_my_zixunTime_rl })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.text_right:
			postDataMethod();
			break;
		case R.id.more_my_name_iv:
			EditTextUtils.showKeyboard(mEtUserInfoName);
			EditTextUtils.setSelection(mEtUserInfoName);
			break;
		case R.id.more_my_shenfenzheng_rl:
			if (userExtension.getUsercode().equals("1")) {
				break;
			}
			if (userExtension.getUsercode().equals("2")) {
				break;
			}
			startActivity(UserIDCardActivity.class);
			break;
		case R.id.more_my_bankcard_rl:// 银行卡号
			EditTextUtils.showKeyboard(mEtUserInfoBankCard);
			EditTextUtils.setSelection(mEtUserInfoBankCard);
			break;
		case R.id.more_my_bankcard_name_rl:// 开户行名称
			EditTextUtils.showKeyboard(mEtUserInfoBankCardName);
			EditTextUtils.setSelection(mEtUserInfoBankCardName);
			break;
		case R.id.teacher_type:
			new AlertDialog.Builder(mActThis).setItems(TeacherType.getAllTeacherName(), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					teacherId = TeacherType.getTeacherId(which + 1);
					Log.i("youzh", "teacher:" + teacherId + "--" + which);
					mTeacherType.setText(TeacherType.getTeacherName(which +1));
				}
			}).show();
			break;
		case R.id.position_type:
			new AlertDialog.Builder(mActThis).setItems(jobsNames, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					jobId = jobsIds[which];
					Log.i("youzh", "job:" +jobId + "--" + which);
					mPositionType.setText(jobsNames[which]);
				}
			}).show();
			break;
		case R.id.more_my_zixunImg_rl:// 图文咨询
			EditTextUtils.showKeyboard(mEtUserInfoZixunImg);
			EditTextUtils.setSelection(mEtUserInfoZixunImg);
			break;
		case R.id.more_my_zixunPhone_rl:// 电话咨询
			EditTextUtils.showKeyboard(mEtUserInfoZixunPhone);
			EditTextUtils.setSelection(mEtUserInfoZixunPhone);
			break;
		case R.id.more_my_zixunTime_rl:// 咨询时段
			Intent intent = new Intent(mActThis, SelectDoubleHourActivity.class);
			intent.putExtra("type", "workTime");
			mActThis.startActivityForResult(intent, Contasts.REQUEST_SELECT_ZIXUN_TIME);
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
		case Contasts.REQUEST_SELECT_ZIXUN_TIME:
			if (resultCode == RESULT_OK && data != null) {
				time1 = data.getStringExtra(SelectDoubleHourActivity.Extra.Time_EarliestStr);
				time2 = data.getStringExtra(SelectDoubleHourActivity.Extra.Time_LatestStr);
				// String time3 =
				// data.getStringExtra(SelectDoubleHourActivity.Extra.Time_Earliest);
				// String time4 =
				// data.getStringExtra(SelectDoubleHourActivity.Extra.Time_Latest);
				// Log.i("youzh", time1 + "-"+time2 +"-"+ time3 + "-"+time4);
				mTVUserInfoZixunTime.setText(time1 + "-" + time2);
			}
			break;
		}
	}

	/**
	 * 获取用户更多资料
	 */
	private void getData() {
		Net.request("userid", AppContext.getInstance().getAccount().getUserid() + "", Api.getUrl(Api.User.GET_USER_EXTENSION), new GsonResponseHandler<UserExtension>(UserExtension.class) {
			@Override
			public void onSuccess(UserExtension object) {
				super.onSuccess(object);
				object.save();
			}
		});
	}

	/**
	 * 更改用户更多资料
	 */
	private void postDataMethod() {
		showProgressDialog("更新中...");
		RequestParams params = new RequestParams();
		params.put("userid", userExtension.getUserid());
		params.put("userbank", EditTextUtils.getText(mEtUserInfoBankCard));
		params.put("bankaddress", EditTextUtils.getText(mEtUserInfoBankCardName));
		params.put("teachertype", teacherId+"");
		params.put("jobtype", jobId+"");
		params.put("pictureprice", EditTextUtils.getText(mEtUserInfoZixunImg));
		params.put("phoneprice", EditTextUtils.getText(mEtUserInfoZixunPhone));
		params.put("starttime", time1);
		params.put("endtime", time2);
		
		Net.request(params, Api.getUrl(Api.User.UP_USER_MORE), new GsonResponseHandler<UpdateUserExtension>(UpdateUserExtension.class) {
			@Override
			public void onSuccess(UpdateUserExtension object) {
				super.onSuccess(object);
				int result = object.getResult();
				switch (result) {
				case Contasts.RESULT_SUCCES:
					// 保存用户数据
					userExtension.setUserbank(EditTextUtils.getText(mEtUserInfoBankCard));
					userExtension.setBankaddress(EditTextUtils.getText(mEtUserInfoBankCardName));
					userExtension.setTeachertype(teacherId + "");
					userExtension.setJobtype(jobId + "");
					userExtension.setPictureprice(EditTextUtils.getText(mEtUserInfoZixunImg));
					userExtension.setPhoneprice(EditTextUtils.getText(mEtUserInfoZixunPhone));
					userExtension.setStarttime(time1);
					userExtension.setEndtime(time2);
					userExtension.save();
					dismissProgressDialog();
					UserInfoMoreActivity.this.finish();
					break;
				case Contasts.RESULT_FAIL:
					dismissProgressDialog();
					ToastUtils.showToast(mActThis, "更新失败，请重试");
					break;
				default:
					break;
				}
			}
			@Override
			public void onFinish() {
				dismissProgressDialog();
			}
			@Override
			public void onFailure(Throwable error, String content) {
				showToast(content);
			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new android.support.v4.content.CursorLoader(mActThis, ContentProvider.createUri(UserExtension.class, null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null && cursor.moveToNext()) {
			userExtension  = new UserExtension();
			userExtension.loadFromCursor(cursor);
			initDatas();
		} else {
			getData();//获取网络数据
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}
}
