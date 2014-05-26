package com.itbox.grzl;

import java.util.ArrayList;

import com.activeandroid.ActiveAndroid;
import com.baidu.location.BDLocation;
import com.itbox.fx.R;
import com.itbox.fx.core.Application;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.Job;
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

	public static BDLocation location;

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

	public static ArrayList<Job> getJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		Job job = new Job(1, "IT/通讯");
		jobs.add(job);
		job = new Job(2, "电子/互联网");
		jobs.add(job);
		job = new Job(3, "金融");
		jobs.add(job);
		job = new Job(4, "建筑房地产");
		jobs.add(job);
		job = new Job(5, "制造业");
		jobs.add(job);
		job = new Job(6, "物流/仓储");
		jobs.add(job);
		job = new Job(7, "文化/传媒");
		jobs.add(job);
		job = new Job(8, "影视/娱乐");
		jobs.add(job);
		job = new Job(9, "教育");
		jobs.add(job);
		job = new Job(10, "矿产/能源");
		jobs.add(job);
		job = new Job(11, "农林牧渔");
		jobs.add(job);
		job = new Job(12, "医药");
		jobs.add(job);
		job = new Job(13, "商业服务");
		jobs.add(job);
		return jobs;
	}
}
