package dfzq.model;

import java.util.List;

public class DataList <T> {
	private int total;
	private List<T> data;
	public DataList(int total, List<T> data) {
		this.total = total;
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public List<T> getData() {
		return data;
	}
}