package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.ExamReportAdapter;
import com.itbox.grzl.bean.ExamReport;
import com.itbox.grzl.engine.ExamEngine;
import com.itbox.grzl.engine.ExamEngine.UserTestingItem;

/**
 * 测评报告界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class ExamReportActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;

	private int page = 1;
	private ExamReportAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_report);

		ButterKnife.inject(this);

		initView();
		getSupportLoaderManager().initLoader(0, null, this);

		loadFirstData();
	}

	private void loadFirstData() {
		page = 1;
		loadData();
	}

	private void loadNextData() {
		page++;
		loadData();
	}

	private void initView() {
		mTitleTv.setText("测评报告");
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadFirstData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadNextData();
			}

		});
		mListView.setMode(Mode.BOTH);

		mAdapter = new ExamReportAdapter(getContext(), null);
		mListView.setAdapter(mAdapter);
	}

	private void loadData() {
		ExamEngine
				.getExamReport(page, new GsonResponseHandler<UserTestingItem>(
						UserTestingItem.class) {
					@Override
					public void onSuccess(UserTestingItem bean) {
						// 保存到数据库
						saveData(page, bean);
					}

					@Override
					public void onFinish() {
						super.onFinish();
						mListView.onRefreshComplete();
					}

				});
	}

	/**
	 * 保存数据
	 * 
	 * @param page
	 * @param bean
	 */
	private void saveData(int page, UserTestingItem bean) {
		L.i(bean.getUserTestingItem().toString());
		if (bean != null) {
			List<ExamReport> list = bean.getUserTestingItem();
			if (list != null) {
				try {
					ActiveAndroid.beginTransaction();
					if (page == 1) {
						try {
							// 清空数据库
							new Delete().from(ExamReport.class).execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 保存
					for (ExamReport er : list) {
						er.save();
					}
					ActiveAndroid.setTransactionSuccessful();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ActiveAndroid.endTransaction();
				}
			}
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				ExamReport.class, null), null, null, null,
				ExamReport.CREATETIME + " desc");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loder, Cursor cursor) {
		// load完毕
		if (cursor != null) {
			mAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}
}
