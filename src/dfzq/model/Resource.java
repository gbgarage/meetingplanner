package dfzq.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class Resource {

    private List<Fund> fundList;

    private List<Company> companyList;

    public List<Fund> getFundList() {
        return fundList;
    }

    public void setFundList(List<Fund> fundList) {
        this.fundList = fundList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }
}
