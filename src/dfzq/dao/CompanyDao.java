package dfzq.dao;

import com.common.BaseDao;
import dfzq.model.Availability;
import dfzq.model.Company;
import dfzq.model.OneOnOneMeetingRequest;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<OneOnOneMeetingRequest> loadAvailableCompanies(int[] timeFrames) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
