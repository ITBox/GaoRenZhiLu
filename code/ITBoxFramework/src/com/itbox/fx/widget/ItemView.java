package com.itbox.fx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ItemView extends View {

	public static final int CELL_TYPE_ARROW = 1;
	public static final int CELL_TYPE_BUTTON = 2;
	public static final int CELL_TYPE_EDIT_TEXT = 4;
	public static final int CELL_TYPE_EDIT_PWD = 5;
	public static final int CELL_TYPE_EDIT_NUM = 6;
	public static final int CELL_TYPE_EDIT_PHONE = 7;
	public static final int CELL_TYPE_SWITCH = 8;
	public static final int CELL_TYPE_SPINNER = 16;

	public static final int CELL_BG_TYPE_SINGLE = 0;
	public static final int CELL_BG_TYPE_TOP = 1;
	public static final int CELL_BG_TYPE_MIDDLE = 2;
	public static final int CELL_BG_TYPE_BOTTOM = 3;

	private String mLeftText;
	private String mRightText;
	private TextView mLeftTv;
	private TextView mRightTv;

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttr(attrs);
	}

	public ItemView(Context context) {
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
			contentView = inflater.inflate(layoutId, null);
		}catch (Exception e){ }
		

		
		setCellBgType(cellBgType);

	}

	/**
	 * 设置背景类型
	 * 
	 * @param type
	 */
	private void setCellBgType(int type) {
		setCellBgType(type, true);
	}
	/**
	 * 设置背景类型
	 * 
	 * @param type
	 */
	private void setCellBgType(int type,boolean clickable) {
		switch (type) {
			case CELL_BG_TYPE_SINGLE:{
				int back = clickable ? R.drawable.cell_single_selector : R.drawable.cell_bottom_bg_nor;
				setBackgroundResource(back);
				break;
			}
			case CELL_BG_TYPE_TOP:{
				int back = clickable ? R.drawable.cell_top_selector : R.drawable.cell_top_bg_nor;
				setBackgroundResource(back);
				break;
			}
			case CELL_BG_TYPE_MIDDLE:{
				int back = clickable ? R.drawable.cell_middle_selector : R.drawable.cell_middle_bg_nor;
				setBackgroundResource(back);
				break;
			}
			case CELL_BG_TYPE_BOTTOM:{
				int back = clickable ? R.drawable.cell_bottom_selector : R.drawable.cell_bottom_bg_nor;
				setBackgroundResource(back);
				break;
			}
		}
	}
	
	/**
	 * 设置类型
	 * 
	 * @param type
	 */
	private void inflate(int type) {
		
		switch (type) {
			case CELL_TYPE_ARROW:{
				
				break;
			}
			case CELL_TYPE_BUTTON:{
				
				break;
			}
			case CELL_TYPE_EDIT_TEXT:{
				
				break;
			}
			case CELL_TYPE_EDIT_PWD:{
				
				break;
			}
			case CELL_TYPE_EDIT_NUM:{
				
				break;
			}
			case CELL_TYPE_EDIT_PHONE:{
				
				break;
			}
			case CELL_TYPE_SWITCH:{
				
				break;
			}
			case CELL_TYPE_SPINNER:{
				
				break;
			}
	}
		
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


		this.setMinimumHeight(cellHeight);
	}

	public void setRightText(String text){
		mRightTv.setText(text);

	}
	
	public String getRightText() {
		return mRightTv.getText().toString();
	}

}
