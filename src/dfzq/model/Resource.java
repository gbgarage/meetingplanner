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

    private List<Company> conflictCompany;

    private List<Fund> conflictFunds;


    private List<Company> otherCompanies;


    public List<Company> getConflictCompany() {
        return conflictCompany;
    }

    public void setConflictCompany(List<Company> conflictCompany) {
        this.conflictCompany = conflictCompany;
    }

    public List<Fund> getConflictFunds() {
        return conflictFunds;
    }

    public void setConflictFunds(List<Fund> conflictFunds) {
        this.conflictFunds = conflictFunds;
    }

    public List<Company> getOtherCompanies() {
        return otherCompanies;
    }

    public void setOtherCompanies(List<Company> otherCompanies) {
        this.otherCompanies = otherCompanies;
    }


    public void addConflictCompany(Company company) {
        this.conflictCompany.add(company);
    }

    public void addNoneConflictCompany(Company company) {
        this.otherCompanies.add(company);
    }

    public void addConflictFund(Fund fund) {
        this.conflictFunds.add(fund);
    }
}

