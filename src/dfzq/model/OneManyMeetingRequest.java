package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: 飞
 * Date: 15-5-2
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class OneManyMeetingRequest extends MeetingRequest {
    public OneManyMeetingRequest() {
        this.priority=3;
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
                return this.company.getId()- o.companyId;
            }
        }
    }



}
