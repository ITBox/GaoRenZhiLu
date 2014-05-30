package com.itbox.grzl.activity;

import android.content.Intent;
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
import com.itbox.grzl.adapter.TeacherCommentAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.TeacherComment;
import com.itbox.grzl.constants.TeacherCommentTable;

public class PictureConsultationDetialActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	ListView mListView;
	@InjectView(R.id.text_left)
	TextView backTextView;
	private View mHeaderView;

	private ConsultationApi consultationApi;
	private TeacherCommentAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_picture_consultation_detial);
		ButterKnife.inject(this);
		mHeaderView = View.inflate(this, R.layout.layout_comment_list_header,
				null);
		mListView.addHeaderView(mHeaderView);
		adapter = new TeacherCommentAdapter(this, null);
		mListView.setAdapter(adapter);
		consultationApi = new ConsultationApi();
		consultationApi.getTeacherComment("14");
		backTextView.setVisibility(View.VISIBLE);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@OnClick(R.id.btn_buy)
	public void buy() {
		Intent intent = new Intent(this, PayActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.text_left)
	public void back() {
		finish();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				TeacherComment.class, null), null,
				TeacherCommentTable.COLUMN_USERID + "=?",
				new String[] { "14" }, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor != null) {
			adapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

}
