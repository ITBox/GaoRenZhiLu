package com.itbox.grzl.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.grzl.Api;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.AddUserAuthEntication;
import com.itbox.grzl.common.Contasts;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
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
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_idcard);
		ButterKnife.inject(mActThis);
		initViews();
	}
	
	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopCancel.setText("个人资料");
		mTVTopMedium.setText("更多资料");
	}
	
	private void postIDCard() {
		RequestParams params = new RequestParams();
		params.put("userid", AppContext.getInstance().getAccount().getUserid()+"");
		params.put("usercode", "");
		params.put("userrealname", "");
		params.put("userbirthday", "");
		params.put("codephoto", "");
		Net.request(params, Api.getUrl(Api.User.ADD_USER_IDCARD), new GsonResponseHandler<AddUserAuthEntication>(AddUserAuthEntication.class) {
			@Override
			public void onSuccess(AddUserAuthEntication object) {
				super.onSuccess(object);
				switch (object.getResult()) {
				case Contasts.RESULT_SUCCES:
					
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
