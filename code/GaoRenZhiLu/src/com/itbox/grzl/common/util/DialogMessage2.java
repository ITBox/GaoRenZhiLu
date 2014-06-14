package com.itbox.grzl.common.util;


import com.zhaoliewang.grzl.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * 
 * @author youzh
 *
 */
public class DialogMessage2 extends DialogFragment {

	public static DialogMessage2 newIntance() {
		DialogMessage2 dialog = new DialogMessage2();
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity(), R.style.custom_dialog);
		View view = View.inflate(getActivity(), R.layout.dialog_message2, null);
		dialog.setContentView(view);
		View ok = view.findViewById(R.id.dailog_ok);
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		return dialog;
	}

}
