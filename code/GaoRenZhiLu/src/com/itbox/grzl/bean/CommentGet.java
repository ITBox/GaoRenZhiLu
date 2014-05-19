package com.itbox.grzl.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 获取论坛信息
 * 
 * @author byz
 * @date 2014-5-11下午6:14:34
 */
@Table(name = "comment_list", id = CommentGet.ID)
public class CommentGet extends BaseModel implements Parcelable {

	public static final String ID = "_id";
	public static final String CL_ID = "cl_id";
	public static final String TITLE = "cl_title";
	public static final String USERID = "cl_userid";
	public static final String COMMENTCONTENT = "cl_commentcontent";
	public static final String PHOTO = "cl_photo";
	public static final String REPLACECOUNT = "cl_replacecount";
	public static final String USERNAME = "cl_username";
	public static final String CREATETIME = "cl_createtime";

	@Column(name = CommentGet.CL_ID)
	private int id;
	@Column(name = CommentGet.TITLE)
	private String title;
	@Column(name = CommentGet.USERID)
	private String userid;
	@Column(name = CommentGet.COMMENTCONTENT)
	private String commentcontent;
	@Column(name = CommentGet.PHOTO)
	private String photo;
	@Column(name = CommentGet.REPLACECOUNT)
	private int replacecount;
	@Column(name = CommentGet.USERNAME)
	private String username;
	@Column(name = CommentGet.CREATETIME)
	private String createtime;

	public int getCommentId() {
		return id;
	}

	public int getReplacecount() {
		return replacecount;
	}

	public void setReplacecount(int replacecount) {
		this.replacecount = replacecount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.commentcontent);
		dest.writeString(this.photo);
		dest.writeString(this.title);
		dest.writeString(this.userid);
		dest.writeString(this.username);
		dest.writeInt(this.replacecount);
		dest.writeLong(this.getId());
		dest.writeInt(this.id);
	}

	public static final Parcelable.Creator<CommentGet> CREATOR = new Creator<CommentGet>() {

		@Override
		public CommentGet[] newArray(int size) {
			return null;
		}

		@Override
		public CommentGet createFromParcel(Parcel source) {
			CommentGet bean = new CommentGet();
			bean.setCommentcontent(source.readString());
			bean.setPhoto(source.readString());
			bean.setTitle(source.readString());
			bean.setUserid(source.readString());
			bean.setUsername(source.readString());
			bean.setReplacecount(source.readInt());
			bean.setId(source.readLong());
			bean.id = source.readInt();
			return bean;
		}
	};
}
