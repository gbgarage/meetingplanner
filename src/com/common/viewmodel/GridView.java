package com.common.viewmodel;

import java.util.List;


public class GridView<T> {
	private int total;
	private List<T> data;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
