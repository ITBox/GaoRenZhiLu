package com.itbox.grzl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 测评试题
 * 
 * @author byz
 * 
 */
public class ExamInscribe implements Parcelable {

	public static final String SELECT_A = "A";
	public static final String SELECT_B = "B";

	private String num;
	private String title; // 标题
	private String content; // 内容
	private String optionA; // A选择
	private String optionB; // B选择
	private String selected; // 已选择

	private String inscribe;
	
	public ExamInscribe() {
		selected = SELECT_A;	// 默认选择A
	}

	public String getInscribe() {
		if (inscribe == null) {
			StringBuilder sb = new StringBuilder();
			sb.append(num).append("、").append(title).append("\n")
					.append(content);
			inscribe = sb.toString();
		}
		return inscribe;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(optionA);
		dest.writeString(optionB);
		dest.writeString(selected);
	}

	public static final Parcelable.Creator<ExamInscribe> CREATOR = new Creator<ExamInscribe>() {

		@Override
		public ExamInscribe[] newArray(int size) {
			return null;
		}

		@Override
		public ExamInscribe createFromParcel(Parcel source) {
			ExamInscribe bean = new ExamInscribe();
			bean.setTitle(source.readString());
			bean.setContent(source.readString());
			bean.setOptionA(source.readString());
			bean.setOptionB(source.readString());
			bean.setSelected(source.readString());
			return bean;
		}
	};
}
