package com.itbox.grzl.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.ImageUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.bean.AddUserAuthEntication;
import com.itbox.grzl.bean.UploadImageResult;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.common.util.FileUtils;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 身份证图片上传
 * 
 * @author youzh
 * 
 */
public class UserIDCardImgActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.idcard_img)
	ImageView mIVIDcard;
	private Uri uri;
	private String imgPath;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_idcard_img);
		ButterKnife.inject(mActThis);
		initViews();
		imgPath = getIntent().getStringExtra("imgPath");
		Log.d("youzh", "传过来的值： " + imgPath);
		Bitmap bitmap = FileUtils.getImageFromLocal(imgPath);
		mIVIDcard.setImageBitmap(bitmap);
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("身份认证");
		mTVTopMedium.setText("上传图片");
	}

	@OnClick({ R.id.text_left, R.id.idcardImg_cancel, R.id.idcardImg_upload })
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			finish();
			break;
		case R.id.idcardImg_cancel:
			finish();
			break;
		case R.id.idcardImg_upload:
			try {
				showProgressDialog("上传中...");
				RequestParams params = new RequestParams();
				params.put("图片流", new FileInputStream(imgPath));
				params.put("id", AppContext.getInstance().getAccount()
						.getUserid().toString());
				params.put("imagetype", "5");
				Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE),
						new GsonResponseHandler<UploadImageResult>(
								UploadImageResult.class) {
							@Override
							public void onFinish() {
								dismissProgressDialog();
								super.onFinish();
							}

							@Override
							public void onSuccess(UploadImageResult result) {
								super.onSuccess(result);
								if (result != null
										&& result.getReturnUrl() != null) {
									Intent data = new Intent();
									data.putExtra("idcardPath",
											result.getReturnUrl());
									setResult(RESULT_OK, data);
									UserIDCardImgActivity.this.finish();
								} else {
									dismissProgressDialog();
									showToast("身份证上传失败");
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
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

}
