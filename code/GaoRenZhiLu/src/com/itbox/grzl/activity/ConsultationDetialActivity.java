package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.TeacherCommentAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.TeacherComment;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.constants.TeacherCommentTable;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 咨询详情页
 * 
 * @author malinkang 2014年5月31日
 * 
 */
public class ConsultationDetialActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	ListView mListView;
	@InjectView(R.id.text_left)
	TextView backTextView;
	private View mHeaderView;

	private ConsultationApi consultationApi;
	private TeacherCommentAdapter adapter;
	private TextView teacherName;
	private RatingBar mRatingBar;
	private TextView jobTypeTextView;
	private TextView teacherTypeTextView;
	private TextView teacherDescriptionTextView;
	private TextView buyCountTextView;
	private TextView answerCountTextView;
	private UserListItem teacher;
	private ImageLoader mImageLoader;
	private ImageView avatarImageView;
	private TextView consultationNameTextView;
	private ImageView iconImageView;
	private String type;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_picture_consultation_detial);
		ButterKnife.inject(this);
		teacher = (UserListItem) getIntent().getSerializableExtra("teacher");
		String consultation_name = getIntent().getStringExtra(
				"consultation_name");
		type = getIntent().getStringExtra("type");
		mHeaderView = View.inflate(this, R.layout.layout_comment_list_header,
				null);
		avatarImageView = (ImageView) mHeaderView.findViewById(R.id.iv_avatar);
		teacherName = (TextView) mHeaderView.findViewById(R.id.tv_name);
		mRatingBar = (RatingBar) mHeaderView.findViewById(R.id.ratingbar);
		jobTypeTextView = (TextView) mHeaderView.findViewById(R.id.tv_jobtype);
		teacherTypeTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_teachertype);
		teacherDescriptionTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_teacher_description);
		buyCountTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_buy_count);
		answerCountTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_answer_count);
		consultationNameTextView = (TextView) mHeaderView
				.findViewById(R.id.tv_consultation_name);
		iconImageView = (ImageView) mHeaderView.findViewById(R.id.iv_icon);
		if ("phone".equals(type)) {
			iconImageView.setImageResource(R.drawable.phone_consultation);
		} else {
			iconImageView.setImageResource(R.drawable.pic_consultation);
		}
		mImageLoader = ImageLoader.getInstance();
		mImageLoader.displayImage(
				Api.User.getAvatarUrl(teacher.getUseravatarversion()),
				avatarImageView);
		teacherName.setText(teacher.getUserrealname());
		String jobName = AppContext.getJobName(Integer.valueOf(teacher
				.getJobtype()));
		jobTypeTextView.setText(jobName);
		String teacherType = teacher.getTeachertype();
		if ("1".equals(teacherType)) {
			teacherTypeTextView.setText("专业导师");
		} else {
			teacherTypeTextView.setText("人力导师");
		}
		teacherDescriptionTextView
				.setText("简介" + teacher.getUserintroduction());
		buyCountTextView.setText(teacher.getBuycount() + "人购买");
		answerCountTextView.setText("回答" + teacher.getAnswercount() + "次");
		mRatingBar.setRating(Float.valueOf(teacher.getTeacherlevel()));
		mListView.addHeaderView(mHeaderView);
		adapter = new TeacherCommentAdapter(this, null);
		mListView.setAdapter(adapter);
		consultationApi = new ConsultationApi();
		consultationApi.getTeacherComment("14");
		backTextView.setVisibility(View.VISIBLE);
		consultationNameTextView.setText(consultation_name);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@OnClick(R.id.tv_buy)
	public void buy() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("购买会员更便宜,是否加入会员")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								ConsultationDetialActivity.this,
								BuyVipActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								ConsultationDetialActivity.this,
								PayActivity.class);
						startActivity(intent);
					}
				}).show();
	}

	@OnClick(R.id.text_left)
	public void back() {
		finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				TeacherComment.class, null), null,
				TeacherCommentTable.COLUMN_USERID + "=?",
				new String[] { "14" }, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null) {
			adapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

}
