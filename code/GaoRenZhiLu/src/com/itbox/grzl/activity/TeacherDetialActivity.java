package com.itbox.grzl.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.TeacherExtension;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.constants.TeacherExtensionTable;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TeacherDetialActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {
	private ConsultationApi api;
	private UserListItem teacher;
	@InjectView(R.id.iv_avatar)
	CircleImageView avatarImageView;
	@InjectView(R.id.tv_name)
	TextView nameTextView;
	@InjectView(R.id.ratingbar)
	RatingBar mRatingBar;
	@InjectView(R.id.tv_jobtype)
	TextView jobtypeTextView;
	@InjectView(R.id.tv_teachertype)
	TextView teachertypeTextView;
	@InjectView(R.id.tv_teacher_description)
	TextView teacherDescriptionTextView;
	@InjectView(R.id.tv_buy_count)
	TextView buyCountTextView;
	@InjectView(R.id.tv_answer_count)
	TextView answerCountTextView;
	private ImageLoader mImageLoader;
	@InjectView(R.id.tv_picture_consultation)
	TextView pictureConsultationTextView;
	@InjectView(R.id.tv_phone_consultation)
	TextView phoneConsultationTextView;
	private TeacherExtension teacherExtension;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		teacher = (UserListItem) getIntent().getSerializableExtra("teacher");
		if (teacher == null) {
			return;
		}

		setContentView(R.layout.activity_teacher_detial);
		ButterKnife.inject(this);

		showLeftBackButton();
		setTitle(teacher.getUserrealname() + "咨询详情");

		teacherExtension = new TeacherExtension();
		api = new ConsultationApi();
		api.getTeacherMoreInfo(teacher.getUserid());
		mImageLoader = ImageLoader.getInstance();
		mImageLoader.displayImage(
				Api.User.getAvatarUrl(teacher.getUseravatarversion()),
				avatarImageView);
		nameTextView.setText(teacher.getUserrealname());
		String jobName = AppContext.getJobName(Integer.valueOf(teacher
				.getJobtype()));
		jobtypeTextView.setText(jobName);
		String teacherType = teacher.getTeachertype();
		if ("1".equals(teacherType)) {
			teachertypeTextView.setText("专业导师");
		} else {
			teachertypeTextView.setText("人力导师");
		}
		teacherDescriptionTextView
				.setText("简介" + teacher.getUserintroduction());
		buyCountTextView.setText(teacher.getBuycount() + "人购买");
		answerCountTextView.setText("回答" + teacher.getAnswercount() + "次");
		mRatingBar.setRating(Float.valueOf(teacher.getTeacherlevel()));
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@OnClick(R.id.ll_picture_consultation)
	public void enterPictureConsultationDetial() {
		Intent intent = new Intent(this, ConsultationDetialActivity.class);
		intent.putExtra("teacherExtension", teacherExtension);
		intent.putExtra("teacher", teacher);
		intent.putExtra("consultation_name",
				"图文咨询 ￥" + teacherExtension.getPicturepice());
		intent.putExtra("type", "picture");
		startActivity(intent);
	}

	@OnClick(R.id.ll_phone_consultation)
	public void enterPhoneConsultationDetial() {
		Intent intent = new Intent(this, ConsultationDetialActivity.class);
		intent.putExtra("teacher", teacher);
		intent.putExtra("teacherExtension", teacherExtension);
		intent.putExtra("consultation_name",
				"电话资讯 ￥" + teacherExtension.getPhoneprice());
		intent.putExtra("type", "phone");
		startActivity(intent);
	}

	@OnClick(R.id.ll_my_course)
	public void enterMyCourse() {
		Intent intent = new Intent(this, EventTeacherActivity.class);
		intent.putExtra("teacher", teacher);
		startActivity(intent);
		showToast("course");
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				TeacherExtension.class, null), null,
				TeacherExtensionTable.COLUMN_USERID + "=?",
				new String[] { teacher.getUserid() }, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null && cursor.moveToNext()) {

			teacherExtension.loadFromCursor(cursor);
			pictureConsultationTextView.setText("图文咨询 ￥"
					+ teacherExtension.getPicturepice());
			phoneConsultationTextView.setText("电话咨询 ￥"
					+ teacherExtension.getPhoneprice());
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
