package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.baoyz.pg.PG;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.UserProblemAdapter;
import com.itbox.grzl.bean.UserProblem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.ConsultationEngine.UserProblemItem;
import com.zhaoliewang.grzl.R;

/**
 * 免费咨询页面
 * 
 * @author baoboy
 * @date 2014-5-24下午11:22:30
 */
public class ConsultationFreeActivity extends BaseLoadActivity<UserProblem> {

	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private CursorAdapter mAdapter;

	private String jobType = "";

	private String orderBy = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultation_free);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		setTitle("免费咨询");
		showLeftBackButton();
		mAdapter = new UserProblemAdapter(this, null);
		
		new Delete().from(UserProblem.class).execute();
		
		initLoad(mListView, mAdapter, UserProblem.class);
	}

	@OnClick({ R.id.tv_type, R.id.tv_order })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_order:
			// 选择排序
			new AlertDialog.Builder(this).setItems(
					new String[] { "时间正序", "时间倒序" },
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							orderBy = which + "";
							startSearch();
						}
					}).show();
			break;
		case R.id.tv_type:
			// 选择类型
			AlertDialog.Builder builder = new Builder(this);
			final String[] jobNames = AppContext.getJobNameArray();
			builder.setItems(jobNames, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					jobType = (arg1 + 1) + "";
					startSearch();
				}
			}).show();
			break;
		}
	}

	private void startSearch() {
		showProgressDialog("正在搜索...");
		loadFirstData();
	}

	@Override
	protected void loadData(final int page) {
		ConsultationEngine.searchFree(orderBy, jobType, page,
				new LoadResponseHandler<UserProblemItem>(this,
						UserProblemItem.class) {
					@Override
					public void onSuccess(UserProblemItem object) {
						saveData(page, object.getUserProblemItem());
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		UserProblem bean = new UserProblem();
		bean.loadFromCursor((Cursor) mAdapter.getItem(position - 1));
		if (UserProblem.TYPE_FREE.equals(bean.getConsultationType())) {
			// 免费咨询
			Intent intent = new Intent(this,
					ConsultationFreeDetailActivity.class);
			intent.putExtra("bean", PG.convertParcelable(bean));
			startActivity(intent);
		} else if (UserProblem.TYPE_PHOTO.equals(bean.getConsultationType())) {
			// 图文咨询
			Intent intent = new Intent(this,
					ConsultationPhotoDetailActivity.class);
			intent.putExtra("bean", PG.convertParcelable(bean));
			startActivity(intent);
		}
	}
}
