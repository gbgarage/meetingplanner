package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: 飞
 * Date: 15-5-2
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class MeetingRequest implements Comparable<MeetingRequest> {
    protected Company company;
    protected Integer companyId;
    protected Integer status;
    protected Integer timeFrameId;

    protected Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(Integer timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


}
