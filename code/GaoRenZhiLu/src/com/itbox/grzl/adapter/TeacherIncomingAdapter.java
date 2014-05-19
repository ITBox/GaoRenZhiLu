package com.itbox.grzl.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.R;
import com.itbox.grzl.bean.TeacherIncoming;

/**
 * 收入明细列表适配器
 * 
 * @author byz
 * @date 2014-5-18下午4:52:04
 */
public class TeacherIncomingAdapter extends CursorAdapter {

	private Context mContext;

	public TeacherIncomingAdapter(Context context, Cursor c) {
		super(context, c, true);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_list_teacher_incoming,
				null);
		new ViewHolder(view);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		TeacherIncoming bean = new TeacherIncoming();
		bean.loadFromCursor(cursor);
		holder.tv_id.setText(bean.getPayid() + "");
		holder.tv_name.setText(bean.getPaytype());
		holder.tv_time.setText(bean.getCreatetime());
		holder.tv_price.setText(bean.getPrice() + "元");
		holder.tv_state.setText(bean.getPayStateName());
	}

	static class ViewHolder {
		@InjectView(R.id.tv_name)
		TextView tv_name;
		@InjectView(R.id.tv_id)
		TextView tv_id;
		@InjectView(R.id.tv_time)
		TextView tv_time;
		@InjectView(R.id.tv_state)
		TextView tv_state;
		@InjectView(R.id.tv_price)
		TextView tv_price;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
