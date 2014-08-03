package com.itbox.grzl.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.fx.util.DateUtil;
import com.itbox.fx.widget.CircleImageView;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.OrderInfoModel;
import com.itbox.grzl.bean.TeacherExtension;
import com.itbox.grzl.bean.UserListItem;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.PayEngine;
import com.itbox.grzl.engine.alipay.Result;
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
	@InjectView(R.id.tv_date)
	protected TextView tv_date;
	@InjectView(R.id.cb_client)
	protected CheckBox cb_client;
	@InjectView(R.id.cb_web)
	protected CheckBox cb_web;

	private TeacherExtension teacherExtension;
	private UserListItem teacher;
	private List<TimeSpan> mTimeList;
	private TimeAdapter mAdapter;
	private int mTimeSelected;

	private boolean isPicture;
	private boolean isClient;

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
		initTeacherInfo();

		tv_date.setText(DateUtil.getTodayString());

		if ("picture".equals(getIntent().getStringExtra("type"))) {
			ll_select_time.setVisibility(View.GONE);
			tv_price.setText("￥" + teacherExtension.getFinalPictureprice());
			isPicture = true;
		} else {
			initTime();
			tv_price.setText("￥" + teacherExtension.getFinalPhoneprice());
		}

		isClient = true;
	}

	@OnCheckedChanged({ R.id.cb_client, R.id.cb_web })
	public void payCheck(CompoundButton cb, boolean isCheck) {
		if (isCheck == true) {
			switch (cb.getId()) {
			case R.id.cb_client:
				cb_web.setChecked(false);
				isClient = true;
				break;
			case R.id.cb_web:
				cb_client.setChecked(false);
				isClient = false;
				break;
			}
		}
	}

	@OnClick(R.id.tv_buy)
	public void onBuy(View v) {
		// 购买
		if (isPicture) {
			buyPicture();
		} else {
			String phone = et_phone.getText().toString();
			if (TextUtils.isEmpty(phone)) {
				showToast("请输入接听电话");
				return;
			}
			String time = tv_select_time.getText().toString();
			if ("未选择".equals(time)) {
				showToast("请选择时间");
				return;
			}
			buyPhone();
		}
	}

	@OnClick(R.id.tv_date)
	public void selectDate() {
		Intent intent = new Intent(this, SelectAfterDateActivity.class);
		startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			tv_date.setText(data
					.getStringExtra(SelectDateActivity.Extra.SelectedTimeStr));
			initTime();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Result resultAli = new Result((String) msg.obj);
			String resultStatus = resultAli.getResultStatus();
			if (TextUtils.equals(resultStatus, "9000")) {
				// 跳支付成功
				startActivity(PaySuccessActivity.class);
				finish();
			} else if (TextUtils.equals(resultStatus, "6001")) {
				showToast("支付取消");
			} else {
				// 跳支付失败
				startActivity(PayFailActivity.class);

			}

		};
	};

	/**
	 * 购买电话咨询
	 */
	private void buyPhone() {
		TimeSpan bean = mAdapter.getItem(mTimeSelected);
		String phone = et_phone.getText().toString();
		String date = tv_date.getText().toString();
		showProgressDialog("正在下订单...");
		if (isClient) {
			ConsultationEngine.buyPhone(teacher.getUserid(),
					teacherExtension.getFinalPhoneprice() + "",
					teacherExtension.getPhoneprice(), date,
					bean.getHour() + "", bean.getMin() + "", phone, isClient,
					new GsonResponseHandler<OrderInfoModel>(
							OrderInfoModel.class) {
						@Override
						public void onFinish() {
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(OrderInfoModel object) {
							PayEngine.startAliPayClient(mActThis,
									object.getApipost(), object.getSign(),
									handler);
						}
					});
		} else {
			ConsultationEngine.buyPhone(teacher.getUserid(),
					teacherExtension.getFinalPhoneprice() + "",
					teacherExtension.getPhoneprice(), date,
					bean.getHour() + "", bean.getMin() + "", phone, isClient,
					new GsonResponseHandler<OrderInfoModel>(
							OrderInfoModel.class) {
						@Override
						public void onFinish() {
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							Intent intent = new Intent(mActThis,
									WebBrowserActivity.class);
							intent.putExtra("html", content);
							startActivity(intent);
						}

						@Override
						public void onFailure(Throwable e, int statusCode,
								String content) {
							L.i(content);
							if (statusCode == 417) {
								Intent intent = new Intent(mActThis,
										WebBrowserActivity.class);
								intent.putExtra("html", content);
								startActivity(intent);
							}
						}
					});
		}
	}

	/**
	 * 购买图文咨询
	 */
	private void buyPicture() {
		if (isClient) {
			ConsultationEngine.buyPicture(teacher.getUserid(),
					teacherExtension.getFinalPictureprice() + "",
					teacherExtension.getPictureprice(), isClient,
					new GsonResponseHandler<OrderInfoModel>(
							OrderInfoModel.class) {
						@Override
						public void onFinish() {
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(OrderInfoModel object) {
							PayEngine.startAliPayClient(mActThis,
									object.getApipost(), object.getSign(),
									handler);
						}
					});
		} else {
			ConsultationEngine.buyPicture(teacher.getUserid(),
					teacherExtension.getFinalPictureprice() + "",
					teacherExtension.getPictureprice(), isClient,
					new GsonResponseHandler<OrderInfoModel>(
							OrderInfoModel.class) {
						@Override
						public void onFinish() {
							dismissProgressDialog();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							Intent intent = new Intent(mActThis,
									WebBrowserActivity.class);
							intent.putExtra("html", content);
							startActivity(intent);
						}

						@Override
						public void onFailure(Throwable e, int statusCode,
								String content) {
							L.i(content);
							if (statusCode == 417) {
								Intent intent = new Intent(mActThis,
										WebBrowserActivity.class);
								intent.putExtra("html", content);
								startActivity(intent);
							}
						}
					});
		}
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
		boolean isToday = DateUtil.getTodayString().equals(
				tv_date.getText().toString());
		for (int i = startTime; i <= endTime; i++) {
			for (int j = 0; j < 60; j += 30) {
				if (isToday && (i * 60 + j) < DateUtil.getTodayMin()) {
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
				tv_select_time.setText(tv_date.getText() + " "
						+ mAdapter.getItem(position).getText());
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
						.append(hour >= 10 ? hour : "0" + hour).append(":")
						.append(min >= 10 ? min : "0" + min).toString();
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
