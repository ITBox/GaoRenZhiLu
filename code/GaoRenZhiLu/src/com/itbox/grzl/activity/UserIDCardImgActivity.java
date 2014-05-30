package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.AddUserAuthEntication;
import com.itbox.grzl.common.Contasts;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 身份证图片上传
 * @author youzh
 *
 */
public class UserIDCardImgActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.idcard_name_et)
	EditText mIDCardName;
	@InjectView(R.id.idcard_birthday_et)
	EditText mIDCardBirthday;
	@InjectView(R.id.idcard_num_et)
	EditText mIDCardNum;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_idcard_img);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("身份认证");
		mTVTopMedium.setText("上传图片");
	}
	
	@OnClick({R.id.text_left, R.id.idcardImg_cancel, R.id.idcardImg_upload})
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			finish();
			break;
		case R.id.idcardImg_cancel:
			finish();
			break;
		case R.id.idcardImg_upload:
			
			break;
		default:
			break;
		}
	}

}
