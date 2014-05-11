package com.itbox.grzl.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.itbox.fx.core.AppContext;
import com.itbox.fx.net.Net;
import com.itbox.fx.net.ResponseHandler;
import com.itbox.grzl.Api;
import com.itbox.grzl.bean.ExamInscribe;
import com.itbox.grzl.common.Contasts;
import com.loopj.android.http.RequestParams;

/**
 * 测评业务
 * 
 * @author byz
 * @date 2014-5-10下午10:53:46
 */
public class ExamEngine {

	public static final String EXAM_FILE = "exam/exam_1";
	public static final int PAGE_NUM = 20;

	/**
	 * 获取测评记录
	 * 
	 * @param pageNum
	 * @param handler
	 */
	public static void getExamReport(int pageNum, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put(
				"userid",
				Integer.toString(AppContext.getUserPreferences().getInt(
						Contasts.USERID, 0)));
		params.put("pagesize", Integer.toString(PAGE_NUM));
		params.put("pageindex", Integer.toString(pageNum));
		Net.request(params, Api.getUrl(Api.User.EXAM_REPORT), handler);
	}

	/**
	 * 获取测评题目
	 * 
	 * @return
	 */
	public static List<ExamInscribe> getExamInscribes() {
		List<ExamInscribe> list = new ArrayList<ExamInscribe>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					AppContext.getInstance().getAssets().open(EXAM_FILE)));
			String line;
			ExamInscribe bean = null;
			String title = null;
			int num = 0;
			while ((line = br.readLine()) != null) {
				String[] array = line.split("\\|");
				if (num++ == 0) {
					title = line;
					continue;
				}
				if (array != null && array.length == 3) {
					bean = new ExamInscribe();
					bean.setNum(Integer.toString(num));
					bean.setTitle(title);
					bean.setContent(array[0]);
					bean.setOptionA(array[1]);
					bean.setOptionB(array[2]);
					list.add(bean);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void submit(List<ExamInscribe> list, ResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("userid", "16"); // TODO userid
		params.put("data", createExamResult(list)); // 测评答案
		Net.request(params, Api.getUrl(Api.User.SUBMIT_EXAM), handler);
	}

	/**
	 * 生成测评试题的回答结果
	 * 
	 * @param list
	 * @return
	 */
	private static String createExamResult(List<ExamInscribe> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			int index = 1;
			for (ExamInscribe bean : list) {
				sb.append(Integer.toString(index)).append(",")
						.append(bean.getSelected()).append(";");
				index++;
			}
		}
		return sb.toString();
	}
}
