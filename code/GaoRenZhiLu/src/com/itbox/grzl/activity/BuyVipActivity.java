package com.itbox.grzl.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.UserLevelAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.UserLevel;
import com.itbox.grzl.constants.UserLevelTable;
import com.itbox.grzl.engine.ConsultationEngine;
import com.zhaoliewang.grzl.R;

public class BuyVipActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	ListView listView;
	private ConsultationApi consultationApi;
	private UserLevelAdapter adapter;
	private View mHeaderView;

	@InjectView(R.id.cb_client)
	protected CheckBox cb_client;
	@InjectView(R.id.cb_web)
	protected CheckBox cb_web;

	private boolean isClient;
	private UserLevel bean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_buy_vip);
		setTitle("购买会员");
		showLeftBackButton();
		ButterKnife.inject(this);
		consultationApi = new ConsultationApi();
		consultationApi.getUserLevel(AppContext.getInstance().getAccount()
				.getUserid().toString());
		mHeaderView = View.inflate(this, R.layout.layout_buy_vip_header, null);
		listView.addHeaderView(mHeaderView);
		adapter = new UserLevelAdapter(this, null);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				bean = new UserLevel();
				bean.loadFromCursor((Cursor) adapter.getItem(position - 1));
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@OnCheckedChanged({ R.id.cb_client, R.id.cb_web })
	public void payCheck(CompoundButton cb, boolean isCheck) {
		if (isCheck == true) {
			switch (cb.getId()) {
			case R.id.cb_client:
				cb_web.setChecked(false);
				isClient = true;
				break;
			case R.id.cb_web:
				cb_client.setChecked(false);
				isClient = false;
				break;
			}
		}
	}

	@OnClick(R.id.tv_buy)
	public void buy(View v) {
		if (bean == null) {
			showToast("请选择会员");
			return;
		}
		ConsultationEngine.buyMember(bean.getMemberid().toString(), isClient,
				null);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				UserLevel.class, null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (arg0 != null) {
			adapter.swapCursor(cursor);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
