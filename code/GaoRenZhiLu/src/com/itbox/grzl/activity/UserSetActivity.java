package com.itbox.grzl.activity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.activeandroid.query.Delete;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.common.util.CheckUpdateVersion;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author youzh
 * 
 */
public class UserSetActivity extends BaseActivity {
	@InjectView(R.id.text_left)
	TextView mTVTopCancel;
	@InjectView(R.id.text_medium)
	TextView mTVTopMedium;
	@InjectView(R.id.userset_version_tv)
	TextView mTVVersion;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_user_set);
		ButterKnife.inject(mActThis);
		initViews();
	}

	private void initViews() {
		mTVTopCancel.setVisibility(View.VISIBLE);
		mTVTopMedium.setText("设置");
	}

	@OnClick({ R.id.text_left, R.id.userset_version, R.id.userset_help,
			R.id.userset_about, R.id.userset_logout })
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.text_left:
			UserSetActivity.this.finish();
			break;
		case R.id.userset_version:
//			dialog = new Dialog(mActThis, R.style.custom_dialog);
//			View inflate = View.inflate(mActThis, R.layout.dialog_updata_apk,
//					null);
//			dialog.setContentView(inflate);
//			dialog.setCancelable(false);
//			new UpdataView(inflate);
//			dialog.show();
			break;
		case R.id.userset_help:
			if (AppContext.getInstance().getAccount().isTeacher()) {
				startActivity(TeacherHelpActivity.class);
			} else {
				startActivity(UserSetHelpActivity.class);
			}
			break;
		case R.id.userset_about:
			startActivity(UserSetAboutActivity.class);
			break;
		case R.id.userset_logout:
			new Delete().from(Account.class).execute();
			startActivity(LoginActicity.class);
			mActThis.finish();
			break;

		default:
			break;
		}
	}

	class UpdataView {
		@InjectView(R.id.updata_content)
		TextView mTVUpdataConent;
		@InjectView(R.id.pb_download)
		ProgressBar mTProgressDown;
		@InjectView(R.id.download_ll)
		LinearLayout mLLDownload;

		public UpdataView(View view) {
			ButterKnife.inject(this, view);
		}

		@OnClick(R.id.download_updata)
		public void onClickUpdata() {
			mLLDownload.setVisibility(View.GONE);
			mTProgressDown.setVisibility(View.VISIBLE);
			File file = CheckUpdateVersion.download("",
					new File(Environment.getExternalStorageDirectory(),
							"/gaorenzhilu/download/gaorenzhilu.apk")
							.getAbsolutePath(), mTProgressDown);
			if (file != null) {
				CheckUpdateVersion.installApk(file);
			} else {
				dialog.dismiss();
				showToast("下载失败");
			}
		}

		@OnClick(R.id.download_cancel)
		public void onClickCancel() {
			dialog.dismiss();
			dialog = null;
		}
	}
}
