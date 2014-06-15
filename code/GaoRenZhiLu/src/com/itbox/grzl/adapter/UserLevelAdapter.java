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
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.UserLevel;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserLevelAdapter extends CursorAdapter {

	static class ViewHolder {
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}

		@InjectView(R.id.iv_level_icon)
		ImageView levelIcon;
		@InjectView(R.id.tv_teacher_name)
		TextView teacherName;
		@InjectView(R.id.tv_level_description)
		TextView levelDescription;
		@InjectView(R.id.tv_price)
		TextView price;
	}

	public UserLevelAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		UserLevel bean = new UserLevel();
		bean.loadFromCursor(cursor);
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getPhoto()), holder.levelIcon);
		holder.price.setText(bean.getPrice() + "");
		holder.teacherName.setText(bean.getTitle());
		holder.levelDescription.setText("加入" + bean.getTitle() + "折扣为"
				+ bean.getDiscount() * 10 + "折");
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getPhoto()), holder.levelIcon);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = View.inflate(mContext, R.layout.layout_user_level_item,
				null);
		new ViewHolder(view);
		return view;
	}

}
