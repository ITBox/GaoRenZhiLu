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
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CommentMarkGet;
import com.itbox.grzl.bean.EventCommentGet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 
 * @author Baoyz
 * 
 */
public class EventCommentAdapter extends CursorAdapter {

	private Context mContext;
	private int gray;

	public EventCommentAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
		gray = AppContext.getInstance().getResources()
				.getColor(R.color.gray_e8);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_event_comment,
				null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		EventCommentGet bean = new EventCommentGet();
		bean.loadFromCursor(cursor);
		holder.tv_name.setText(bean.getUserid());
		holder.tv_content.setText(bean.getCommentcontent());
		holder.tv_time.setText(bean.getCreateTime());
	}

	static class ViewHolder {
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@InjectView(R.id.tv_content)
		TextView tv_content;
		@InjectView(R.id.tv_time)
		TextView tv_time;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
