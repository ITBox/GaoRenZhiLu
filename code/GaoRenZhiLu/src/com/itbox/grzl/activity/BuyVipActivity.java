package com.itbox.grzl.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import com.activeandroid.content.ContentProvider;
import com.itbox.fx.core.L;
import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.adapter.UserLevelAdapter;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.bean.OrderInfoModel;
import com.itbox.grzl.bean.UserLevel;
import com.itbox.grzl.engine.ConsultationEngine;
import com.itbox.grzl.engine.PayEngine;
import com.itbox.grzl.engine.alipay.Result;
import com.zhaoliewang.grzl.R;

public class BuyVipActivity extends BaseActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.list)
	ListView listView;
	private ConsultationApi consultationApi;
	private UserLevelAdapter adapter;
	private View mHeaderView;

	@InjectView(R.id.cb_client)
	protected CheckBox cb_client;
	@InjectView(R.id.cb_web)
	protected CheckBox cb_web;
	@InjectView(R.id.tv_price)
	protected TextView tv_price;

	private boolean isClient = true;
	private UserLevel bean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_buy_vip);
		setTitle("购买会员");
		showLeftBackButton();
		ButterKnife.inject(this);
		consultationApi = new ConsultationApi();
		consultationApi.getUserLevel(AppContext.getInstance().getAccount()
				.getUserid().toString());
		mHeaderView = View.inflate(this, R.layout.layout_buy_vip_header, null);
		listView.addHeaderView(mHeaderView);
		adapter = new UserLevelAdapter(this, null);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				bean = new UserLevel();
				bean.loadFromCursor((Cursor) adapter.getItem(position - 1));
				adapter.setClickPosition(position - 1);
				adapter.notifyDataSetChanged();
				tv_price.setText("￥" + bean.getPrice());
			}
		});
		getSupportLoaderManager().initLoader(0, null, this);
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

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Result resultAli = new Result((String) msg.obj);
			String resultStatus = resultAli.getResultStatus();
			if (TextUtils.equals(resultStatus, "9000")) {
				// 跳支付成功
				Intent intent = new Intent(mActThis, PaySuccessActivity.class);
				intent.putExtra("price", bean.getPrice().doubleValue());
				startActivity(intent);
				finish();
			} else if (TextUtils.equals(resultStatus, "6001")) {
				showToast("支付取消");
			} else {
				// 跳支付失败
				startActivity(PayFailActivity.class);

			}

		};
	};

	@OnClick(R.id.tv_buy)
	public void buy(View v) {
		if (bean == null) {
			showToast("请选择会员");
			return;
		}
		if (isClient) {
			ConsultationEngine.buyMember(bean.getMemberid().toString(),
					isClient, new GsonResponseHandler<OrderInfoModel>(
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
			ConsultationEngine.buyMember(bean.getMemberid().toString(),
					isClient, new GsonResponseHandler<OrderInfoModel>(
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

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new CursorLoader(this, ContentProvider.createUri(
				UserLevel.class, null), null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (arg0 != null) {
			adapter.swapCursor(cursor);
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
