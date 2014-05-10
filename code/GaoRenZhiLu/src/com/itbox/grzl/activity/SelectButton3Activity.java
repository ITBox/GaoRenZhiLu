package com.itbox.grzl.activity;

import com.itbox.fx.core.AppException;
import com.itbox.grzl.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * 三个选择按键
 * @author hyh creat_at：2013-8-6-下午4:10:05
 */
public class SelectButton3Activity extends SelectAbstractActivity implements OnClickListener {
	
	public static class Extra{
		public static final String SelectedID = "selected_id";
		public static final String SelectedItem = "selected_item";
		public static final String SelectedItemStr = "selected_item_string";
		public static final String Button0_Text = "button_0_text";
		public static final String Button1_Text = "button_1_text";
		public static final String ButtonCancel_Text = "button_cancle_text";
		/**按键选择界面返回值*/
		public static final int Button_0 = 0,
								Button_1 = 1,
								Selected_enter = 2,
								Selected_cancle = -1;
	}
	private View chooseLl;
	private Button firstBtn;
	private Button secondBtn;
	private Button cancelBtn;
	private Intent intent;
	private String btn0Text;
	private String btn1Text;
	private String cancelText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_button3);
		initView();		
		show();
	}

	private void initView() {
		firstBtn = (Button) findViewById(R.id.select_btn_0);
		secondBtn = (Button) findViewById(R.id.select_btn_1);
		cancelBtn = (Button) findViewById(R.id.select_btn_cancle);
		chooseLl = findViewById(R.id.select_ll_button);
		findViewById(R.id.select_background);
		
		setSelectView(chooseLl);
//		setBackgroundView(backgroundLl);
		firstBtn.setOnClickListener(this);
		secondBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);

		intent = getIntent();
		btn0Text = intent.getStringExtra(Extra.Button0_Text);
		btn1Text = intent.getStringExtra(Extra.Button1_Text);
		if(TextUtils.isEmpty(btn0Text)||TextUtils.isEmpty(btn1Text)){
			AppException.handle(new NullPointerException("未设置按键文字"));
			return;
		}
		firstBtn.setText(btn0Text);
		secondBtn.setText(btn1Text);
		
		cancelText = intent.getStringExtra(Extra.ButtonCancel_Text);
		if(!TextUtils.isEmpty(cancelText)){
			cancelBtn.setText(cancelText);
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dismiss();
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.select_btn_0:
			Intent intent0 = new Intent();
			intent0.putExtra(Extra.SelectedItem, Extra.Button_0);
			intent0.putExtra(Extra.SelectedItemStr, btn0Text);
			setResult(RESULT_OK, intent0);
			dismiss();
			break;
		case R.id.select_btn_1:
			Intent intent1 = new Intent();
			intent1.putExtra(Extra.SelectedItem, Extra.Button_1);
			intent1.putExtra(Extra.SelectedItemStr, btn1Text);
			setResult(RESULT_OK, intent1);
			dismiss();
			break;
		case R.id.select_btn_cancle:
			Intent intent = new Intent();
			intent.putExtra(Extra.SelectedItem, Extra.Selected_cancle);
			intent.putExtra(Extra.SelectedItemStr, cancelText);
			setResult(RESULT_OK, intent);
			dismiss();
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return "三个选择按键";
	}
}
