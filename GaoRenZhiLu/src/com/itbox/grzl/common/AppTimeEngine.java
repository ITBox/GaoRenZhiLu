package com.itbox.grzl.common;

import com.itbox.fx.core.AppTime;
import com.itbox.fx.core.AppTime.OnTimeSetChangedListener;
import com.itbox.fx.core.L;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.fx.util.DateUtil;
import com.itbox.grzl.Api;

public class AppTimeEngine implements OnTimeSetChangedListener{

	private ResponseHandler handler = new ResponseHandler(false){
		public void onSuccess(String content) {
			content = content.replace("\"", "").replace("/", "-");
			L.i("SvrTime: " + content);
//			L.i("start: "+CalendarUtils.formatDate(startMills) +"    "+startMills);
//			L.i("end: "+CalendarUtils.formatDate(endMills) +"    "+endMills);
			long svrTimeMills = DateUtil.parseDate(content);
			AppTime.refreshTimeDeviation(svrTimeMills);
		};
	};

	@Override
	public void onTimeSetChanged() {
		Net.request(null,Api.getUrl(Api.Common.GetServerTime), handler);
	}

}
