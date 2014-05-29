package com.itbox.grzl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.TeacherCommentGet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 我的评论列表适配器
 * 
 * @author baoboy
 * @date 2014-5-29下午11:05:59
 */
public class TeacherCommentAdapter extends CursorAdapter {

	private Context mContext;
	private int gray;

	public TeacherCommentAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
		gray = AppContext.getInstance().getResources()
				.getColor(R.color.gray_e8);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_teacher_comment,
				null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		TeacherCommentGet bean = new TeacherCommentGet();
		bean.loadFromCursor(cursor);
		holder.tv_name.setText(bean.getUsername());
		holder.tv_time.setText(bean.getCreatetime());
		holder.tv_content.setText(bean.getCommentcontent());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getUseravatarversion()),
				holder.iv_head);
		holder.rb_rating.setProgress((int) bean.getScore());
		if ((cursor.getPosition() % 2) == 0) {
			// 偶数，白色背景
			view.setBackgroundColor(Color.WHITE);
		} else {
			// 灰色背景
			view.setBackgroundColor(gray);
		}
	}

	static class ViewHolder {
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@InjectView(R.id.tv_time)
		TextView tv_time;
		@InjectView(R.id.tv_content)
		TextView tv_content;
		@InjectView(R.id.ratingbar)
		RatingBar rb_rating;
		@InjectView(R.id.iv_head)
		ImageView iv_head;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
