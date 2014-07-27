package com.itbox.grzl.activity;

import java.io.FileNotFoundException;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.CommentAdd;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.bean.UploadImageResult;
import com.itbox.grzl.engine.CommentEngine;
import com.loopj.android.http.RequestParams;

/**
 * 添加论坛界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentAddActivity extends BaseActivity {

	private static final int REQUEST_PICTURE = 1;

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.et_title)
	protected EditText mTitleEt;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;
	@InjectView(R.id.iv_photo)
	protected ImageView mPhotoIv;

	private Uri mPhotoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_add);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("发布论坛");
		showLeftBackButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			mPhotoUri = data.getData();

			try {
				mPhotoIv.setVisibility(View.VISIBLE);
				mPhotoIv.setImageBitmap(BitmapFactory
						.decodeStream(getContentResolver().openInputStream(
								mPhotoUri)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@OnClick({ R.id.bt_add, R.id.tv_photo })
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_photo:
			// 选择图片
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, REQUEST_PICTURE);
			break;

		case R.id.bt_add:
			if (!checkParams()) {
				return;
			}
			// 发布
			showProgressDialog("正在发布...");
			// 上传图片
			try {
				RequestParams params = new RequestParams();
				params.put("图片流", AppContext.getInstance().getContentResolver()
						.openInputStream(mPhotoUri));
				params.put("id", AppContext.getInstance().getAccount()
						.getUserid().toString());
				params.put("imagetype", "3"); // 论坛图片
				Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE),
						new GsonResponseHandler<UploadImageResult>(
								UploadImageResult.class) {
							@Override
							public void onSuccess(UploadImageResult result) {
								super.onSuccess(result);
								if (result != null
										&& result.getReturnUrl() != null) {
									pushComment(result.getReturnUrl());
								} else {
									dismissProgressDialog();
									showToast("海报上传失败");
								}
							}

							@Override
							public void onFailure(Throwable error,
									String content) {
								super.onFailure(error, content);
								dismissProgressDialog();
								showToast(content);
							}
						});
			} catch (Exception e) {
				dismissProgressDialog();
				showToast("出错");
			}
			break;
		}
	}

	private boolean checkParams() {
		if (mPhotoUri == null) {
			showToast("请选择海报");
			return false;
		}
		if (TextUtils.isEmpty(mTitleEt.getText().toString())) {
			showToast("请输入标题");
			return false;
		}
		if (TextUtils.isEmpty(mContentEt.getText().toString())) {
			showToast("请输入描述");
			return false;
		}
		return true;
	}

	private void pushComment(String photo) {
		// 发布论坛
		CommentAdd bean = new CommentAdd();
		String title = mTitleEt.getText().toString();
		String content = mContentEt.getText().toString();
		bean.setCommentcontent(content);
		bean.setTitle(title);
		bean.setPhoto(photo);
		bean.setUserid(AppContext.getInstance().getAccount().getUserid()
				.toString());
		CommentEngine.addComment(bean, new GsonResponseHandler<RespResult>(
				RespResult.class) {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onSuccess(RespResult result) {
				if (result.isSuccess()) {
					showToast("发布成功");
					finish();
				} else {
					showToast("发布失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showToast(content);
			}
		});
	}
}
