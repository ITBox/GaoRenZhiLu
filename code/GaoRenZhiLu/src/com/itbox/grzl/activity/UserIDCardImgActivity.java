package com.itbox.grzl.activity;

import java.io.FileNotFoundException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.ImageUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.AddUserAuthEntication;
import com.itbox.grzl.bean.UploadImageResult;
import com.itbox.grzl.common.Contasts;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
	@InjectView(R.id.idcard_name_et)
	EditText mIDCardName;
	@InjectView(R.id.idcard_birthday_et)
	EditText mIDCardBirthday;
	@InjectView(R.id.idcard_num_et)
	EditText mIDCardNum;
	@InjectView(R.id.idcard_img)
	ImageView mIVIDcard;
	private Uri uri;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_idcard_img);
		ButterKnife.inject(mActThis);
		initViews();
		uri = getIntent().getData();
		Bitmap uriBitmap = ImageUtils.getUriBitmap(mActThis, uri);
		mIVIDcard.setImageBitmap(uriBitmap);
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
				params.put("图片流", mActThis.getContentResolver().openInputStream(uri));
				params.put("id", AppContext.getInstance().getAccount().getUserid().toString());
				params.put("imagetype", "");
				Net.request(params, Api.getUrl(Api.User.UPLOAD_IMAGE), new GsonResponseHandler<UploadImageResult>(UploadImageResult.class) {
					@Override
					public void onSuccess(UploadImageResult result) {
						super.onSuccess(result);
						if (result != null && result.getReturnUrl() != null) {
							Intent data = new Intent();
							data.putExtra("idcardPath", result.getReturnUrl());
                            setResult(Contasts.UPLOAD_IDCARD, data);
                            UserIDCardImgActivity.this.finish();
						} else {
							dismissProgressDialog();
							showToast("身份证上传失败");
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
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
