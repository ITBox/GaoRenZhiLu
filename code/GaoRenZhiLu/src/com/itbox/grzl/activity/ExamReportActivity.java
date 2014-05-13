package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.activeandroid.util.SQLiteUtils;
import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.api.ConsultationApi;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_report);

		ButterKnife.inject(this);

		initView();
		 loadData(page);

//		new ConsultationApi().searchConsultation("1", "", "20", "1", "1", "1");
	}

	private void initView() {
		mTitleTv.setText("测评报告");

	}

	private void loadData(final int page) {
		ExamEngine
				.getExamReport(page, new GsonResponseHandler<UserTestingItem>(
						UserTestingItem.class) {
					@Override
					public void onSuccess(UserTestingItem bean) {
						// 保存到数据库
						saveData(page, bean);
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
		if (bean != null) {
			List<ExamReport> list = bean.getUserTestingItem();
			if (list != null) {
				try {
					ActiveAndroid.beginTransaction();
					if (page == 1) {
						// 清空数据库
						new Delete().from(ExamReport.class).execute();
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
				ExamReport.class, null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// load完毕
		L.i("..............................load完毕");
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}
}
