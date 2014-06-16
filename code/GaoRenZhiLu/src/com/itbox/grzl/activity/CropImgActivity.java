package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.ImageUtils;
import com.itbox.grzl.common.util.FileUtils;
import com.itbox.grzl.cropper.CropImageView;
import com.zhaoliewang.grzl.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CropImgActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.text_right)
	TextView mTVTopSave;
	@InjectView(R.id.cropimageview)
	CropImageView cropIV;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_crop_img);
		ButterKnife.inject(mActThis);
		initViews();
		Uri uri = getIntent().getData();
//		Uri uri = (Uri) getIntent().getSerializableExtra("imgUri");
//		Uri uri = getIntent().getParcelableExtra("imgUri");
//		initData(uri);
		Bitmap bitmap = ImageUtils.getUriBitmap(mActThis, uri, 400, 400);
		cropIV.setImageBitmap(bitmap);
	}
	
	private void initData(Uri uri) {
		// TODO Auto-generated method stub
		Bitmap bitmap = ImageUtils.getBitmap(mActThis, "", null, uri, 400, 400);
		cropIV.setImageBitmap(bitmap);
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopSave.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("截取图片");
		mTVTopSave.setText("截取");
	}
	
	@OnClick({R.id.text_left, R.id.text_right})
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_left:
			mActThis.finish();
			break;
		case R.id.text_right:
			Bitmap bitmap = cropIV.getCroppedImage();
			String saveBitToSD = FileUtils.saveBitToSD(bitmap, System.currentTimeMillis()+"");
			Intent intent = new Intent();
			intent.putExtra("cropPath", saveBitToSD);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}
}
