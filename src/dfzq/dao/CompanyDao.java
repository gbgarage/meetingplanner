package dfzq.dao;

import java.util.Iterator;
import java.util.List;

import com.common.BaseDao;

import dfzq.model.Availability;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.FundAvailability;
import dfzq.model.Timeframe;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-5
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CompanyDao extends BaseDao {
    public Company saveOrGetId(Company company) {

        Company c = getCompanyByName(company.getName());
        if (c == null) {
            Integer id = saveCompany(company);
            company.setId(id);
            return company;
        } else {
            return c;
        }


    }

    private Integer saveCompany(Company c) {
        return (Integer) getSqlMapClientTemplate().insert("saveCompany", c);
    }

    public Company getCompanyByName(String name) {

        return (Company) getSqlMapClientTemplate().queryForObject("getCompanyByName", name);
    }


    public void saveCompanyAvailablility(Availability companyAvailability) {
        getSqlMapClientTemplate().insert("saveCompanyAvailablility", companyAvailability);


    }
    
    public List<Company> getCompanyList() {
    	return (List<Company>)getSqlMapClientTemplate().queryForList("getCompanyList");
    }
   
    public List<Timeframe> getCompanyTime(Integer companyid) {
    	return (List<Timeframe>)getSqlMapClientTemplate().queryForList("getCompanyTime", companyid);
    }
    
    public Company getCompanyById(Integer companyid) {
    	return (Company)getSqlMapClientTemplate().queryForObject("getCompanyById", companyid);
    }
    
    public void saveTimeCompany(List<Integer> timeids, Integer companyid) {
    	
    	getSqlMapClientTemplate().delete("deleteCompanyAllTimeslots", companyid);
    	
    	Company company = this.getCompanyById(companyid);
    
    	Iterator<Integer> timeItr = timeids.iterator();
    	
    	while (timeItr.hasNext()) {
    		
    		Availability compAvail = new Availability(company, timeItr.next());
    		getSqlMapClientTemplate().insert("insertCompanyTimeslot", compAvail);
    	}
    }
}
