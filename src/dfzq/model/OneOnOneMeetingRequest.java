package dfzq.model;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class OneOnOneMeetingRequest {

    private Fund fund;

    private Company company;

    private Integer fundId;

    private Integer companyId;

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
}
