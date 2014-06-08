package com.itbox.grzl.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.UserLevelAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.UserLevel;
import com.itbox.grzl.constants.UserLevelTable;

public class BuyVipActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.text_left)
	TextView backTextView;
	@InjectView(R.id.list)
	ListView listView;
	private ConsultationApi consultationApi;
	private UserLevelAdapter adapter;
	private View mFooterView;
	private View mHeaderView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_buy_vip);
		ButterKnife.inject(this);
		backTextView.setVisibility(View.VISIBLE);
		consultationApi = new ConsultationApi();
		consultationApi.getUserLevel("14");
		adapter = new UserLevelAdapter(this, null);
		listView.setAdapter(adapter);
		mHeaderView = View.inflate(this, R.layout.layout_buy_vip_header, null);
		getSupportLoaderManager().initLoader(0, null, this);
		mFooterView = View.inflate(this, R.layout.layout_buy_vip_footer, null);
		listView.addHeaderView(mHeaderView);
		listView.addFooterView(mFooterView);
	}

	@OnClick(R.id.text_left)
	public void back() {
		finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				UserLevel.class, null), null, UserLevelTable.COLUMN_USER_ID
				+ "=?", new String[] { "14" }, null);
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
