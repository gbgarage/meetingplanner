package dfzq.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class Resource {

    private Set<Company> conflictCompany = new HashSet<Company>();

    private Set<Fund> conflictFunds = new HashSet<Fund>();


    private Set<Company> otherCompanies = new HashSet<Company>();


    public Set<Company> getConflictCompany() {
        return conflictCompany;
    }

    public Set<Fund> getConflictFunds() {
        return conflictFunds;
    }

    public Set<Company> getOtherCompanies() {
        return otherCompanies;
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

