package com.itbox.grzl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CommentGet;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 
 * @author Baoyz
 * 
 */
public class CommentListAdapter extends CursorAdapter {

	private Context mContext;

	public CommentListAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_comment, null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		CommentGet bean = new CommentGet();
		bean.loadFromCursor(cursor);
		holder.tv_name.setText(bean.getUsername());
		holder.tv_title.setText(bean.getTitle());
		holder.tv_count.setText("回复数：" + bean.getReplacecount());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getPhoto()), holder.iv_head);
	}

	static class ViewHolder {
		@InjectView(R.id.iv_head)
		ImageView iv_head;
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@InjectView(R.id.tv_title)
		TextView tv_title;
		@InjectView(R.id.tv_count)
		TextView tv_count;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
