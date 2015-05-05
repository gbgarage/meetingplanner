package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class OneOnOneMeetingRequest extends MeetingRequest {

    private Fund fund;

    private Integer fundId;

    private boolean musthave;

    private boolean small;

    public boolean isMusthave() {
        return musthave;
    }

    public void setMusthave(boolean musthave) {
        this.musthave = musthave;
    }

    public boolean isSmall() {
        return small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public OneOnOneMeetingRequest(Fund fund, Company company) {
        this.fund = fund;
        this.company = company;

    }

    public OneOnOneMeetingRequest(OneOnOneMeetingRequest meetingReq) {
        this.fund = meetingReq.fund;
        this.company = meetingReq.company;
        this.fundId = meetingReq.fundId;
        this.companyId = meetingReq.companyId;
        this.status = meetingReq.status;
        this.timeFrameId = meetingReq.timeFrameId;
        this.musthave = meetingReq.musthave;
        this.small = meetingReq.small;
    }

    public OneOnOneMeetingRequest() {
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }


    @Override
    public String toString() {
        return "OneOnOneMeetingRequest{" +
                "fund=" + fund +
                '}';
    }

    @Override
    public int compareTo(MeetingRequest o) {
        try {
            int result = this.priority - o.priority;
            if (result != 0) {
                return result;

            } else {
                if (!(o instanceof OneOnOneMeetingRequest)) {
                    return 1;
                } else {
                    OneOnOneMeetingRequest meetingRequest = (OneOnOneMeetingRequest) o;
                    return this.fund.compareTo(meetingRequest.fund);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingRequest)) return false;

        OneOnOneMeetingRequest that = (OneOnOneMeetingRequest) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (fundId != null ? !fundId.equals(that.fundId) : that.fundId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fundId != null ? fundId.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
