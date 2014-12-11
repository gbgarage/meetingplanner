package dfzq.model;

import java.util.Date;

public class Timeframe {
	
	private int id;
	
	private String date;
	
	private String time_window;
	
	private String region;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime_window() {
		return time_window;
	}

	public void setTime_window(String time_window) {
		this.time_window = time_window;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
}
