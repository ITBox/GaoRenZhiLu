package com.itbox.grzl;

/**
 * WebService接口地址
 * 
 * @author hyh
 */
public class Api {
	/** 正式地址 */
	private static final String API_HOST = "http://115.28.105.82:8006/";
	private static final String CODE_HOST = "http://whoyao.com/checkcode.ashx?key=android_remark";
	private static final String IMAGE_HOST = "http://image.whoyao.net/";
	private static final String IMAGE_URL = "http://115.28.105.82:8007/%s/100x100.jpg";

	public static final int PAGE_SIZE = 15;
	public static final int PAGE_DEFAULT_INDEX = 1;
	// /**测试 接口地址*/
	// private static final String Api_d = "http://api.d.whoyao.com/";
	// private static final String Api_r = "http://api.r.whoyao.com/";
	// private static final String Api_h = "http://10.10.1.137:40008/";
	// private static final String Api_c = "http://10.10.1.155:82/";
	// /**测试 验证码地址*/
	// private static final String Code_d =
	// "http://d.whoyao.com/checkcode.ashx?key=android_remark";
	// private static final String Code_r =
	// "http://r.whoyao.com/checkcode.ashx?key=android_remark";
	// private static final String Code_h =
	// "http://10.10.1.137/checkcode.ashx?key=android_remark";
	// // 211.100.49.56
	// /**测试 图片地址*/
	// private static final String Image_d = "http://image.d.whoyao.com/";
	// private static final String Image_r = "http://image.r.whoyao.com/";
	//
	// /**接口地址*/
	// public static final String Api_Addr = Api_d;
	// /**图片地址*/
	// public static final String Image_Addr = Image_d;
	// /**验证码图片*/
	// public static final String Code_Addr = Code_d;

	/** 图片尺寸 */
	// private static final String ImageDemen_60 = "/60x60.jpg";
	public static final String ImageDemen_100 = "/100x100.jpg";
	public static final String ImageDemen_120_90 = "/120x90.jpg";
	public static final String ImageDemen_240_180 = "/240x180.jpg";
	public static final String ImageDemen_0 = "/0x0.jpg";

	/** 公共模块 */
	public static final class Common {
		private static final String PartName = "common/";
		/** 与系统同步时间 */
		public static final String GetServerTime = PartName + "getsystemtime";
		// /**获取系统标签*/
		// public static final String GetSystemTag = PartName + "getsystemtag";
		// /**测试UserAgent*/
		// public static final String TestUserAgent = PartName + "useragent";
	}

	/**
	 * 用户模块
	 * 
	 * @author hyh
	 */
	public static final class User {

		private static final String PartName = "user/";
		/** 1.客户端初始化和检查更新 */
		public static final String CheckUpdate = PartName + "mobileinfo";
		/** 2.登录 */
		public static final String Login = PartName + "login";
		/** 3.注册 */
		public static final String Register = PartName + "register";
		/** 4.验证账号是否已注册 */
		public static final String CheckAccount = PartName + "checkaccount";
		/** 5.获取短信验证码 */
		public static final String SendVerifyCode = PartName + "sendphonecode";
		/** 6.校验短信验证码 */
		public static final String CheckVerifyCode = PartName
				+ "checkphonecode";
		/** 8.邮箱找回密码 */
		public static final String ResetPwdEmail = PartName + "uppwdbyemail";
		/** 9.获取用户详细信息 */
		public static final String DetailInfo = PartName + "getmyuserdetail";
		/** 10.编辑个人资料 */
		public static final String UpUserInfo = PartName + "upuserdetail";
		/** 12.修改个人头像 */
		public static final String UpUserFace = PartName + "upuserface";
		/** 13.上传个人照片 */
		public static final String AddUserPhoto = PartName + "adduserphoto";
		/** 14.删除用户照片 */
		public static final String DelUserPhoto = PartName + "deleteuserphoto";
		/** 15.实名认证 */
		public static final String UpHonestyInfo = PartName + "myhonestyinfo";
		/** 16.获取闲人预告 */
		public static final String GetFree = PartName + "getmyfree";
		/** 17.发闲人预告 */
		public static final String AddMyFree = PartName + "addmyfree";
		/** 18.修改密码 */
		public static final String ChangePassword = PartName + "updatepwd";
		/** 19.标签换一换 */
		public static final String GetNextTags = PartName + "nexttags";
		/** 20.添加标签 */
		public static final String UpTags = PartName + "addtag";
		/** 21.删除标签 */
		public static final String DeleteTag = PartName + "deleteusertag";
		/** 22.获取隐私设置 */
		public static final String AddUserSafeSetting = PartName
				+ "addusersetsafe";
		/** 23.修改隐私设置 */
		public static final String UpUserSafeSetting = PartName + "usersetsafe";
		/** 24.获取隐私设置 */
		public static final String GetUserSafeSetting = PartName
				+ "getusersetsafe";
		/** 25.修改条件设置 */
		public static final String UpConditionSetting = PartName
				+ "conditionsetting";
		/** 26.获取条件设置 */
		public static final String GetConditionSetting = PartName
				+ "getconditionsetting";
		/** 27.意见反馈 */
		public static final String Feedback = PartName + "addfeedback";
		public static final String GETUSEREXTENSION = PartName
				+ "getuserextension";
		/** 28.修改时空设置 */
		public static final String UpUserSpacetime = PartName
				+ "adduserspantime";
		/** 29.手机找回密码 */
		public static final String RetrievePwdByPhone = PartName
				+ "uppwdbyuserphone";
		/** 30.获取时空设置 */
		public static final String GetUserSpacetime = PartName
				+ "getuserspacetime";
		/* 31.注销接口,仅ios */
		/** 32.获取实名认证信息 */
		public static final String GetHonestyInfo = PartName + "getuserhonesty";
		/** 33 删除全部标签 */
		public static final String DeleteAllTag = PartName + "deletealltag";
		/*
		 * 用户模块第2部分
		 */
		/** 获取用户信息 **/
		public static final String GET_USER_LIST = PartName + "getuserlist";
		public static final String GET_USER_EXTENSION = PartName
				+ "getuserextension";
		/**
		 * 修改导师更多资料接口
		 */
		public static final String UP_USER_MORE = PartName
				+ "updateuserextension";
		/**
		 * 修改个人资料
		 */
		public static final String UP_USER_INFO = PartName + "updateuserlist";
		/**
		 * 添加身份验证接口
		 */
		public static final String ADD_USER_IDCARD = PartName
				+ "adduserauthentication";
		/** 获取老师评价 **/
		public static final String GET_TEACHER_COMMENT = PartName
				+ "getteachercomment";
		public static final String ADD_TEACHER_COMMENT = PartName
				+ "addteachercomment";
		/** 上传图片 **/
		public static final String UPLOAD_IMAGE = PartName + "uploadimage";

		/** 测评 */
		/** 提交测评 */
		public static final String SUBMIT_EXAM = PartName + "addusertesting";
		/** 测评记录 */
		public static final String EXAM_REPORT = PartName + "getusertesting";

		/** 论坛 */
		/** 添加论坛 */
		public static final String ADD_COMMENT = PartName + "addcomment";
		/** 添加论坛评论 */
		public static final String ADD_COMMENTRE_MARK = PartName
				+ "addcommentremark";
		/** 获取论坛 */
		public static final String GET_COMMENT = PartName + "getcomment";
		/** 获取论坛 */
		public static final String GET_COMMENTRE_MARK = PartName
				+ "getcommentremark";

		/** 收入 */
		/** 获取收入明细 */
		public static final String GET_TEACHER_INCOME = PartName
				+ "getteacherincome";
		/** 申请提现 */
		public static final String ADD_USER_WITHDRAWALS = PartName
				+ "adduserwithdrawals";
		/** 获取申请提现记录 */
		public static final String GET_USER_WITHDRAWALS = PartName
				+ "getuserwithdrawals";
		/** 取消申请提现记录 */
		public static final String CANCEL_USER_WITHDRAWALS = PartName
				+ "canceluserwithdrawals";

		/**
		 * 获取用户头像地址
		 * 
		 * @param path
		 * @return
		 */
		public static String getAvatarUrl(String path) {

			return String.format(IMAGE_URL, path);
		}
	}

	/** 活动模块接口 */
	public static final class Event {

		private static final String PartName = "activity/";

		/** 1:添加活动接口 */
		public static final String add = PartName + "addactivity";
		/** 2:活动搜索接口 */
		public static final String search = PartName + "searchactivity";
		/** 3:参加活动接口 */
		public static final String join = PartName + "joinactivityuser";
		/** 4:添加感兴趣接口 */
		public static final String interestAdd = PartName
				+ "addactivityuserinterest";
		/** 5:添加活动评论接口 */
		public static final String commentAdd = PartName
				+ "addactivityusercomment";
		/** 6:获取活动评论接口 */
		public static final String commentList = PartName
				+ "getactivityusercomment";
		/** 7:获取活动详情接口 */
		public static final String detail = PartName + "getactivitydetail";
		/** 8:我的活动接口 */
		public static final String listForUser = PartName + "getmyactivity";

	}

	/** 在线学习模块 */
	public static final class Online {

		private static final String PartName = "OnLine/";
		/** 1.获取在线活动接口 */
		public static final String getOnline = PartName + "getonline";
		/** 2.添加学习统计数接口 */
		public static final String addStatistics = PartName
				+ "addonlinestatistics";

	}

	/** 支付模块 */
	public static final class Pay {

		private static final String PartName = "Alipay/";
		// /** 1.话题首页 */
		// public static final String TopicHome = PartName + "topiclist";

	}

	/** 咨询模块 */
	public static final class Consultation {

		private static final String PartName = "Consultation/";
		/** 1.马上提问 */
		public static final String probleming = PartName + "probleming";
		/** 2.咨询搜索 */
		public static final String getteacher = PartName + "getteacher";

		public static final String searchprobleming = PartName
				+ "searchprobleming";
		public static final String getteacherbooking = PartName
				+ "getteacherbooking";
		public static final String GETUSERMEMBER = PartName + "getusermember";
		public static final String ISSOLVE = PartName + "issolve";
		public static final String GETUSERPROBLEMDETAIL = PartName + "getuserproblemdetail";

	}

	/** 支付模块 */
	public static final class Alipay {
		private static final String PartName = "Alipay/";
		public static final String Buy_Member_Web = PartName + "memberwebstart";
		public static final String Buy_Member_Clinet = PartName
				+ "memberclientstart";
		public static final String Buy_Picture_Web = PartName
				+ "picturewebstart";
		public static final String Buy_Picture_Client = PartName
				+ "pictureclientstart";
		public static final String Buy_Phone_Web = PartName + "phonewebstart";
		public static final String Buy_Phone_Client = PartName
				+ "phoneclientstart";

	}

	private static String getHost() {
		return API_HOST;
	}

	/** 获取Api的URL地址 */
	public static String getUrl(String apiName) {
		return getHost() + apiName;
	}

	/** 获取图片的URL地址 */
	// public static String getImageUrl(String ImageID,String ImageDimen){
	// return Image_Addr + ImageID + ImageDimen;
	// }

}