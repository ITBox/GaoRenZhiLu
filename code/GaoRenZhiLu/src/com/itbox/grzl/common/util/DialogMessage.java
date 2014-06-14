package com.itbox.grzl.common.util;


import com.zhaoliewang.grzl.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author youzh 2014年5月11日 下午6:49:03
 */
public class DialogMessage extends DialogFragment {

	private TextView mTitle;

	public static DialogMessage newIntance() {
		DialogMessage dialog = new DialogMessage();
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity(), R.style.custom_dialog);
		View view = View.inflate(getActivity(), R.layout.dialog_message, null);
		dialog.setContentView(view);
		mTitle = (TextView) view.findViewById(R.id.dialog_title);
		View ok = view.findViewById(R.id.dialog_ok);
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		View cancel = view.findViewById(R.id.dialog_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		return dialog;
	}
    
    public void setMessage(String msg){
    	mTitle.setText(msg);
    }

}
