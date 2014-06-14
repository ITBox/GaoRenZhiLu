package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
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

	@InjectView(R.id.tv_select_teachertype)
	TextView teacherTypeTextView;
	@InjectView(R.id.tv_select_jobtype)
	TextView jobTypeTextView;

	@InjectView(R.id.text_medium)
	TextView mediumTextView;

	private ConsultationApi consultationApi;
	private UserListAdapter adapter;
	private String jobtype;
	private String teachertype;
	private String jobtypeName;
	private String teachertypeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_search);
		ButterKnife.inject(this);
		mediumTextView.setText("咨询搜索");
		jobtype = getIntent().getStringExtra("jobtype");
		teachertype = getIntent().getStringExtra("teachertype");
		jobtypeName = getIntent().getStringExtra("jobtypename");
		teachertypeName = getIntent().getStringExtra("teachertypename");
		if (jobtypeName != null) {
			jobTypeTextView.setText(jobtypeName);
		}

		if (teachertype != null) {
			teacherTypeTextView.setText(teachertypeName);
		}

		consultationApi = new ConsultationApi();
		adapter = new UserListAdapter(this, null);
		mListView.setAdapter(adapter);

		if (jobtype == null) {
			jobtype = "0";
		}
		if (teachertype == null) {
			teachertype = "0";
		}
		consultationApi.searchConsultation(null, jobtype, teachertype);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
				UserListItem teacher = new UserListItem();
				teacher.loadFromCursor(cursor);
				Intent intent = new Intent(ConsultationSearchActivity.this,
						TeacherDetialActivity.class);
				intent.putExtra("teacher", teacher);
				startActivity(intent);
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		if (jobtype.equals("0")) {
			return new CursorLoader(this, ContentProvider.createUri(
					UserListItem.class, null), null,
					UserListItemTable.COLUMN_TEACHERTYPE + "=?",
					new String[] { teachertype }, null);
		} else if (teachertype.equals("0")) {
			return new CursorLoader(this, ContentProvider.createUri(
					UserListItem.class, null), null,
					UserListItemTable.COLUMN_JOBTYPE + "=?",
					new String[] { jobtype }, null);
		} else {
			return new CursorLoader(this, ContentProvider.createUri(
					UserListItem.class, null), null,
					UserListItemTable.COLUMN_JOBTYPE + "=? and "
							+ UserListItemTable.COLUMN_TEACHERTYPE + "=?",
					new String[] { jobtype, teachertype }, null);
		}

	}

	@OnClick(R.id.tv_select_teachertype)
	public void selectTeacherType() {
		AlertDialog.Builder builder = new Builder(this);
		final String[] teacherNames = { "专业导师", "人力导师" };
		builder.setItems(teacherNames, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				teachertype = (arg1 + 1) + "";
				teacherTypeTextView.setText(teacherNames[arg1]);
				consultationApi.searchConsultation(null, jobtype, teachertype);
				getSupportLoaderManager().restartLoader(0, null,
						ConsultationSearchActivity.this);
			}
		}).show();
	}

	@OnClick(R.id.tv_select_jobtype)
	public void selectJobType() {
		AlertDialog.Builder builder = new Builder(this);
		final String[] jobNames = AppContext.getJobNameArray();
		builder.setItems(jobNames, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				jobtype = (arg1 + 1) + "";
				jobTypeTextView.setText(jobNames[arg1]);
				consultationApi.searchConsultation(null, jobtype, teachertype);
				getSupportLoaderManager().restartLoader(0, null,
						ConsultationSearchActivity.this);
			}
		}).show();
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
