package com.itbox.grzl.fragment;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.core.AppTime;
import com.itbox.fx.net.NetCache;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.DateUtil;
import com.itbox.fx.util.GSON;
import com.itbox.fx.widget.adapter.ViewPagerAdapter;
import com.itbox.grzl.Extra;
import com.itbox.grzl.R;
import com.itbox.grzl.activity.EventAddGuideActivity;
import com.itbox.grzl.activity.EventDetialActivity;
import com.itbox.grzl.activity.EventMapModeActivity;
import com.itbox.grzl.activity.EventSearchInitialActivity;
import com.itbox.grzl.activity.MainActivity;
import com.itbox.grzl.activity.SelectCityActivity;
import com.itbox.grzl.bean.AreaData;
import com.itbox.grzl.common.util.AddressUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author hyh creat_at：2013-9-3-下午4:42:56
 */
public class EventFragment extends BaseFragment implements OnClickListener, OnItemClickListener, OnRefreshListener2<ListView> {

	private ListView mListView;
	private View emptyView;
	private LayoutInflater inflater;
	private List<EventListItem> mList = new ArrayList<EventListItem>();
	private List<EventListItem> hotList;
	private List<ImageView> views = new ArrayList<ImageView>();

	private EventListAdapter mAdapter;
	private View vHeader;
	private ViewPager glHotImage;
	private ViewPagerAdapter<String> pagerAdapter;
	private TextView tvHotTitle;
	private ImageAsyncLoader loader;
//	private String[] types;
	private LinearLayout llIndic;
	private PullToRefreshListView mPullRefreshListView;
	private View contentView;
	private EventListTModel model;
	private boolean isRefresh;
	private ResponseHandler moreHandler;
	private ResponseHandler initHandler;
	private TextView tvCity;
	private View btnMapMode;
	private View btnSearch;
	private Intent cityIntent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		cityIntent = new Intent(getActivity(),EventSearchInitialActivity.class);
		if (null == contentView) {
//			loader = OriginalImageAsyncLoader.getInstance(); // TODO
//			types = AppContext.getRes().getStringArray(R.array.event_tags);
			initRequestModel(AddressUtil.getLocArea());//使用定位
			contentView = inflater.inflate(R.layout.frag_event, container, false);
			vHeader = inflater.inflate(R.layout.header_hot_event, null);
			initView();
			initEventList();
		} else {
			((ViewGroup) contentView.getParent()).removeView(contentView);
		}
		if (pagerAdapter != null) {
			pagerAdapter.notifyDataSetChanged();
		}
		if (null != mAdapter) {
			mAdapter.notifyDataSetChanged();
		}
		return contentView;
	}

	private void initView() {
		tvCity = (TextView)contentView.findViewById(R.id.event_tv_city);
		tvCity.setOnClickListener(this);
		btnMapMode = contentView.findViewById(R.id.event_iv_map);
		btnMapMode.setOnClickListener(this);
		btnSearch = contentView.findViewById(R.id.event_iv_search);
		btnSearch.setOnClickListener(this);
		emptyView = contentView.findViewById(R.id.event_ll_empty);
		emptyView.findViewById(R.id.event_empty_other_area).setOnClickListener(this);
		emptyView.findViewById(R.id.event_empty_add_event).setOnClickListener(this);

		glHotImage = (ViewPager) vHeader.findViewById(R.id.header_viewpager);
		llIndic = (LinearLayout) vHeader.findViewById(R.id.header_ll_indic);
		tvHotTitle = (TextView) vHeader.findViewById(R.id.header_tv_title);
				
		mPullRefreshListView = (PullToRefreshListView) contentView.findViewById(R.id.event_lv);
		mPullRefreshListView.setMode(Mode.BOTH);
		mListView = mPullRefreshListView.getRefreshableView();
		mListView.setHeaderDividersEnabled(false);
		mPullRefreshListView.getRefreshableView().setSelector(
				new ColorDrawable(Color.TRANSPARENT ));
		mListView.setEmptyView(emptyView);
		mPullRefreshListView.setOnItemClickListener(this);
		mListView.addHeaderView(vHeader, null, true);
		mListView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		mAdapter = new EventListAdapter(mList, inflater);
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
		glHotImage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				EventListItem item = hotList.get(position);
				tvHotTitle.setText("[" + types[item.getTypeid() - 1] + "]" + item.getTitle());
				for (int i = 0; i < views.size(); i++) {
					views.get(i).setBackgroundResource(R.drawable.ring_gary);
				}
				views.get(position).setBackgroundResource(R.drawable.ring_white);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		if (null != pagerAdapter) {
			pagerAdapter.notifyDataSetChanged();
		}
	}

	private void initRequestModel(AreaData data){
		if(null == model){
			model = new EventListTModel();
		}
		if (null != data) {
			model.setUsercity(data.getCode());
			switch (data.getCode()) {
			case 110000:
			case 120000:
			case 310000:
			case 500000:
				model.setUserprovince(data.getCode());
				break;
			default:
				model.setUserprovince(data.getParentCode());
				break;
			}
		} else if (null != MyinfoManager.getUserInfo()) {
			model.setUserprovince(MyinfoManager.getUserInfo().getUserProvince());
			model.setUsercity(MyinfoManager.getUserInfo().getUserCity());
		}
	}
	
	/**初始化和下拉刷新<p>刷新是不显示ProgressDialog*/
	private void initEventList() {
		if(null == initHandler){
			initHandler = new ResponseHandler(true){
				@Override
				public void onSuccess(String content) {
					isRefresh = false;
					ArrayList<EventListItem> list = JSON.getObject(content, EventListRModel.class).ActivityListItem;
					if(null != list){
						mList.clear();
						mList.addAll(list);
						mAdapter.notifyDataSetChanged();
						initHotEvent();
						mPullRefreshListView.setMode(Mode.BOTH);
					}
					if(mList.isEmpty()){
						hideRightBtn();
					}else{
						showRightBtn();
					}
					mPullRefreshListView.onRefreshComplete();
				}
				@Override
				public void onFailure(Throwable e, int statusCode, String content) {
					super.onFailure(e, statusCode, content);
					mPullRefreshListView.onRefreshComplete();
					if(400 == statusCode){
						mList.clear();
						mAdapter.notifyDataSetChanged();
						mPullRefreshListView.setMode(Mode.PULL_FROM_START);
					}
					hideRightBtn();
				}
			};
		}
		initHandler.setShowProgress(!isRefresh);
		model.setPageindex(Const.PAGE_DEFAULT_INDEX);
		Net.cacheRequest(model, WebApi.Event.GetEventList, initHandler);	
	}
	
	private void loadMore(){
		if(null == moreHandler){
			moreHandler = new ResponseHandler(!isRefresh){
				@Override
				public void onSuccess(String content) {
					ArrayList<EventListItem> list = GSON.getObject(content, EventListRModel.class).ActivityListItem;
					mPullRefreshListView.onRefreshComplete();
					if(null != list){
						mList.addAll(list);
						mAdapter.notifyDataSetChanged();
						if(model.getPagesize() > list.size()){
							mPullRefreshListView.setMode(Mode.PULL_FROM_START);
						}
					}else{
						mPullRefreshListView.setMode(Mode.PULL_FROM_START);
					}
					if(!mList.isEmpty()){
						showRightBtn();
					}
				}
				@Override
				public void onFailure(Throwable e, int statusCode, String content) {
					super.onFailure(e, statusCode, content);
					mPullRefreshListView.onRefreshComplete();
					if(400 == statusCode){
						mPullRefreshListView.setMode(Mode.PULL_FROM_START);
					}
				}
			};
		}
		model.setPageindex(model.getPageindex() + 1);
		Net.cacheRequest(model, WebApi.Event.GetEventList, moreHandler);	
	}
	
	private void initHotEvent(){
		EventListManager.getInstance().getHotEvent(new CallBack<List<EventListItem>>() {
			@Override
			public void onCallBack(boolean isSuccess,List<EventListItem> data) {
				if (null != data && !data.isEmpty()) {
					hotList = data;
					if (null != pagerAdapter) {
						pagerAdapter.notifyDataSetChanged();
					}
					for(View view : views){
						llIndic.removeView(view);
					}
					views.clear();
					for (int i = 0; i < hotList.size(); i++) {
						ImageView view = (ImageView) View.inflate(getActivity(), R.layout.item_hotevent_pointer, null);
						int dimension = (int) (getResources().getDimension(R.dimen.dip8));
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dimension, dimension);

						params.setMargins(0, 0, dimension / 2, 0);
						view.setLayoutParams(params);
						views.add(view);
						if (i == 0) {
							view.setBackgroundResource(R.drawable.ring_white);
						} else {
							view.setBackgroundResource(R.drawable.ring_gary);
						}
						llIndic.addView(view);
					}
					pagerAdapter = new ViewPagerAdapter() {

						@Override
						public int getCount() {
							return hotList.size();
						}

						@Override
						public View getView(int position, View convertView, ViewGroup parent) {
							if (null == convertView) {
								convertView = View.inflate(getActivity(), R.layout.item_hotevent, null);
							}
							String imageUrl = WebApi.getImageUrl(hotList.get(position).getActivitypicture(),"/450x0.jpg");
							loader.loadBitmap(imageUrl, (ImageView) convertView);
							return convertView;
						}
					};
					glHotImage.setAdapter(pagerAdapter);
					EventListItem item = hotList.get(0);
					tvHotTitle.setText(item.getTitle());
					glHotImage.setVisibility(View.VISIBLE);
					llIndic.setVisibility(View.VISIBLE);
					mListView.setHeaderDividersEnabled(true);
				}
			}
		});
	}

	private void showRightBtn(){
		btnMapMode.setVisibility(View.VISIBLE);
//		btnSearch.setVisibility(View.VISIBLE);
	}
	private void hideRightBtn(){
		btnMapMode.setVisibility(View.INVISIBLE);
//		btnSearch.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.event_tv_city:
			startActivityForResult(SelectCityActivity.class, MainActivity.REQUEST_CODE_CITY);
			break;
		case R.id.event_iv_map:
			startActivity(EventMapModeActivity.class);
			break;
		case R.id.event_iv_search:
			//TODO 
			getActivity().startActivity(cityIntent);
//			AppContext.startAct(EventSearchInitialActivity.class);
			
			break;
		case R.id.event_empty_other_area:
			startActivityForResult(SelectCityActivity.class, MainActivity.REQUEST_CODE_CITY);
			break;
		case R.id.event_empty_add_event:
			startActivity(EventAddGuideActivity.class);
//			EventEngine.publishEvent();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case MainActivity.REQUEST_CODE_CITY:
			if (Activity.RESULT_OK == resultCode && null != data) {
				AreaData area = (AreaData) data.getSerializableExtra(Extra.SelectedItem);
				if (null != area) {
					cityIntent.putExtra(Extra.SelectedItem, area);
					tvCity.setText(area.getAreaName());
					initRequestModel(area);
					initEventList();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		int hearders = mListView.getHeaderViewsCount();
		Intent intent = new Intent(getActivity(),EventDetialActivity.class);
		intent.putExtra(Extra.SelectedID,mList.get(position-hearders).getActivityid());
		startActivity(intent);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(DateUtil.formatDate(AppTime.getTimeMillis()));
		isRefresh = true;
		NetCache.clearCaches();
		initEventList();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		loadMore();
	}

}
