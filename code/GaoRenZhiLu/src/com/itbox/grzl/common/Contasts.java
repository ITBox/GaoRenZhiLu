package com.itbox.grzl.common;

import java.io.File;

import com.itbox.fx.util.FileUtil;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;


/**
 * 
 * @author youzh
 *
 */
public class Contasts {

	public static final String USERID = "userid";
	
	public static final int RESULT_FAIL   = 0;
	public static final int RESULT_SUCCES = 1;
	
	public static final int REQUEST_SELECT_SEX            = 1000; 
	public static final int REQUEST_SELECT_BIRTHDAY       = 1001;
	public static final int REQUEST_SELECT_AREA           = 1002;
	public static final int REQUEST_SELECT_TYPE           = 1003;
	public static final int REQUEST_SELECT_ZIXUN_TIME     = 1004;
	
	public static final int TAKE_PICTURE_FROM_GALLERY     = 2000;
	public static final int TAKE_PICTURE_FROM_CAMERA      = 2050;
	
	public static final int UPLOAD_IDCARD                 = 2100;
	public static final int CROP_CAMERA_PICTURE           = 2150;
	public static final int CROP_GALLERY_PICTURE          = 2200;
	
	public static final class State {
		private static final int SUCCES            = 200;  // 请求的操作处理成功
		private static final int BAD_REQUEST       = 400;  // 请求操作的数据不存在or验证码错误or密码错误
	    private static final int UNAUTHORIZED      = 401;  // 客户端未登录，要求登录
	    private static final int NOT_FOUND         = 404;  // 请求的操作处理失败
	    private static final int NOT_ACCEPTABLE    = 406;  // 请求所发送的数据格式错误
	    private static final int FAIL              = 500;  // 服务器遇到一个错误，使其无法为请求提供服务。
	    
	    public static String getDesription(int code) {
	    	switch (code) {
			case SUCCES:
				return "处理成功";
			case BAD_REQUEST:
				return "";
			case UNAUTHORIZED:
				return "未登录，请登录";
			case NOT_FOUND:
				return "请求的操作处理失败";
			case NOT_ACCEPTABLE:
				return "数据格式错误";
			case FAIL:
				return "请求服务失败";
			default:
				return "";
			}
	    	
	    }
			
	}
	public static Uri photoUri(Context ctx) {
		if (FileUtil.isSDCardAvailable()) { // 有外置存储卡
			return Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg");
		} else { // 没有，存到内置的
			return Uri.parse("file://" + new File(ctx.getFilesDir(), "tmp.jpg").getAbsolutePath());
		}
	}
}
