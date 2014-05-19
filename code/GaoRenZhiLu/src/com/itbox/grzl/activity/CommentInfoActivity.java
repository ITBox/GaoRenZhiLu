package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.CommentMarkAdapter;
import com.itbox.grzl.bean.CommentGet;
import com.itbox.grzl.bean.CommentMarkGet;
import com.itbox.grzl.engine.CommentEngine;
import com.itbox.grzl.engine.CommentEngine.CommentMarkItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 论坛列表界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentInfoActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.tv_name)
	protected TextView mNameTv;
	@InjectView(R.id.tv_content)
	protected TextView mContentTv;
	@InjectView(R.id.tv_count)
	protected TextView mCountTv;
	@InjectView(R.id.tv_title)
	protected TextView mCommentTitleTv;
	@InjectView(R.id.iv_head)
	protected ImageView mHeadIv;

	private int page = 1;
	private CommentMarkAdapter mAdapter;
	private CommentGet mBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_info);

		ButterKnife.inject(this);

		getSupportLoaderManager().initLoader(0, null, this);

		initView();
		initData();

	}

	private void initData() {
		mBean = getIntent().getParcelableExtra("bean");
		mContentTv.setText(mBean.getCommentcontent());
		mCountTv.setText("回复数：" + mBean.getReplacecount());
		mNameTv.setText(mBean.getUsername());
		mCommentTitleTv.setText(mBean.getTitle());
		ImageLoader.getInstance().displayImage(mBean.getPhoto(), mHeadIv);
		loadData();
	}

	private void initView() {
		mTitleTv.setText("论坛详情");
		mListView.setMode(Mode.PULL_FROM_END);
		mAdapter = new CommentMarkAdapter(getContext(), null);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 从网络加载数据
	 */
	private void loadData() {
		CommentEngine
				.getCommentMark(page, mBean.getCommentId(),
						new GsonResponseHandler<CommentMarkItem>(
								CommentMarkItem.class) {
							@Override
							public void onSuccess(CommentMarkItem bean) {
								// 保存到数据库
								saveData(page, bean);
							}

							@Override
							public void onFinish() {
								super.onFinish();
							}

						});
	}

	/**
	 * 保存数据
	 * 
	 * @param page
	 * @param bean
	 */
	private void saveData(int page, CommentMarkItem bean) {
		L.i(bean.getCommentItem().toString());
		if (bean != null) {
			List<CommentMarkGet> list = bean.getCommentItem();
			if (list != null) {
				try {
					ActiveAndroid.beginTransaction();
					if (page == 1) {
						try {
							// 清空数据库
							new Delete().from(CommentMarkGet.class).execute();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 保存
					for (CommentMarkGet er : list) {
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
				CommentMarkGet.class, null), null, null, null, null);
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
