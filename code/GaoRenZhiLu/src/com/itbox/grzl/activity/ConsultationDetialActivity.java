package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshListView;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.google.gson.Gson;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.GSON;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.TeacherCommentAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.TeacherCommentGet;
import com.itbox.grzl.bean.TeacherCommentList;
import com.itbox.grzl.bean.TeacherExtension;
import com.itbox.grzl.bean.UserLevel;
import com.itbox.grzl.bean.UserLevelList;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.UserEngine;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

/**
 * 咨询详情页
 * 
 * @author malinkang 2014年5月31日
 * 
 */
public class ConsultationDetialActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	PullToRefreshListView mListView;
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
	private TeacherExtension teacherExtension;
	private boolean isNotPrice;
	private int page = 1;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_picture_consultation_detial);
		ButterKnife.inject(this);
		teacher = (UserListItem) getIntent().getSerializableExtra("teacher");
		teacherExtension = (TeacherExtension) getIntent().getSerializableExtra(
				"teacherExtension");
		type = getIntent().getStringExtra("type");
		String consultation_name = getIntent().getStringExtra(
				"consultation_name");

		mHeaderView = View.inflate(this, R.layout.layout_comment_list_header,
				null);

		TextView tv_description = (TextView) mHeaderView
				.findViewById(R.id.tv_description);
		if ("picture".equals(type)) {
			setTitle("图文咨询详情");
			if (Double.parseDouble(teacherExtension.getPictureprice()) <= 0) {
				// tv_description.setText("当前导师未设置购买金额，不能购买");
				isNotPrice = true;
			}
		} else {
			setTitle("电话咨询详情");
			if (Double.parseDouble(teacherExtension.getPhoneprice()) <= 0) {
				// tv_description.setText("当前导师未设置购买金额，不能购买");
				isNotPrice = true;
			} else {
				// tv_description.setText("通过电话进行咨询专业导师");
			}
			tv_description.setText("通过电话进行咨询专业导师");
		}

		showLeftBackButton();

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
		iconImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buy();
			}
		});
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
		mListView.getRefreshableView().addHeaderView(mHeaderView);
		mListView.setMode(Mode.BOTH);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 刷新数据
				page = 1;
				loadData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 加载更多数据;
				page++;
				loadData();
			}

		});

		adapter = new TeacherCommentAdapter(this, null);
		mListView.setAdapter(adapter);
		consultationApi = new ConsultationApi();
		loadData();
		consultationNameTextView.setText(consultation_name);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	private void loadData() {
		consultationApi.getTeacherComment(teacher.getUserid(), page,
				new ResponseHandler() {
					@Override
					public void onSuccess(int statusCode, String content) {
						super.onSuccess(statusCode, content);
						TeacherCommentList mTeacherCommentList = new Gson()
								.fromJson(content, TeacherCommentList.class);
						if (page == 1) {
							new Delete()
									.from(TeacherCommentGet.class)
									.where(TeacherCommentGet.TEACHERUSERID
											+ "=?", teacher.getUserid())
									.execute();
						}
						if (mTeacherCommentList != null
								&& mTeacherCommentList.getTeacherCommentItem() != null) {
							ActiveAndroid.beginTransaction();
							try {
								for (TeacherCommentGet comment : mTeacherCommentList
										.getTeacherCommentItem()) {
									comment.save();
								}
								ActiveAndroid.setTransactionSuccessful();
							} finally {
								ActiveAndroid.endTransaction();
							}
						}
					}

					@Override
					public void onFinish() {
						mListView.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable error, String content) {
						page--;
					}
				});
	}

	@OnClick(R.id.tv_buy)
	public void buy() {
		// TODO 0元先让购买

//		if (isNotPrice) {
//			showToast("当前导师未设置购买金额，不能购买");
//			return;
//		}
		// 判断会员
		showLoadProgressDialog();
		UserEngine.getUserList(new GsonResponseHandler<Account>(Account.class) {
			@Override
			public void onSuccess(Account user) {
				super.onSuccess(user);
				if (user != null && user.getMemberid() > 0) {
					goPay(user.getMemberid());
				} else {
					AlertDialog.Builder builder = new Builder(mActThis);
					builder.setMessage("购买会员更便宜,是否加入会员")
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent intent = new Intent(
													ConsultationDetialActivity.this,
													BuyVipActivity.class);
											startActivity(intent);
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											goPay(0);
										}

									}).show();
				}
			}

			@Override
			public void onFinish() {
				dismissProgressDialog();
			}
		});
	}

	private void goPay(final int memberid) {
		if (memberid > 0) {
			// 计算会员折扣价
			showLoadProgressDialog();
			ConsultationEngine.getUserMember(new ResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String content) {
					super.onSuccess(statusCode, headers, content);
					UserLevelList userLevelList = GSON.getObject(content,
							UserLevelList.class);
					if (userLevelList != null
							&& userLevelList.getUserMemberItem() != null) {
						for (UserLevel level : userLevelList
								.getUserMemberItem()) {
							if (level != null
									&& (memberid == level.getMemberid())) {
								// 计算折扣
								goPayActivity(level.getDiscount());
							}
						}
					}
				}

				public void onFinish() {
					dismissProgressDialog();
				};
			});
		} else {
			// 不是会员按原价计算
			goPayActivity(1.0f);
		}
	}

	private void goPayActivity(float dis) {
		teacherExtension.setFinalPhoneprice(Double.parseDouble(teacherExtension
				.getPhoneprice()) * dis);
		teacherExtension.setFinalPictureprice(Double
				.parseDouble(teacherExtension.getPictureprice()) * dis);
		Intent intent = new Intent(ConsultationDetialActivity.this,
				PayActivity.class);
		intent.putExtra("teacher", teacher);
		intent.putExtra("type", type);
		intent.putExtra("teacherExtension", teacherExtension);
		startActivity(intent);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				TeacherCommentGet.class, null), null,
				TeacherCommentGet.TEACHERUSERID + "=?",
				new String[] { teacher.getUserid() }, null);
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
