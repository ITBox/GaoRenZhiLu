package com.itbox.grzl.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.DateUtil;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.TeacherExtension;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhaoliewang.grzl.R;

public class PayActivity extends BaseActivity {

	@InjectView(R.id.ll_select_time)
	protected View ll_select_time;
	@InjectView(R.id.iv_avatar)
	protected CircleImageView iv_avatar;
	@InjectView(R.id.tv_name)
	protected TextView tv_name;
	@InjectView(R.id.ratingbar)
	protected RatingBar rb_star;
	@InjectView(R.id.tv_jobtype)
	protected TextView tv_jobtype;
	@InjectView(R.id.tv_teachertype)
	protected TextView tv_teachertype;
	@InjectView(R.id.tv_teacher_description)
	protected TextView tv_teacher_description;
	@InjectView(R.id.tv_buy_count)
	protected TextView tv_buy_count;
	@InjectView(R.id.tv_answer_count)
	protected TextView tv_answer_count;

	@InjectView(R.id.tv_price)
	protected TextView tv_price;
	@InjectView(R.id.gv_time)
	protected GridView gv_time;
	@InjectView(R.id.et_phone)
	protected EditText et_phone;
	@InjectView(R.id.tv_select_time)
	protected TextView tv_select_time;

	private TeacherExtension teacherExtension;
	private UserListItem teacher;
	private List<TimeSpan> mTimeList;
	private TimeAdapter mAdapter;
	private int mTimeSelected;

	private boolean isPicture;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pay);

		teacher = (UserListItem) getIntent().getSerializableExtra("teacher");
		teacherExtension = (TeacherExtension) getIntent().getSerializableExtra(
				"teacherExtension");

		ButterKnife.inject(this);

		showLeftBackButton();
		setTitle("购买咨询");

		if ("picture".equals(getIntent().getStringExtra("type"))) {
			ll_select_time.setVisibility(View.GONE);
			tv_price.setText("￥" + teacherExtension.getFinalPictureprice());
			isPicture = true;
		} else {
			initTime();
			tv_price.setText("￥" + teacherExtension.getFinalPhoneprice());
		}

		initTeacherInfo();
	}

	@OnClick(R.id.tv_buy)
	public void onBuy(View v) {
		// 购买
		if (isPicture) {
			buyPicture();
		} else {
			buyPhone();
		}
	}

	/**
	 * 购买电话咨询
	 */
	private void buyPhone() {
		TimeSpan bean = mAdapter.getItem(mTimeSelected);
		String phone = et_phone.getText().toString();
		String date = "2014-6-15";
		ConsultationEngine.buyPhone(teacher.getUserid(),
				teacherExtension.getFinalPhoneprice() + "",
				teacherExtension.getPhoneprice(), date, bean.getHour() + "", bean.getMin() + "", phone, null);
	}

	/**
	 * 购买图文咨询
	 */
	private void buyPicture() {
		ConsultationEngine.buyPicture(teacher.getUserid(),
				teacherExtension.getFinalPictureprice() + "",
				teacherExtension.getPicturepice(), null);
	}

	private void initTeacherInfo() {
		ImageLoader.getInstance().displayImage(
				Api.User.getAvatarUrl(teacher.getUseravatarversion()),
				iv_avatar);
		tv_answer_count.setText("答题" + teacher.getAnswercount() + "次");
		tv_buy_count.setText(teacher.getBuycount() + "人购买");
		tv_jobtype.setText(ConsultationEngine.getJobType(teacher.getJobtype()));
		tv_name.setText(teacher.getUserrealname());
		tv_teacher_description.setText(teacher.getUserintroduction());
		tv_teachertype.setText(ConsultationEngine.getTeacherType(teacher
				.getTeachertype()));
		rb_star.setProgress(Integer.parseInt(teacher.getTeacherlevel()));
	}

	private void initTime() {
		// 获取可预订时间
		ConsultationEngine.getTeacherBooking(teacher.getUserid(), null);
		// 计算时间段
		int startTime = Integer.parseInt(teacherExtension.getStarttime());
		int endTime = Integer.parseInt(teacherExtension.getEndtime());
		mTimeList = new ArrayList<TimeSpan>();
		for (int i = startTime; i <= endTime; i++) {
			for (int j = 0; j < 60; j += 30) {
				if ((i * 60 + j) < DateUtil.getTodayMin()) {
					continue;
				}
				TimeSpan bean = new TimeSpan();
				bean.setHour(i);
				bean.setMin(j);
				mTimeList.add(bean);
			}
		}
		mAdapter = new TimeAdapter();
		gv_time.setAdapter(mAdapter);
		gv_time.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mTimeSelected = position;
				mAdapter.notifyDataSetChanged();
				tv_select_time.setText(mAdapter.getItem(position).getText());
			}
		});
	}

	class TimeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mTimeList.size();
		}

		@Override
		public TimeSpan getItem(int position) {
			return mTimeList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ViewHolder holder = null;
			if (view == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.item_grid_time_span, null);
				holder = new ViewHolder();
				holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			TimeSpan bean = getItem(position);
			holder.tv_time.setText(bean.getText());
			if (mTimeSelected == position) {
				// 选中
				holder.tv_time.setBackgroundColor(getResources().getColor(
						R.color.time_span_blue));
			} else {
				if (bean.getState() == TimeSpan.STATE_NOR) {
					holder.tv_time.setBackgroundColor(getResources().getColor(
							R.color.time_span_green));
				} else {
					holder.tv_time.setBackgroundColor(getResources().getColor(
							R.color.time_span_red));
				}
			}
			return view;
		}
	}

	static class ViewHolder {
		TextView tv_time;
	}

	static class TimeSpan {

		public static final int STATE_NOR = 0;
		public static final int STATE_BOOK = 1;

		private String text;
		private int hour;
		private int min;
		private int state;

		public String getText() {
			if (text == null) {
				text = new StringBuilder()
						.append(hour > 10 ? hour : "0" + hour).append(":")
						.append(min > 10 ? min : "0" + min).toString();
			}
			return text;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMin() {
			return min;
		}

		public void setMin(int min) {
			this.min = min;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

	}
}
