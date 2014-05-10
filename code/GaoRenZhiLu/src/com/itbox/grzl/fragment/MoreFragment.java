package com.itbox.grzl.fragment;

import butterknife.ButterKnife;

import com.itbox.grzl.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MoreFragment extends BaseFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = null;
		if (container != null) {
			layout = inflater.inflate(R.layout.fragment_more, null);
			ButterKnife.inject(mActThis, layout);
		}
		return layout;
	}
	
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	super.onClick(v);
    }
}
