package me.ianhe.model.wx;

import me.ianhe.model.wx.Item;

import java.util.List;

public class RootMedia {
	private List<Item> item;

	private int total_count;

	private int item_count;

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getItem_count() {
		return item_count;
	}

	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}

}
