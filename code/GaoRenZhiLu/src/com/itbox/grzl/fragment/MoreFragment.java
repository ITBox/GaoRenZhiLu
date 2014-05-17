package com.itbox.grzl.fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author youzh
 * 2014年5月17日 下午4:58:45
 */
public class MoreFragment extends BaseFragment {
	@InjectView(R.id.more_my_photo) CircleImageView mMorePhoto;
	@InjectView(R.id.more_my_name) TextView mMoreName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.fragment_more, null);
		ButterKnife.inject(mActThis, layout);
		return layout;
	}
	
	@OnClick({R.id.more_my_action, R.id.more_my_ask, R.id.more_my_evaluate, R.id.more_my_message, R.id.more_my_tixian, R.id.more_my_shouru, R.id.more_my_forum, R.id.more_my_consult, R.id.more_my_set})
    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_my_action://我的活动
			
			break;
		case R.id.more_my_ask://我的咨询
			break;
		case R.id.more_my_evaluate://我的评价
			break;
		case R.id.more_my_message://我的消息
			break;
		case R.id.more_my_tixian://提现明细
			break;
		case R.id.more_my_shouru://收入明细
			break;
		case R.id.more_my_forum://行业论坛
			break;
		case R.id.more_my_consult://免费咨询
			break;
		case R.id.more_my_set://个人设置
			break;
		default:
			break;
		}
    	super.onClick(v);
    }
}
