package com.itbox.grzl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.activity.AttentionMyActivity;
import com.itbox.grzl.activity.CommentListActivity;
import com.itbox.grzl.activity.ConsultationFreeActivity;
import com.itbox.grzl.activity.ConsultationMyActivity;
import com.itbox.grzl.activity.EventMyActivity;
import com.itbox.grzl.activity.ExamReportActivity;
import com.itbox.grzl.activity.TeacherCommentListActivity;
import com.itbox.grzl.activity.TeacherIncomingActivity;
import com.itbox.grzl.activity.TeacherWithdrawalsListActivity;
import com.itbox.grzl.activity.UserInfoActivity;
import com.itbox.grzl.activity.UserSetActivity;
import com.itbox.grzl.bean.Account;

/**
 * 
 * @author youzh 2014年5月17日 下午4:58:45
 */
public class MoreFragment extends BaseFragment {

	@InjectView(R.id.more_my_photo)
	CircleImageView mMorePhoto;
	@InjectView(R.id.more_my_name)
	TextView mMoreName;
	@InjectView(R.id.more_my_evaluate)
	TextView mMoreEvaluate;
	@InjectView(R.id.more_my_tixian)
	TextView mMoreTixian;
	@InjectView(R.id.more_my_shouru)
	TextView mMoreShouru;
	@InjectView(R.id.more_my_consult)
	TextView mMoreConsult;
	@InjectView(R.id.more_my_evaluate_line)
	ImageView mMoreEvaluateLine;
	@InjectView(R.id.more_my_tixian_line)
	ImageView mMoreTixianLine;
	@InjectView(R.id.more_my_shouru_line)
	ImageView mMoreShouruLine;
	@InjectView(R.id.more_my_consult_line)
	ImageView mMoreConsultLine;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.fragment_more, null);
		ButterKnife.inject(mActThis, layout);
		Account account = initView();
		// Account bean = new
		// Select(AccountTable.COLUMN_USERNAME).from(Account.class).executeSingle();
		// bean.getUsername();
		showViews(account);
		return layout;
	}

	public Account initView() {
		Account account = AppContext.getInstance().getAccount();
		loader.displayImage(account.getUseravatarversion(), mMorePhoto,
				photoOptions);
		mMoreName.setText(account.getUsername());
		return account;
	}

	private void showViews(Account account) {
		// TODO Auto-generated method stub
		if (account.getUsertype() == 2) {
			mMoreEvaluate.setVisibility(View.GONE);
			mMoreTixian.setVisibility(View.GONE);
			mMoreShouru.setVisibility(View.GONE);
			mMoreConsult.setVisibility(View.GONE);
			mMoreEvaluateLine.setVisibility(View.GONE);
			mMoreTixianLine.setVisibility(View.GONE);
			mMoreShouruLine.setVisibility(View.GONE);
			mMoreConsultLine.setVisibility(View.GONE);
		}
	}

	@OnClick({ R.id.more_my_photo, R.id.more_my_action, R.id.more_my_ask,
			R.id.more_my_evaluate, R.id.more_my_message, R.id.more_my_tixian,
			R.id.more_my_shouru, R.id.more_my_forum, R.id.more_my_consult,
			R.id.more_my_exam, R.id.more_my_set })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_my_photo:
			startActivityForResult(UserInfoActivity.class, 0);
			break;
		case R.id.more_my_action:// 我的活动
			startActivity(EventMyActivity.class);
			break;
		case R.id.more_my_ask:// 我的咨询
			startActivity(ConsultationMyActivity.class);
			break;
		case R.id.more_my_evaluate:// 我的评价
			startActivity(TeacherCommentListActivity.class);
			break;
		case R.id.more_my_message:// 我的消息
			break;
		case R.id.more_my_tixian:// 提现明细
			startActivity(TeacherWithdrawalsListActivity.class);
			break;
		case R.id.more_my_shouru:// 收入明细
			startActivity(TeacherIncomingActivity.class);
			break;
		case R.id.more_my_forum:// 行业论坛
			startActivity(AttentionMyActivity.class);
			break;
		case R.id.more_my_consult:// 免费咨询
			startActivity(ConsultationFreeActivity.class);
			break;
		case R.id.more_my_exam:// 测评报告
			startActivity(ExamReportActivity.class);
			break;
		case R.id.more_my_set:// 个人设置
			startActivity(UserSetActivity.class);
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		initView();
	}
}
