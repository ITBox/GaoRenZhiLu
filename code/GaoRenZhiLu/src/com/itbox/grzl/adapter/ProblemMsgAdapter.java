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
import com.itbox.grzl.bean.ProblemMsg;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

/**
 * 活动交流适配器
 * 
 * @author baoboy
 * @date 2014-5-27上午12:13:56
 */
public class ProblemMsgAdapter extends CursorAdapter {

	private Context mContext;

	public ProblemMsgAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		ProblemMsg bean = new ProblemMsg();
		bean.loadFromCursor((Cursor) getItem(position));
		return bean.getViewType();
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ProblemMsg bean = new ProblemMsg();
		bean.loadFromCursor(cursor);
		int viewId = 0;
		switch (bean.getViewType()) {
		case 0:
			// 左边
			viewId = R.layout.item_chat_right_text;
			break;
		case 1:
			// 右边
			viewId = R.layout.item_chat_right_text;
			break;
		}
		View view = View.inflate(mContext, viewId, null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		ProblemMsg bean = new ProblemMsg();
		bean.loadFromCursor(cursor);
		holder.tv_content.setText(bean.getCommentcontent());
		holder.tv_time.setText(bean.getCreatetime());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(bean.getUseravatarversion()),
				holder.iv_head);
	}

	static class ViewHolder {
		@InjectView(R.id.iv_head)
		ImageView iv_head;
		@InjectView(R.id.tv_text)
		TextView tv_content;
		@InjectView(R.id.tv_time)
		TextView tv_time;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
