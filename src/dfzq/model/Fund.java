package dfzq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-11-27
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public class Fund implements Comparable<Fund> {
    private Integer id;

    private String contactor;

    private String phoneNumber;

    private String fundName;

    private int fundAvailabilityCount;

    private int priority;


    private List<MeetingRequest> meetingRequests = new ArrayList<MeetingRequest>();


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Fund() {
    }

    public Fund(String contactor, String phoneNumber, String fundName) {

        this.contactor = contactor;
        this.phoneNumber = phoneNumber;
        this.fundName = fundName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }


    public int getFundAvailabilityCount() {
        return fundAvailabilityCount;
    }

    public void setFundAvailabilityCount(int fundAvailabilityCount) {
        this.fundAvailabilityCount = fundAvailabilityCount;
    }

    public void addOneOnOneMeetingRequest(MeetingRequest meetingRequest) {
        meetingRequests.add(meetingRequest);
    }

    public List<MeetingRequest> getOneOnOneMeetingRequests() {
        return meetingRequests;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "fundName='" + fundName + '\'' +
                ", contactor='" + contactor + '\'' +
                '}';
    }

    public boolean isConflict() {
        return meetingRequests.size() > fundAvailabilityCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fund)) return false;

        Fund fund = (Fund) o;

        if (id != null ? !id.equals(fund.id) : fund.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(Fund o) {
        if (this.priority - o.priority != 0) {
            return this.priority - o.priority;
        } else {
            return this.fundName.compareTo( o.fundName);
        }
    }

    public void decreaseAvailbility() {
        this.fundAvailabilityCount--;

    }
}
