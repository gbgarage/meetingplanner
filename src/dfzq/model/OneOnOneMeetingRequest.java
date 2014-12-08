package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class OneOnOneMeetingRequest implements Comparable<OneOnOneMeetingRequest> {

    private Fund fund;

    private Company company;

    private Integer fundId;

    private Integer companyId;

    private Integer status;

    private Integer timeFrameId;

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

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public OneOnOneMeetingRequest(Fund fund, Company company) {
        this.fund = fund;
        this.company = company;

    }

    public OneOnOneMeetingRequest() {
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    @Override
    public String toString() {
        return "OneOnOneMeetingRequest{" +
                "fund=" + fund +
                '}';
    }

    @Override
    public int compareTo(OneOnOneMeetingRequest o) {
        return this.fund.compareTo(o.fund);
    }
}
