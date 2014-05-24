package com.itbox.grzl.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.api.LoginAndRegisterApi;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.constants.AccountTable;

/**
 * 
 * @author youzh 2014年5月2日 下午5:43:55
 */
public class LoginActicity extends BaseActivity implements
		LoaderCallbacks<Cursor> {
	@InjectView(R.id.login_username)
	EditText usernameEditText;
	@InjectView(R.id.login_password)
	EditText passwordEditText;
	@InjectView(R.id.login_regist_pass_tv)
	TextView registerTextView;
	@InjectView(R.id.login_find_pass_tv)
	TextView forgetPasswordTextView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@OnClick(R.id.login_login_bt)
	public void userLogin() {
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		new LoginAndRegisterApi().login(username, password);
	}

	@OnClick(R.id.login_regist_pass_tv)
	public void userRegist() {
		new AlertDialog.Builder(this).setItems(new String[] { "手机注册", "邮箱注册" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							startActivity(RegistPhoneFirstActivity.class);
							break;
						case 1:
							startActivity(RegistEmailActivity.class);
							break;
						default:
							break;
						}
					}
				}).show();
	}

	@OnClick(R.id.login_find_pass_tv)
	public void userFindPass() {
		new AlertDialog.Builder(this).setItems(new String[] { "手机找回", "邮箱找回" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							startActivity(ResetPassPhoneActivity.class);
							break;
						case 1:
							startActivity(ResetPassEmailActivity.class);
							break;
						default:
							break;
						}
					}
				}).show();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new CursorLoader(this, ContentProvider.createUri(Account.class,
				null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null && cursor.moveToNext()) {
			Account account = new Account();
			account.loadFromCursor(cursor);
			AppContext.getInstance().setAccount(account);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			setContentView(R.layout.activity_login);
			ButterKnife.inject(this);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}
