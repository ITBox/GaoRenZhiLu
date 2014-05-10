package com.itbox.grzl.bean;

import java.util.List;

/**
 * 在线学习信息
 * 
 * @author baoyz
 * 
 *         2014-5-2 下午3:10:23
 * 
 */
public class OnLineItem {

	private List<Item> OnLineItem;

	public List<Item> getOnlineItem() {
		return OnLineItem;
	}

	public void setOnlineItem(List<Item> onlineItem) {
		this.OnLineItem = onlineItem;
	}

	public static class Item {
		private int id;
		private String title;
		private String link;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		@Override
		public String toString() {
			return title;
		}
		
	}

}
