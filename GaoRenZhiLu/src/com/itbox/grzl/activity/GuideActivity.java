package com.itbox.grzl.activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.widget.adapter.ViewPagerAdapter;
import com.itbox.grzl.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;



/**
 * Created by huiyh on 2014/4/2.
 */
public class GuideActivity  extends BaseActivity{
	public static final String HAS_SHOW_GUIDE = "hasShowGuide";
	private static final List<Integer> guideImages = new ArrayList<Integer>();
	static{
		guideImages.add(R.drawable.image_guide_0);
		guideImages.add(R.drawable.image_guide_1);
		guideImages.add(R.drawable.image_guide_2);
		guideImages.add(R.drawable.image_guide_3);
	}
	
	@InjectView(R.id.viewpager) ViewPager mViewPager;
	private GuideAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences appConfig = AppContext.getAppPreferences();
		appConfig.edit().putBoolean(HAS_SHOW_GUIDE, false).commit();

		setContentView(R.layout.activity_guride);
		ButterKnife.inject(this);
		mAdapter = new GuideAdapter(this,guideImages);
		mViewPager.setAdapter(mAdapter);
	}
	
	public class GuideAdapter extends ViewPagerAdapter<Integer> implements OnClickListener{

		public GuideAdapter(Context context, List<Integer> list) {
			super(context, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = null;
			if(convertView == null){
				convertView = View.inflate(context, R.layout.item_guide, null);
				image = (ImageView) convertView.findViewById(R.id.imageview);
				image.setOnClickListener(this);
				convertView.setTag(image);
			}else{
				image = (ImageView) convertView.getTag();
			}
			image.setTag(position);;
			image.setImageResource(contentList.get(position));
			return convertView;
		}

		@Override
		public int getCount() {
			return contentList.size() ;
		}
		
		@Override
		public void onClick(View v) {
			int position = (Integer)v.getTag();
			if(position + 1 == contentList.size() ){
				setResult(RESULT_OK);
				finish();
				return;
			}
		}
	}
}
