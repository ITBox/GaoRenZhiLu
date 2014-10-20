package com.itbox.grzl.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.AppContext;
import com.itbox.grzl.bean.Account;
import com.zhaoliewang.grzl.R;

/**
 * 更多咨询
 * 
 * @author baoyz
 * @date 2014-10-20
 * 
 */
public class MoreConsulationActivity extends BaseActivity {

	@InjectView(R.id.more_my_consult)
	View more_my_consult;
	@InjectView(R.id.more_my_evaluate)
	View more_my_evaluate;
	@InjectView(R.id.more_my_consult_line)
	View more_my_consult_line;
	@InjectView(R.id.more_my_evaluate_line)
	View more_my_evaluate_line;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_more_consulation);
		ButterKnife.inject(mActThis);

		Account account = AppContext.getInstance().getAccount();
		if (account != null && account.getUsertype() == 2) {
			more_my_consult.setVisibility(View.GONE);
			more_my_evaluate.setVisibility(View.GONE);
			more_my_consult_line.setVisibility(View.GONE);
			more_my_evaluate_line.setVisibility(View.GONE);
		}
		
		showLeftBackButton();
		setTitle("学生咨询");
	}

	@OnClick({ R.id.more_my_ask, R.id.more_my_evaluate,
			R.id.more_my_consult, R.id.more_my_forum })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.more_my_consult:
			startActivity(ConsultationFreeActivity.class);
			break;
		case R.id.more_my_evaluate:
			startActivity(TeacherCommentListActivity.class);
			break;
		case R.id.more_my_ask:
			startActivity(ConsultationMyActivity.class);
			break;
		case R.id.more_my_forum:
			startActivity(AttentionMyActivity.class);
			break;
		}
	}
}
