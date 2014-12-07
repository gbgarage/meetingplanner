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
public class Fund {
    private Integer id;

    private String contactor;

    private String phoneNumber;

    private String fundName;

    private int fundAvailabilityCount;


    private List<OneOnOneMeetingRequest> oneOnOneMeetingRequests= new ArrayList<OneOnOneMeetingRequest>();



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

    public void addOneOnOneMeetingRequest(OneOnOneMeetingRequest oneOnOneMeetingRequest) {
        oneOnOneMeetingRequests.add(oneOnOneMeetingRequest);
    }

    public List<OneOnOneMeetingRequest> getOneOnOneMeetingRequests() {
        return oneOnOneMeetingRequests;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "fundName='" + fundName + '\'' +
                ", contactor='" + contactor + '\'' +
                '}';
    }

    public boolean isConflict() {
        return oneOnOneMeetingRequests.size()> fundAvailabilityCount;
    }
}
