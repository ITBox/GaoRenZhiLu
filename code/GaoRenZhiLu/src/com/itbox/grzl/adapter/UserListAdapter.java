package com.itbox.grzl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.UserListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserListAdapter extends CursorAdapter {

	class ViewHolder {
		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}

		@InjectView(R.id.iv_avatar)
		CircleImageView avatarImageView;
		@InjectView(R.id.tv_name)
		TextView nameTextView;
		@InjectView(R.id.ratingbar)
		RatingBar ratingBar;
		@InjectView(R.id.tv_jobtype)
		TextView jobTypeTextView;
		@InjectView(R.id.tv_teachertype)
		TextView teacherTypeTextView;
		@InjectView(R.id.tv_teacher_description)
		TextView teacherDescriptionTextView;
		@InjectView(R.id.tv_buy_count)
		TextView buyCountTextView;
		@InjectView(R.id.tv_answer_count)
		TextView answerCountTextView;

	}

	private ImageLoader mImageLoader;
	private LayoutInflater mInflater;

	public UserListAdapter(Context context, Cursor c) {
		super(context, c);
		mImageLoader = ImageLoader.getInstance();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		UserListItem item = new UserListItem();
		item.loadFromCursor(cursor);
		holder.nameTextView.setText(item.getUserrealname());
		String jobName = AppContext.getJobName(Integer.valueOf(item
				.getJobtype()));
		holder.jobTypeTextView.setText(jobName);
		String teacherType = item.getTeachertype();
		if ("1".equals(teacherType)) {
			holder.teacherTypeTextView.setText("专业导师");
		} else {
			holder.teacherTypeTextView.setText("人力导师");
		}

		holder.teacherDescriptionTextView.setText("简介 "
				+ item.getUserintroduction());
		holder.buyCountTextView.setText(item.getBuycount() + "人购买");
		holder.answerCountTextView.setText("回答" + item.getAnswercount() + "次");
		mImageLoader.displayImage(
				Api.User.getAvatarUrl(item.getUseravatarversion()),
				holder.avatarImageView);

		holder.ratingBar.setRating(Float.valueOf(item.getTeacherlevel()));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		return mInflater.inflate(R.layout.layout_teacher_item, null);

	}

}
