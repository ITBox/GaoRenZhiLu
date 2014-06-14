package com.itbox.grzl.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.IntentUtil;
import com.itbox.grzl.Api;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.OnLineItem;
import com.itbox.grzl.bean.OnLineItem.Item;

/**
 * 在线学习页面
 * 
 * @author baoyz
 * 
 *         2014-5-2 下午6:13:21
 * 
 */
public class OnlineStudyFragment extends BaseFragment implements
		OnItemClickListener {

	@InjectView(R.id.grid)
	protected GridView mGridView;
	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;

	private List<Item> mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_study, null);

		ButterKnife.inject(this, view);

		initView();

		loadData();

		return view;
	}

	private void initView() {
		// 设置标题
		mTitleTv.setText(R.string.online_study);
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		if (mList == null) {
			// 从网络加载数据
			showLoadProgressDialog();
			Net.request(null, Api.getUrl(Api.Online.getOnline),
					new GsonResponseHandler<OnLineItem>(OnLineItem.class) {

						@Override
						public void onSuccess(OnLineItem item) {
							super.onSuccess(item);
							mList = item.getOnlineItem();
							initAdapter();
						}

						@Override
						public void onFinish() {
							super.onFinish();
							dismissProgressDialog();
						}

					});
		} else {
			initAdapter();
		}
	}

	private void initAdapter() {
		mGridView.setAdapter(new ArrayAdapter<OnLineItem.Item>(getActivity(),
				R.layout.item_grid_online_study, R.id.textview, mList));
	}

	@OnItemClick(R.id.grid)
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Item item = mList.get(position);
		// 统计
		Net.request("id", item.getId() + "",
				Api.getUrl(Api.Online.addStatistics), null);
		// 打开浏览器
		IntentUtil.startWebActivity(getActivity(), item.getLink());
	}
}
