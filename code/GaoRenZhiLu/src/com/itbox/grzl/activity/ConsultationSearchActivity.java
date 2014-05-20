package com.itbox.grzl.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.UserListAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.constants.UserListItemTable;

/**
 * 咨询搜索页面
 * 
 * @author baoyz
 * 
 *         2014-5-3 下午3:54:06
 * 
 */
public class ConsultationSearchActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	protected ListView mListView;
	private ConsultationApi consultationApi;
	private UserListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_search);
		ButterKnife.inject(this);
		String jobtype = getIntent().getStringExtra("jobtype");
		String teachertype = getIntent().getStringExtra("teachertype");
		consultationApi = new ConsultationApi();
		adapter = new UserListAdapter(this, null);
		mListView.setAdapter(adapter);
		consultationApi.searchConsultation(null, "0", "0");
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				UserListItem.class, null), null,
				UserListItemTable.COLUMN_JOBTYPE + "=? and "
						+ UserListItemTable.COLUMN_TEACHERTYPE + "=?",
				new String[] { "0", "0" }, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		if (arg1 != null) {
			// 数据加载完毕
			adapter.swapCursor(arg1);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}
}
