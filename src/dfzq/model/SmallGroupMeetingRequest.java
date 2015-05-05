package dfzq.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 飞
 * Date: 15-5-2
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
public class SmallGroupMeetingRequest extends  MeetingRequest {

    private List<Integer> fundIds;

    private List<Fund> funds;




    public List<Integer> getFundIds() {
        return fundIds;
    }

    public void setFundIds(List<Integer> fundIds) {
        this.fundIds = fundIds;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    @Override
    public int compareTo(MeetingRequest o) {
        int result = this.priority - o.priority;
        if (result != 0) {
            return result;

        } else {
            if (o instanceof OneOnOneMeetingRequest) {
                return -1;
            } else{

                return this.companyId-o.companyId;
            }
        }
    }
}
