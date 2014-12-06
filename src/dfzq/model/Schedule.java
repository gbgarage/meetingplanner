package dfzq.model;

import java.sql.Timestamp;

public class Schedule {
	private long id;
	private String subject;
	private String location;
	private String description;
	private Timestamp startTime;
	private Timestamp endTime;
	private long isAllDayEvent;
	private String color;
	private String recurringRule;
	
	public long getId() {
		return id;
	}
	public void setId(long pId) {
		id = pId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String pSubject) {
		subject = pSubject;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String pLocation) {
		location = pLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String pDescription) {
		description = pDescription;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp pStartTime) {
		startTime = pStartTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp pEndTime) {
		endTime = pEndTime;
	}
	public long getIsAllDayEvent() {
		return isAllDayEvent;
	}
	public void setIsAllDayEvent(long pIsAllDayEvent) {
		isAllDayEvent = pIsAllDayEvent;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String pColor) {
		color = pColor;
	}
	public String getRecurringRule() {
		return recurringRule;
	}
	public void setRecurringRule(String pRecurringRule) {
		recurringRule = pRecurringRule;
	}
}
