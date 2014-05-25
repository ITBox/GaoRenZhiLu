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
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;

/**
 * Load页面基类
 * 
 * @author byz
 * @date 2014-5-18下午6:42:12
 * @param <T>
 */
public abstract class BaseLoadActivity<T extends Model> extends BaseActivity
		implements LoaderCallbacks<Cursor>, OnItemClickListener {

	protected PullToRefreshListView mRefreshListView;
	protected ListView mListView;

	private int page = 1;
	private int oldPage = 1;
	private CursorAdapter mAdapter;
	private Class<T> mClazz;
	private String mOrderBy;
	private String mSelection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化加载器
	 * 
	 * @param refreshListView
	 * @param adapter
	 * @param clazz
	 * @param orderBy
	 */
	public void initLoad(ListView listView, PullToRefreshListView refreshListView, CursorAdapter adapter,
			Class<T> clazz, String selection, String orderBy) {
		mRefreshListView = refreshListView;
		mListView = listView;
		mAdapter = adapter;
		mClazz = clazz;
		mSelection = selection;
		mOrderBy = orderBy;
		initView();
		getSupportLoaderManager().initLoader(0, null, this);
		loadFirstData();
	}

	/**
	 * 初始化加载器
	 * 
	 * @param listView
	 * @param adapter
	 * @param clazz
	 */
	public void initLoad(PullToRefreshListView listView, CursorAdapter adapter,
			Class<T> clazz) {
		initLoad(null, listView, adapter, clazz, null, null);
	}
	
	/**
	 * 初始化加载器
	 * 
	 * @param listView
	 * @param adapter
	 * @param clazz
	 */
	public void initLoad(ListView listView, CursorAdapter adapter,
			Class<T> clazz) {
		initLoad(listView, null, adapter, clazz, null, null);
	}

	/**
	 * 加载第一页
	 */
	protected void loadFirstData() {
		oldPage = page;
		page = 1;
		loadData(page);
	}

	/**
	 * 加载下一页
	 */
	protected void loadNextData() {
		oldPage = page;
		page++;
		loadData(page);
	}

	/**
	 * 还原页码
	 */
	protected void restorePage() {
		page = oldPage;
	}

	private void initView() {
		// 设置刷新监听器
		if (mRefreshListView != null) {
			mRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
				
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
			mRefreshListView.setMode(Mode.BOTH);
			mRefreshListView.setAdapter(mAdapter);
			mRefreshListView.setOnItemClickListener(this);
		}
		if (mListView != null) {
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(this);
		}
	}

	/**
	 * 加载数据
	 */
	protected abstract void loadData(int page);

	/**
	 * 数据加载完毕
	 */
	protected void loadFinish() {
		if (mRefreshListView != null) {
			mRefreshListView.onRefreshComplete();
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param page
	 * @param bean
	 */
	protected void saveData(int page, List<T> list) {
		try {
			ActiveAndroid.beginTransaction();
			if (page == 1) {
				try {
					// 清空数据库
					new Delete().from(mClazz).where(mSelection).execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 保存
			if (list != null) {
				for (T er : list) {
					er.save();
				}
			}
			ActiveAndroid.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(mClazz, null),
				null, mSelection, null, mOrderBy);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}
}
