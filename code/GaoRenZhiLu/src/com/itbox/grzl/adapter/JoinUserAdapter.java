package com.itbox.grzl.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.EventUser;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 参与用户列表适配器
 * 
 * @author baoboy
 * @date 2014-5-26下午11:33:24
 */
public class JoinUserAdapter extends BaseAdapter {

	private Context mContext;
	private List<EventUser> mList;

	public JoinUserAdapter(Context context, List<EventUser> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public EventUser getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_list_join_user,
					null);
			holder = new ViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance()
				.displayImage(
						Api.User.getAvatarUrl(getItem(position)
								.getUseravatarversion()), holder.iv_head);
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.iv_head)
		ImageView iv_head;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			view.setTag(this);
		}
	}

}
