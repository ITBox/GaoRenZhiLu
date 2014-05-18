package com.itbox.grzl;

import com.activeandroid.ActiveAndroid;
import com.itbox.fx.R;
import com.itbox.fx.core.Application;
import com.itbox.grzl.bean.Account;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by huiyh on 14-2-24.
 */
public class AppContext extends Application {

	private static AppContext instance;

	private Account account;

	public static AppContext getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();

		ActiveAndroid.initialize(this);

		initImageLoader();
	}

	/**
	 * 初始化ImageLoader
	 */
	private void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(options).writeDebugLogs()
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
