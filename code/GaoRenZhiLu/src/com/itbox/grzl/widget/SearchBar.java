package com.itbox.grzl.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import com.zhaoliewang.grzl.R;

/**
 * 搜索条
 * 
 * @author baoboy
 * @date 2014-5-24下午10:36:59
 */
public class SearchBar extends FrameLayout {

	@InjectView(R.id.et_input)
	protected EditText mInputEt;
	@InjectView(R.id.tv_search)
	protected TextView mSearchTv;

	private boolean isInput;
	private OnSearchListener onSearchListener;
	private OnClickListener clickListener;

	public SearchBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SearchBar(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		View.inflate(getContext(), R.layout.view_search_bar, this);

		ButterKnife.inject(this);

		mInputEt.setFocusable(false);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		clickListener = l;
	}
	
	@OnClick(R.id.et_input)
	public void onEditClick(View v){
		if (clickListener != null) {
			performClick();
			return;
		}
	}

	@OnClick(R.id.tv_search)
	public void onClick(View v) {
		if (clickListener != null) {
			performClick();
			return;
		}
		if (isInput) {
			// 开始搜索
			if (onSearchListener != null) {
				onSearchListener.onSearch(mInputEt.getText().toString());
			}
		} else {
			// 进入搜索模式
			mInputEt.setFocusable(true);
			mInputEt.setFocusableInTouchMode(true);
			mInputEt.requestFocus();
			showKeyboard(mInputEt);

			// 搜索图片放到右侧
			LayoutParams params = (LayoutParams) mSearchTv.getLayoutParams();
			params.gravity = Gravity.RIGHT;
			mSearchTv.setLayoutParams(params);
			isInput = true;
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public void hiddenKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive(view)) {
			// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
			// view.clearFocus();
		}
	}

	/**
	 * 显示软键盘
	 */
	public void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.HIDE_NOT_ALWAYS);
		// imm.restartInput(view);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void setOnSearchListener(OnSearchListener onSearchListener) {
		this.onSearchListener = onSearchListener;
	}

	public static interface OnSearchListener {
		public void onSearch(String keyword);
	}

}
