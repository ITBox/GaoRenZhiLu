package com.itbox.grzl.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.itbox.fx.util.DialogUtil;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.R;

public class BaseFragment extends Fragment implements OnClickListener {
	protected Fragment mActThis;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActThis = this;
	}

	@Override
	public void onClick(View v) {
	}

	/**
	 * 显示进度条对话框
	 * @param msg
	 */
	protected void showProgressDialog(String msg) {
		DialogUtil.showProgressDialog(getActivity(), msg);
	}

	/**
	 * 显示正在加载对话框
	 */
	protected void showLoadProgressDialog() {
		DialogUtil.showProgressDialog(getActivity(),
				getString(R.string.loading));
	}

	/**
	 * 隐藏进度条对话框
	 */
	protected void dismissProgressDialog() {
		DialogUtil.dismissProgressDialog();
	}
	protected void showToast(String msg){
		ToastUtils.showToast(getActivity(), msg);
	}
	protected void showToast(int stringResID){
		ToastUtils.showToast(getActivity(), stringResID);
	}
	
	protected void startActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(getActivity(), activity);
		startActivity(intent);
	}
	
	protected void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
		Intent intent = new Intent(getActivity(), activity);
		startActivityForResult(intent, requestCode);
	}
}
