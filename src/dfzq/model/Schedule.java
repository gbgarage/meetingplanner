package dfzq.model;

import java.sql.Timestamp;

public class Schedule {
    public static final String ONE_ON_ONE="O";
    public static final String SMALL_GROUP="S";
    public static final String ONE_MANY="P";


	private long id;
	private String subject;
	private String location;
	private String description;
	private Timestamp startTime;
	private Timestamp endTime;
	private long isAllDayEvent;
	private String color;
	private String recurringRule;
	private String attendee;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttendee() {
		return attendee;
	}
	public void setAttendee(String pAttendee) {
		attendee = pAttendee;
	}
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
	
	public Schedule clone() {
		Schedule s = new Schedule();
		s.id = id;
		s.subject = subject;
		s.location = location;
		s.description = description;
		s.startTime = startTime;
		s.endTime = endTime;
		s.isAllDayEvent = isAllDayEvent;
		s.color = color;
		s.recurringRule = recurringRule;
		s.attendee = attendee;

		return s;
	}
}
