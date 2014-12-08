package dfzq.dao;

import com.common.BaseDao;
import dfzq.model.Availability;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-5
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CompanyDao extends BaseDao {
    @Autowired
    private FundDao fundDao;

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

    public Company getCompanyById(Integer companyId) {
        return (Company) getSqlMapClientTemplate().queryForObject("getCompanyById", companyId);

    }


    public void saveCompanyAvailablility(Availability companyAvailability) {
        getSqlMapClientTemplate().insert("saveCompanyAvailablility", companyAvailability);


    }

    public List<OneOnOneMeetingRequest> loadAvailableCompanies(int[] timeFrames, int[] otherTimeFrames) {

        Map<String, String> parameterMap = initParameterMap(timeFrames, otherTimeFrames);
        List<OneOnOneMeetingRequest> oneOnOneMeetingRequests = getSqlMapClientTemplate().queryForList("loadAvailableMeetingRequest", parameterMap);

        Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
        Map<Integer, Fund> fundMap = new HashMap<Integer, Fund>();

        for (OneOnOneMeetingRequest oneOnOneMeetingRequest : oneOnOneMeetingRequests) {
            initCompanyAndFund(oneOnOneMeetingRequest, companyMap, fundMap, timeFrames);

        }


        return oneOnOneMeetingRequests;


    }

    private void initCompanyAndFund(OneOnOneMeetingRequest oneOnOneMeetingRequest, Map<Integer, Company> companyMap, Map<Integer, Fund> fundMap, int[] timeFrames) {
        Integer fundId = oneOnOneMeetingRequest.getFundId();
        Integer companyId = oneOnOneMeetingRequest.getCompanyId();

        Company company = getCompany(companyMap, companyId);
        company.setAvailableMeetingCount(getCompanyAvailableTimeCount(timeFrames, companyId));
        Fund fund = getFund(fundMap, fundId);
        fund.setFundAvailabilityCount(timeFrames.length);
        oneOnOneMeetingRequest.setCompany(company);
        oneOnOneMeetingRequest.setFund(fund);
        company.addOneOnOneMeetingRequest(oneOnOneMeetingRequest);
        fund.addOneOnOneMeetingRequest(oneOnOneMeetingRequest);

    }

    private int getCompanyAvailableTimeCount(int[] timeFrames, Integer companyId) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("companyId", companyId);
        parameterMap.put("companyAvailableTimeFrame", convertArrayToINString(timeFrames));
        return (Integer) getSqlMapClientTemplate().queryForObject("countTheAvailableTime", parameterMap);
    }

    private Fund getFund(Map<Integer, Fund> fundMap, Integer fundId) {
        Fund fund = fundMap.get(fundId);
        if (fund == null) {
            fund = fundDao.getFundById(fundId);
            fundMap.put(fundId, fund);

        }
        return fund;
    }

    private Company getCompany(Map<Integer, Company> companyMap, Integer companyId) {
        Company c = companyMap.get(companyId);
        if (c == null) {
            c = getCompanyById(companyId);
            companyMap.put(companyId, c);
        }
        return c;
    }


    private Map<String, String> initParameterMap(int[] timeFrames, int[] otherTimeFrames) {
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("timeFrame", convertArrayToINString(timeFrames));
        parameterMap.put("otherTimeFrame", convertArrayToINString(otherTimeFrames));
        return parameterMap;

    }

    private String convertArrayToINString(int[] timeFrames) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(");
        for (int timeFrame : timeFrames) {
            stringBuffer.append(timeFrame);
            stringBuffer.append(",");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(")");
        return stringBuffer.toString();

    }


    public Integer getNextAvailableTimeFrame(Company company) {
        List<Integer> availableTimeFrames = (List<Integer>) getSqlMapClientTemplate().queryForObject("getNextAvailableTimeFrame", company);
        if (availableTimeFrames.isEmpty()) {
            return null;
        } else {
            for (Integer availableTimeFrameId : availableTimeFrames) {
                if (checkTimeFrameAvailable(company, availableTimeFrameId)) {
                    return availableTimeFrameId;
                }
            }
            return null;
        }
    }

    private boolean checkTimeFrameAvailable(Company company, Integer availableTimeFrameId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company_id", company.getId());
        parameters.put("availableTimeFrameId", availableTimeFrameId);

        OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) getSqlMapClientTemplate().queryForObject("getOneOnOneMeetingRequestByCompanyIdAndtimeFrameId", parameters);
        return oneOnOneMeetingRequest.getStatus() == null;

    }
}
