package com.itbox.fx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itbox.fx.R;


/**
 * 
 * 
 * @author baoyz
 * 
 *         2014-5-1 下午7:02:16
 * 
 */
public class CellView extends LinearLayout {

	public static final int CELL_TYPE_BUTTON_ARROW = 0;
	public static final int CELL_TYPE_BUTTON_TEXT = 1;
	public static final int CELL_TYPE_EDIT_TEXT = 2;
	public static final int CELL_TYPE_SWITCH_BUTTON = 3;
	public static final int CELL_TYPE_SPINNER = 4;

	public static final int CELL_BG_TYPE_SINGLE = 0;
	public static final int CELL_BG_TYPE_TOP = 1;
	public static final int CELL_BG_TYPE_MIDDLE = 2;
	public static final int CELL_BG_TYPE_BOTTOM = 3;

	private String mLeftText;
	private String mRightText;
	private TextView mLeftTv;
	private TextView mRightTv;

	public CellView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttr(attrs);
	}

	public CellView(Context context) {
		super(context);
	}

	/**
	 * 初始化属性
	 * 
	 * @param attrs
	 */
	private void initAttr(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CellView);

		mLeftText = a.getString(R.styleable.CellView_leftText);
		mRightText = a.getString(R.styleable.CellView_rightText);
		int layoutId = a.getResourceId(R.styleable.CellView_layout, 0);
		int cellType = a.getInt(R.styleable.CellView_cellType, 0);
		int cellBgType = a.getInt(R.styleable.CellView_cellBgType, 0);
		a.recycle();
		
		View contentView = null;
		try{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contentView = inflater.inflate(layoutId, this);
		}catch (Exception e){ }
		
		if(contentView == null){
			setCellType(cellType);
		}else{
			mLeftTv = (TextView) contentView.findViewById(R.id.cellview_left);
			mRightTv = (TextView) contentView.findViewById(R.id.cellview_right);
		}
		
		setCellBgType(cellBgType);

		setGravity(Gravity.CENTER_VERTICAL);
	}

	/**
	 * 设置背景类型
	 * 
	 * @param type
	 */
	private void setCellBgType(int type) {
		switch (type) {
		case CELL_BG_TYPE_SINGLE:
			setBackgroundResource(R.drawable.cell_single_selector);
			break;
		case CELL_BG_TYPE_TOP:
			setBackgroundResource(R.drawable.cell_top_selector);
			break;
		case CELL_BG_TYPE_MIDDLE:
			setBackgroundResource(R.drawable.cell_middle_selector);
			break;
		case CELL_BG_TYPE_BOTTOM:
			setBackgroundResource(R.drawable.cell_bottom_selector);
			break;
		}
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 */
	private void setCellType(int type) {
		int cellHeight = (int) getContext().getResources().getDimension(R.dimen.cell_height);
		int cellLeftWidth = (int) getContext().getResources().getDimension(R.dimen.cell_left_width);

		mLeftTv = new TextView(getContext());
		mRightTv = null;
		if (type == CELL_TYPE_EDIT_TEXT) {
			mRightTv = new EditText(getContext());
			mRightTv.setBackgroundResource(android.R.color.transparent);
			mRightTv.setPadding(mRightTv.getPaddingLeft(), 0, mRightTv.getPaddingRight(), 0);
		} else {
			mRightTv = new TextView(getContext());
		}

		mLeftTv.setText(mLeftText);
		mRightTv.setText(mRightText);

		LayoutParams paramsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams paramsRight = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// params.weight = 1;
		mLeftTv.setLayoutParams(paramsLeft);
		// mLeftTv.setMinWidth(cellLeftWidth);

		mRightTv.setLayoutParams(paramsRight);

		switch (type) {
		case CELL_TYPE_BUTTON_ARROW:
			mRightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
			break;
		}
		this.addView(mLeftTv);
		this.addView(mRightTv);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setMinimumHeight(cellHeight);
	}

	public void setRightText(String text){
		mRightTv.setText(text);

	}
	
	public String getRightText() {
		return mRightTv.getText().toString();
	}
}
