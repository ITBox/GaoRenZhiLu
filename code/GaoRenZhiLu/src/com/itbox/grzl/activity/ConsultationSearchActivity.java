package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.UserListAdapter;
import com.itbox.grzl.bean.UserList;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.ConsultationEngine.GetTeacher;
import com.zhaoliewang.grzl.R;

/**
 * 咨询搜索页面
 * 
 * @author baoyz
 * 
 *         2014-5-3 下午3:54:06
 * 
 */
public class ConsultationSearchActivity extends BaseLoadActivity<UserListItem> {

	@InjectView(R.id.list)
	protected PullToRefreshListView mListView;

	@InjectView(R.id.tv_select_teachertype)
	TextView teacherTypeTextView;
	@InjectView(R.id.tv_select_jobtype)
	TextView jobTypeTextView;

	private UserListAdapter adapter;
	private GetTeacher info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_search);
		ButterKnife.inject(this);
		setTitle("咨询搜索");
		showLeftBackButton();

		info = new GetTeacher();
		info.setJobtype(getIntent().getStringExtra("jobtype"));
		info.setTeachertype(getIntent().getStringExtra("teachertype"));
		info.setRealname(getIntent().getStringExtra("realname"));
		String jobtypeName = getIntent().getStringExtra("jobtypename");
		String teachertypeName = getIntent().getStringExtra("teachertypename");
		if (jobtypeName != null) {
			jobTypeTextView.setText(jobtypeName);
		}

		if (teachertypeName != null) {
			teacherTypeTextView.setText(teachertypeName);
		}

		adapter = new UserListAdapter(this, null);
		mListView.setAdapter(adapter);

		new Delete().from(UserListItem.class).execute();

		initLoad(mListView, adapter, UserListItem.class);
		mListView.setMode(Mode.PULL_FROM_END);

		showProgressDialog("正在搜索...");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		UserListItem teacher = new UserListItem();
		teacher.loadFromCursor(cursor);
		Intent intent = new Intent(ConsultationSearchActivity.this,
				TeacherDetialActivity.class);
		intent.putExtra("teacher", teacher);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@OnClick(R.id.tv_select_teachertype)
	public void selectTeacherType() {
		AlertDialog.Builder builder = new Builder(this);
		final String[] teacherNames = { "专业导师", "人力导师" };
		builder.setItems(teacherNames, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				info.setTeachertype((arg1 + 1) + "");
				teacherTypeTextView.setText(teacherNames[arg1]);
				loadFirstData();
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
				info.setJobtype((arg1 + 1) + "");
				jobTypeTextView.setText(jobNames[arg1]);
				loadFirstData();
			}
		}).show();
	}

	@Override
	protected void loadData(final int page) {
		info.setPageindex(page + "");
		ConsultationEngine.getTeacher(info, new LoadResponseHandler<UserList>(
				this, UserList.class) {
			@Override
			public void onSuccess(UserList object) {
				saveData(page, object.getUserListItem());
			}

			@Override
			public void onFailure(Throwable e, int statusCode, String content) {
				super.onFailure(e, statusCode, content);
				if (page == 1 && statusCode == 400) {
					new Delete().from(UserListItem.class).execute();
				}
			}
		});
	}
}
