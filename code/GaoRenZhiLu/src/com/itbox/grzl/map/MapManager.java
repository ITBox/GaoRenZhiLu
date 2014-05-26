package com.itbox.grzl.map;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.itbox.fx.core.AppException;
import com.itbox.fx.util.ToastUtils;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.BuildConfig;
import com.itbox.grzl.Const;

public class MapManager extends BMapManager {
	/*
	 * 注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。
	 * 因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请， 申请及配置流程请参考开发指南的对应章节
	 */
	public boolean m_bKeyRight = true;
	private static MapManager mBMapManager;

	public static MapManager getInstance() {
		if (null == mBMapManager) {
			mBMapManager = new MapManager();
		}
		return mBMapManager;
	}

	private MapManager() {
		super(AppContext.getInstance());
		if (!init(getKeyStr(), new MyGeneralListener())) {
			AppException
					.handle(new NullPointerException("BMapManager  初始化错误!"));
		}
	}

	/**
	 * 获取Key,
	 * 
	 * @return
	 */
	private static String getKeyStr() {

		if (BuildConfig.DEBUG) {
			return Const.MapKey_Debug;
		}
		return Const.MapKey_Release;

	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				ToastUtils.showToast(AppContext.getInstance(), "您的网络出错啦！");
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				ToastUtils.showToast(AppContext.getInstance(), "输入正确的检索条件！");
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				// Toast.show("请在 DemoApplication.java文件输入正确的授权Key！");
				AppException.handle(new NullPointerException(
						"请在MapEngine.java文件输入正确的授权Key！"));
				MapManager.getInstance().m_bKeyRight = false;
			}
		}
	}
}