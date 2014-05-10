package com.itbox.grzl.activity;

import java.util.List;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.ToastUtils;
import com.itbox.fx.widget.CellView;
import com.itbox.grzl.Api;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.Login;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
/**
 * 
 * @author youzh
 * 2014年5月2日 下午5:43:55
 */
public class LoginActicity extends BaseActivity {
	private long exitTime;
	@InjectView(R.id.login_username) CellView mETLoginUserName;
	@InjectView(R.id.login_password) CellView mETLoginPassword;
    @InjectView(R.id.login_regist_pass_tv) TextView mTVLoginRegist;
    @InjectView(R.id.login_find_pass_tv) TextView mTVLoginFind;
//    @InjectView(R.id.login_login_bt) Button mBTLoginLogin;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		ButterKnife.inject(mActThis);
	}
	
	@OnClick(R.id.login_login_bt)
	public void userLogin() {
		String userName = mETLoginUserName.getRightText();
		String password = mETLoginPassword.getRightText();
		if (TextUtils.isEmpty(userName)){
			ToastUtils.showToast(mActThis, "帐号为空");
			return;
		}
		if (TextUtils.isEmpty(password)){
			ToastUtils.showToast(mActThis, "密码为空");
			return;
		}
		RequestParams params = new RequestParams();
		params.put("account", userName);
		params.put("userpassword", password);
		
		showProgressDialog("登录中...");
		Net.request(params, Api.getUrl(Api.User.Login), new GsonResponseHandler<Login>(Login.class) {

			@Override
			public void onSuccess(List<Login> list) {
				// TODO Auto-generated method stub
				super.onSuccess(list);
			}
			
			@Override
			public void onSuccess(Login object) {
				// TODO Auto-generated method stub
				super.onSuccess(object);
				SharedPreferences sp = AppContext.getUserPreferences();
				sp.edit().putInt("userid", object.getUserid()).commit();
				startActivity(MainActivity.class);
				mActThis.finish();
			}
			
			@Override
			public void onFailure(Throwable e, int statusCode, String content) {
				super.onFailure(e, statusCode, content);
				switch (statusCode) {

				default:
					break;
				}
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}
		});
	}
	
	@OnClick(R.id.login_regist_pass_tv)
	public void userRegist() {
		new AlertDialog.Builder(this).setItems(new String[]{"手机注册", "邮箱注册"} , new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					startActivity(new Intent(mActThis, RegistPhoneFirstActivity.class));
					break;
				case 1:
					startActivity(new Intent(mActThis, RegistEmailActivity.class));
					break;
				default:
					break;
				}
			}
		}).show();
	}
    
	@OnClick(R.id.login_find_pass_tv)
	public void userFindPass() {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				ToastUtils.showToast(mActThis, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
   
}
