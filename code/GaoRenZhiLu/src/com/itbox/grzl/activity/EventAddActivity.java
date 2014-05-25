package com.itbox.grzl.activity;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.DateUtil;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.Const;
import com.itbox.grzl.Const.State;
import com.itbox.grzl.R;
import com.itbox.grzl.activity.SelectTimeHalfHourActivity.Extra;
import com.itbox.grzl.bean.EventAdd;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.bean.UploadImageResult;
import com.itbox.grzl.engine.EventEngine;
import com.itbox.grzl.enumeration.EventType;
import com.itbox.grzl.map.AddrInfoModel;
import com.loopj.android.http.RequestParams;

/**
 * 添加活动页面
 * @author baoboy
 * @date 2014-5-26上午12:28:25
 */
public class EventAddActivity extends BaseActivity {

	public static final int RESULT_SUCCESS = 1;

	private static final int REQ_PICTURE = 1;
	private static final int REQ_START_TIME = 2;
	private static final int REQ_END_TIME = 3;
	private static final int REQ_ADDRESS = 4;

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.tv_type)
	protected TextView mTypeTv;
	@InjectView(R.id.et_title)
	protected EditText mTitleEt;
	@InjectView(R.id.tv_start_time)
	protected TextView mStartTimeTv;
	@InjectView(R.id.tv_end_time)
	protected TextView mEndTimeTv;
	@InjectView(R.id.tv_address)
	protected TextView mAddressTv;
	@InjectView(R.id.et_person_count)
	protected EditText mPersonCountEt;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	private Uri mPhotoUri;
	private long startTime;
	private EventAdd bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_add);

		bean = new EventAdd();

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("发布活动");
		showLeftBackButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			Calendar cal = null;
			switch (requestCode) {
			case REQ_PICTURE:
				mPhotoUri = data.getData();
				break;
			case REQ_START_TIME:
				startTime = data.getLongExtra(Extra.SelectedTime,
						State.Selected_cancle);
				bean.setBegintime(data
						.getStringExtra(SelectTimeHalfHourActivity.Extra.SelectedTimeStr));
				mStartTimeTv.setText(bean.getBegintime());
				break;
			case REQ_END_TIME:
				bean.setEndtime(data
						.getStringExtra(SelectTimeMinuteActivity.Extra.SelectedTimeStr));
				mEndTimeTv.setText(bean.getEndtime());
				break;
			case REQ_ADDRESS:
				AddrInfoModel addrInfo = (AddrInfoModel) data
						.getSerializableExtra(Const.Extra.AddrModel);
				mAddressTv.setText(addrInfo.getStrAddr());
				bean.setAddress(addrInfo.getStrAddr());
				bean.setLatitude(addrInfo.getLatitudeE6() + "");
				bean.setLongitude(addrInfo.getLongitudeE6() + "");
				bean.setUsercity(addrInfo.getCity());
				bean.setUserdistrict(addrInfo.getDistrict());
				bean.setUserprovince(addrInfo.getProvince());
				break;
			}
		}
	}

	@OnClick({ R.id.bt_add, R.id.tv_photo, R.id.tv_type, R.id.tv_start_time,
			R.id.tv_end_time, R.id.tv_address })
	public void onClick(View v) {

		Calendar cal = null;
		switch (v.getId()) {
		case R.id.tv_photo:
			// 选择图片
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, REQ_PICTURE);
			break;
		case R.id.bt_add:
			add();
			break;
		case R.id.tv_type:
			// 选择类型
			selectType();
			break;
		case R.id.tv_start_time:
			// 选择开始时间
			cal = DateUtil.getNewCalendar();
			Intent beginIntent = new Intent(this,
					SelectTimeHalfHourActivity.class);
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);// 普通活动，1小时候
			cal.setTimeInMillis(cal.getTimeInMillis());
			beginIntent.putExtra(Extra.Time_Earliest, cal.getTimeInMillis());
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 15);// 15天内
			beginIntent.putExtra(Extra.Time_Latest, cal.getTimeInMillis());
			startActivityForResult(beginIntent, REQ_START_TIME);
			break;
		case R.id.tv_end_time:
			// 选择结束时间
			if (startTime == 0 || State.Selected_cancle == startTime) {
				showToast(R.string.warn_event_begintime_empty);
				return;
			}
			Intent endIntent = new Intent(this,
					SelectTimeHalfHourActivity.class);
			cal = DateUtil.getNewCalendar();
			cal.setTimeInMillis(startTime);
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
			cal.setTimeInMillis(cal.getTimeInMillis());
			endIntent.putExtra(Extra.Time_Earliest, cal.getTimeInMillis());
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 7);// 15天内
			endIntent.putExtra(Extra.Time_Latest, cal.getTimeInMillis());
			startActivityForResult(endIntent, REQ_END_TIME);
			break;
		case R.id.tv_address:
			// 选择地址
			startActivityForResult(SelectMapPointActivity.class, REQ_ADDRESS);
			break;
		}
	}

	/**
	 * 选择类型
	 */
	private void selectType() {
		new AlertDialog.Builder(this).setItems(EventType.getAllName(),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EventType type = EventType.getByIndex(which);
						bean.setTypeid(type.getIndex() + "");
						mTypeTv.setText(type.getName());
					}
				}).show();
	}

	/**
	 * 发布
	 */
	private void add() {
		if (!checkParams()) {
			return;
		}
		// 发布
		showProgressDialog("正在发布...");
		// 上传图片
		try {
			RequestParams params = new RequestParams();
			params.put("图片流", AppContext.getInstance().getContentResolver()
					.openInputStream(mPhotoUri));
			params.put("id", AppContext.getInstance().getAccount().getUserid()
					.toString());
			params.put("imagetype", "3"); // 论坛图片
			Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE),
					new GsonResponseHandler<UploadImageResult>(
							UploadImageResult.class) {
						@Override
						public void onSuccess(UploadImageResult result) {
							super.onSuccess(result);
							if (result != null && result.getReturnUrl() != null) {
								pushEvent(result.getReturnUrl());
							} else {
								dismissProgressDialog();
								showToast("海报上传失败");
							}
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							dismissProgressDialog();
							showToast(content);
						}
					});
		} catch (Exception e) {
			dismissProgressDialog();
			showToast("出错");
		}
	}

	private boolean checkParams() {
		if (TextUtils.isEmpty(bean.getTypeid())) {
			showToast("请选择活动类型");
			return false;
		}
		if (TextUtils.isEmpty(mTitleEt.getText().toString())) {
			showToast("请输入活动标题");
			return false;
		}
		if (TextUtils.isEmpty(bean.getBegintime())) {
			showToast("请选择开始时间");
			return false;
		}
		if (TextUtils.isEmpty(bean.getEndtime())) {
			showToast("请选择结束时间");
			return false;
		}
		if (TextUtils.isEmpty(bean.getAddress())) {
			showToast("请选择活动地址");
			return false;
		}
		if (TextUtils.isEmpty(mPersonCountEt.getText().toString())) {
			showToast("请输入活动人数");
			return false;
		}
		if (mPhotoUri == null) {
			showToast("请选择活动海报");
			return false;
		}
		if (TextUtils.isEmpty(mContentEt.getText().toString())) {
			showToast("请输入活动描述");
			return false;
		}
		return true;
	}

	private void pushEvent(String photo) {
		// 发布活动
		String title = mTitleEt.getText().toString();
		String content = mContentEt.getText().toString();
		String personCount = mPersonCountEt.getText().toString();
		bean.setActivitydescription(content);
		bean.setActivitypicture(photo);
		bean.setPeoplecount(personCount);
		bean.setTitle(title);
		bean.setUserid(AppContext.getInstance().getAccount().getUserid()
				.toString());
		EventEngine.addEvent(bean, new GsonResponseHandler<RespResult>(
				RespResult.class) {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onSuccess(RespResult result) {
				if (result.isSuccess()) {
					showToast("发布成功");
					setResult(RESULT_SUCCESS);
					finish();
				} else {
					showToast("发布失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showToast(content);
			}
		});
	}
}
