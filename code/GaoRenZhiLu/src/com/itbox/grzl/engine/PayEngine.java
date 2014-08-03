package com.itbox.grzl.engine;

import java.net.URLEncoder;

import android.app.Activity;
import android.os.Handler;

import com.itbox.fx.core.AppException;
import com.itbox.fx.core.L;
import com.itbox.fx.net.Net;
import com.itbox.grzl.Const;
import com.itbox.grzl.bean.OrderInfoModel;
import com.itbox.grzl.engine.alipay.Rsa;
import com.loopj.android.http.RequestParams;

/**
 * @author hyh creat_at：2014-2-28-下午4:54:19
 */
public class PayEngine {

	private static String getOrderInfo(OrderInfoModel model) {

		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Const.ALIPAY_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(model.getOut_trade_no());
		sb.append("\"&subject=\"");
		sb.append(model.getSubject());
		sb.append("\"&body=\"");
		sb.append(model.getBody());
		sb.append("\"&total_fee=\"");
		sb.append(model.getTotal_fee());
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(model.getNotify_url()));// TODO 缺少
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(model.getSeller_id());

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		// sb.append("\"&it_b_pay=\"");
		// sb.append(model.getIt_b_pay());
		sb.append("\"");

		// start the pay.
		return new String(sb);
	}

	private static String getOrderInfoWithoutNull(OrderInfoModel model) {
		RequestParams params = Net.getRequestParamsWithoutNull(model);
		String info = params.toString();
		info = info.replace("=", "=\"").replace("&", "\"&") + "\"";
		return info;
	}

	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	private static String signInfo2(String orderInfo) {
		String sign = Rsa.sign(orderInfo, Const.ALIPAY_PUBLIC);
		sign = URLEncoder.encode(sign);
		orderInfo += "&sign=\"" + sign + "\"&" + getSignType();
		return orderInfo;
	}

	private static String signInfo(String orderInfo, String sign) {
		// String sign = Rsa.sign(orderInfo, Const.ALIPAY_PRIVATE);
		// String newSign = URLEncoder.encode(sign);
		orderInfo += "&sign=\"" + sign + "\"&" + getSignType();
		// orderInfo += "&" + getSignType();
		return orderInfo;
	}

	public static void startAliPayClient(Activity context, String model,
			String sign, Handler handler) {

		try {
			// String info = getOrderInfo(model);
			String info = model;
			// String info2 = signInfo2(info);
			info = signInfo(info, sign);
			new AlipayThread(context, info, handler).start();
		} catch (Exception ex) {
			AppException.handle(ex);
		}

	}

	public static void startAliPayClient(Activity context,
			OrderInfoModel model,Handler handler) {

		try {
			// String info = getOrderInfo(model);
			String info = getOrderInfoWithoutNull(model);
//			String info2 = signInfo2(info);
			info = getOrderInfo(model);
			info = signInfo(info, URLEncoder.encode(model.getSign()));
			
			L.i("order info = " + model.toString());
			L.i("order info = " + info);
			new AlipayThread(context, info, handler).start();
		} catch (Exception ex) {
			AppException.handle(ex);
		}

	}

	public static void startAliPayClient2(Activity context, String result,
			Handler handler) {

		try {
			// String info = getOrderInfo(model);
			// String info = getOrderInfoWithoutNull(model);
			// info = signInfo(info);
			new AlipayThread(context, result, handler).start();
		} catch (Exception ex) {
			AppException.handle(ex);
		}

	}

}
