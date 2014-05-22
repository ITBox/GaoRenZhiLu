package com.itbox.grzl.activity;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshListView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

import com.activeandroid.query.Delete;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.adapter.CommentMarkAdapter;
import com.itbox.grzl.bean.CommentGet;
import com.itbox.grzl.bean.CommentMarkAdd;
import com.itbox.grzl.bean.CommentMarkGet;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.CommentEngine;
import com.itbox.grzl.engine.CommentEngine.CommentMarkItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 论坛列表界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentInfoActivity extends BaseLoadActivity<CommentMarkGet> {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.lv_list)
	protected PullToRefreshListView mListView;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	public static class HeaderView {
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
	}

	private CommentMarkAdapter mAdapter;
	private CommentGet mBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_info);
		mBean = getIntent().getParcelableExtra("bean");
		if (mBean == null) {
			finish();
			return;
		}

		ButterKnife.inject(this);

		// 清空评论数据库，防止显示别的评论
		new Delete().from(CommentMarkGet.class).execute();

		initView();
	}

	private void initView() {
		// 添加头
		View header = View.inflate(getApplicationContext(),
				R.layout.item_top_comment_info, null);
		mListView.getRefreshableView().addHeaderView(header);

		HeaderView hv = new HeaderView();
		ButterKnife.inject(hv, header);
		hv.mContentTv.setText(mBean.getCommentcontent());
		hv.mCountTv.setText("回复数：" + mBean.getReplacecount());
		hv.mNameTv.setText(mBean.getUsername());
		hv.mCommentTitleTv.setText(mBean.getTitle());
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(mBean.getPhoto()), hv.mHeadIv);

		mTitleTv.setText("论坛详情");

		mAdapter = new CommentMarkAdapter(getContext(), null);
		initLoad(mListView, mAdapter, CommentMarkGet.class, null, null);
		mListView.setMode(Mode.PULL_FROM_END);
	}

	@OnClick(R.id.bt_mark)
	public void onClick(View v) {
		// 评论
		String content = mContentEt.getText().toString();
		if (TextUtils.isEmpty(content)) {
			showToast("请输入内容");
			return;
		}
		CommentMarkAdd bean = new CommentMarkAdd();
		bean.setCommentcontent(content);
		bean.setId(mBean.getCommentId() + "");
		bean.setUserid(AppContext.getInstance().getAccount().getUserid()
				.toString());
		showProgressDialog("正在提交...");
		CommentEngine.addCommentMark(bean, new GsonResponseHandler<RespResult>(
				RespResult.class) {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onSuccess(RespResult result) {
				super.onSuccess(result);
				if (result.isSuccess()) {
					showToast("评论成功");
					mContentEt.setText("");
					loadFirstData();
				} else {
					showToast("评论失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showToast(content);
			}
		});
	}

	/**
	 * 从网络加载数据
	 */
	protected void loadData(final int page) {
		CommentEngine
				.getCommentMark(page, mBean.getCommentId(),
						new GsonResponseHandler<CommentMarkItem>(
								CommentMarkItem.class) {
							@Override
							public void onSuccess(CommentMarkItem bean) {
								// 保存到数据库
								saveData(page, bean.getCommentItem());
							}

							@Override
							public void onFinish() {
								super.onFinish();
								loadFinish();
							}

						});
	}

}
