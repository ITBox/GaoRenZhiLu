package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.util.EditTextUtils;
import com.itbox.fx.util.ImageUtils;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.AddUserAuthEntication;
import com.itbox.grzl.common.Contasts;
import com.itbox.grzl.common.util.FileUtils;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 身份证
 * @author youzh
 *
 */
public class UserIDCardActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.idcard_img)
	TextView mIDCardUpload;
	@InjectView(R.id.idcard_save)
	TextView mIDCardSave;
	@InjectView(R.id.idcard_name_et)
	EditText mIDCardName;
	@InjectView(R.id.idcard_birthday_et)
	EditText mIDCardBirthday;
	@InjectView(R.id.idcard_num_et)
	EditText mIDCardNum;
	private String idcardPath;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_idcard);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("更多资料");
		mTVTopMedium.setText("身份认证");
	}
	
	@OnClick({R.id.idcard_img, R.id.idcard_save})
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.idcard_img:
			Intent intent = new Intent("android.intent.action.PICK");
			intent.setType("image/*");
			mActThis.startActivityForResult(intent, Contasts.TAKE_PICTURE_FROM_GALLERY);
			break;
		case R.id.idcard_save:
			String name = EditTextUtils.getText(mIDCardName);
			String birthday = EditTextUtils.getText(mIDCardBirthday);
			String num = EditTextUtils.getText(mIDCardNum);
			postIDCard(num, name, birthday, idcardPath);
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Contasts.TAKE_PICTURE_FROM_GALLERY:
				Intent intent = new Intent(mActThis, UserIDCardImgActivity.class);
//				intent.setDataAndType(data.getData(), "image/jpeg");
				Bitmap uriBitmap = ImageUtils.getUriBitmap(mActThis, data.getData());
				String saveBitToSD = FileUtils.saveBitToSD(uriBitmap, System.currentTimeMillis()+"");
				intent.putExtra("imgPath", saveBitToSD);
				Log.i("youzh", saveBitToSD);
				mActThis.startActivityForResult(intent, Contasts.UPLOAD_IDCARD);
				break;
			case Contasts.UPLOAD_IDCARD:
				idcardPath = data.getStringExtra("idcardPath");
				mIDCardUpload.setVisibility(View.GONE);
				mIDCardSave.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void postIDCard(String usercode, String userrealname, String userbirthday, String codephoto) {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()+"");
		params.put("usercode", usercode);
		params.put("userrealname", userrealname);
		params.put("userbirthday", userbirthday);
		params.put("codephoto", codephoto);
		Net.request(params, Api.getUrl(Api.User.ADD_USER_IDCARD), new GsonResponseHandler<AddUserAuthEntication>(AddUserAuthEntication.class) {
			@Override
			public void onSuccess(AddUserAuthEntication object) {
				super.onSuccess(object);
				switch (object.getResult()) {
				case Contasts.RESULT_SUCCES:
					UserIDCardActivity.this.finish();
					break;
				case Contasts.RESULT_FAIL:
					
					break;

				default:
					break;
				}
			}
		});
	}
}
