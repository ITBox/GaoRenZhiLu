package com.itbox.grzl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.CommentMarkGet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 
 * @author Baoyz
 * 
 */
public class CommentMarkAdapter extends CursorAdapter {

	private Context mContext;
	private int gray;

	public CommentMarkAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
		gray = AppContext.getInstance().getResources()
				.getColor(R.color.gray_e8);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_comment_mark,
				null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		CommentMarkGet bean = new CommentMarkGet();
		bean.loadFromCursor(cursor);
		holder.tv_content.setText(bean.getCommentcontent());
		holder.tv_time.setText(bean.getCreatetime());
		holder.tv_name.setText(bean.getUsername());
		holder.tv_floor.setText((cursor.getPosition() + 1) + "楼");
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getUseravatarversion()),
				holder.iv_head);
		if ((cursor.getPosition() % 2) == 0) {
			// 偶数，白色背景
			view.setBackgroundColor(Color.WHITE);
		} else {
			// 灰色背景
			view.setBackgroundColor(gray);
		}
	}

	static class ViewHolder {
		@InjectView(R.id.iv_head)
		ImageView iv_head;
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@InjectView(R.id.tv_content)
		TextView tv_content;
		@InjectView(R.id.tv_floor)
		TextView tv_floor;
		@InjectView(R.id.tv_time)
		TextView tv_time;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
