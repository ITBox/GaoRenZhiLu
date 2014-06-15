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
import com.itbox.grzl.bean.UserProblem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

/**
 * 
 * 
 * @author Baoyz
 * 
 */
public class UserProblemAdapter extends CursorAdapter {

	private Context mContext;

	public UserProblemAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_user_problem,
				null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		UserProblem bean = new UserProblem();
		bean.loadFromCursor(cursor);
		holder.tv_time.setText(bean.getCreatetime());
		holder.tv_title.setText(bean.getTitle());
		holder.tv_dis.setText(bean.getContents());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getPhoto()),
				holder.iv_head);
	}

	static class ViewHolder {
		@InjectView(R.id.iv_head)
		ImageView iv_head;
		@InjectView(R.id.tv_time)
		TextView tv_time;
		@InjectView(R.id.tv_title)
		TextView tv_title;
		@InjectView(R.id.tv_dis)
		TextView tv_dis;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
