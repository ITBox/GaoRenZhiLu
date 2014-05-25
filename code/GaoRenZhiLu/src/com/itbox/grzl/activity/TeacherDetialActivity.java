package com.itbox.grzl.activity;

import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.itbox.grzl.R;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.UserListItem;

public class TeacherDetialActivity extends BaseActivity {
	private ConsultationApi api;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_teacher_detial);
		ButterKnife.inject(this);
		UserListItem user = (UserListItem) getIntent().getSerializableExtra(
				"user");
		api = new ConsultationApi();
		api.getTeacherMoreInfo("14");
	}

	@OnClick(R.id.ll_picture_consultation)
	public void enterPictureConsultationDetial() {
		Intent intent = new Intent(this,
				PictureConsultationDetialActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.ll_phone_consultation)
	public void enterPhoneConsultationDetial() {

	}
}
