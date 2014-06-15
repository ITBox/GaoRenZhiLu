package com.itbox.grzl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import cn.jpush.android.api.JPushInterface;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.baidu.location.BDLocation;
import com.itbox.fx.core.Application;
import com.itbox.grzl.bean.Account;
import com.itbox.grzl.bean.Job;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhaoliewang.grzl.R;

/**
 * Created by huiyh on 14-2-24.
 */
@SuppressLint("UseSparseArrays")
public class AppContext extends Application {

	private static AppContext instance;

	public static BDLocation location;

	private Account account;

	private static ArrayList<Job> jobArrayList;

	private static Map<Integer, String> jobMap;

	public static AppContext getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();

		ActiveAndroid.initialize(this);

		initImageLoader();

		initJobArrayList();

		initJobMap();
		
		 JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
	}

	private void initJobArrayList() {
		jobArrayList = new ArrayList<Job>();
		Job job = new Job(1, "IT/通讯");
		jobArrayList.add(job);
		job = new Job(2, "电子/互联网");
		jobArrayList.add(job);
		job = new Job(3, "金融");
		jobArrayList.add(job);
		job = new Job(4, "建筑房地产");
		jobArrayList.add(job);
		job = new Job(5, "制造业");
		jobArrayList.add(job);
		job = new Job(6, "物流/仓储");
		jobArrayList.add(job);
		job = new Job(7, "文化/传媒");
		jobArrayList.add(job);
		job = new Job(8, "影视/娱乐");
		jobArrayList.add(job);
		job = new Job(9, "教育");
		jobArrayList.add(job);
		job = new Job(10, "矿产/能源");
		jobArrayList.add(job);
		job = new Job(11, "农林牧渔");
		jobArrayList.add(job);
		job = new Job(12, "医药");
		jobArrayList.add(job);
		job = new Job(13, "商业服务");
		jobArrayList.add(job);
	}

	private void initJobMap() {
		jobMap = new HashMap<Integer, String>();
		jobMap.put(1, "IT/通讯");
		jobMap.put(2, "电子/互联网");
		jobMap.put(3, "金融");
		jobMap.put(4, "建筑房地产");
		jobMap.put(5, "制造业");
		jobMap.put(6, "物流/仓储");
		jobMap.put(7, "文化/传媒");
		jobMap.put(8, "影视/娱乐");
		jobMap.put(9, "教育");
		jobMap.put(10, "矿产/能源");
		jobMap.put(11, "农林牧渔");
		jobMap.put(12, "医药");
		jobMap.put(13, "商业服务");
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
		if (account == null) {
			account = new Select().from(Account.class).executeSingle();
		}
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public static String getJobName(int jobtype) {

		return jobMap.get(jobtype);
	}

	public static ArrayList<Job> getJobs() {

		return jobArrayList;
	}

	public static String[] getJobNameArray() {
		ArrayList<String> jobNameList = new ArrayList<String>();
		for (Job job : jobArrayList) {
			jobNameList.add(job.getName());
		}
		return jobNameList.toArray(new String[jobNameList.size()]);
	}
}
