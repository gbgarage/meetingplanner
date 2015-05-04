package dfzq.dao;

import java.util.*;

import com.common.BaseDao;

import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void saveCompanyAvailablility(Availability companyAvailability) {
        getSqlMapClientTemplate().insert("saveCompanyAvailablility", companyAvailability);


    }


    public List<Company> getCompanyList(String companyName) {
        return (List<Company>) getSqlMapClientTemplate().queryForList("getCompanyList", "%" + companyName + "%");

    }

    public List<Timeframe> getCompanyTime(Integer companyid) {
        return (List<Timeframe>) getSqlMapClientTemplate().queryForList("getCompanyTime", companyid);
    }

    public Company getCompanyById(Integer companyid) {
        return (Company) getSqlMapClientTemplate().queryForObject("getCompanyById", companyid);
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

    public List<MeetingRequest> loadAvailableCompanies(int[] timeFrames, int[] otherTimeFrames) {

        Map<String, String> parameterMap = initParameterMap(timeFrames, otherTimeFrames);
        //load one on one meeting request
        List<MeetingRequest> meetingRequests = getSqlMapClientTemplate().queryForList("loadAvailableMeetingRequest", parameterMap);

        //load small group  meeting request
        List<MeetingRequest> oneToManyMeetingRequests = loadOneToManyMeetingRequest(parameterMap);

        //load one to many meeting request

        List<MeetingRequest> smallGroupMeetingRequests = loadSmallGroupMeetingRequest(parameterMap);

        meetingRequests.addAll(oneToManyMeetingRequests);
        meetingRequests.addAll(smallGroupMeetingRequests);

        Collections.sort(meetingRequests);

        Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
        Map<Integer, Fund> fundMap = new HashMap<Integer, Fund>();

        for (MeetingRequest meetingRequest : meetingRequests) {
            initCompany(meetingRequest, companyMap, timeFrames);
            if (meetingRequest instanceof OneOnOneMeetingRequest) {
                OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                initCompanyAndFund(oneOnOneMeetingRequest, companyMap, fundMap, timeFrames);
            } else if (meetingRequest instanceof SmallGroupMeetingRequest) {
                SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;

                initFundForSmallGroup(smallGroupMeetingRequest, fundMap, timeFrames);


            }
        }


        return meetingRequests;


    }

    private void initFundForSmallGroup(SmallGroupMeetingRequest smallGroupMeetingRequest, Map<Integer, Fund> fundMap, int[] timeFrames) {
        List<Fund> funds = new ArrayList<Fund>();
        for (Integer fundId : smallGroupMeetingRequest.getFundIds()) {
            Fund fund = getFund(fundMap, fundId);
            fund.setFundAvailabilityCount(timeFrames.length);
            funds.add(fund);
            fund.addOneOnOneMeetingRequest(smallGroupMeetingRequest);
        }
        smallGroupMeetingRequest.setFunds(funds);

    }

    private void initCompany(MeetingRequest meetingRequest, Map<Integer, Company> companyMap, int[] timeFrames) {
        Integer companyId = meetingRequest.getCompanyId();

        Company company = getCompany(companyMap, companyId);
        company.setAvailableMeetingCount(getCompanyAvailableTimeCount(timeFrames, companyId));
        meetingRequest.setCompany(company);
        company.addOneOnOneMeetingRequest(meetingRequest);
    }

    private List<MeetingRequest> loadSmallGroupMeetingRequest(Map<String, String> parameterMap) {
        List<MeetingRequest> meetingRequests = getSqlMapClientTemplate().queryForList("loadAvailableSmallGroupMeetingRequest", parameterMap);
        for (MeetingRequest meetingRequest : meetingRequests) {
            SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;
            loadFundId(smallGroupMeetingRequest, parameterMap);

        }
        return meetingRequests;
    }

    private void loadFundId(SmallGroupMeetingRequest smallGroupMeetingRequest, Map<String, String> parameterMap) {
        parameterMap.put("companyId", smallGroupMeetingRequest.getCompanyId().toString());
        List<Integer> fundIds = getSqlMapClientTemplate().queryForList("loadFundIdByCompnayId", parameterMap);
        smallGroupMeetingRequest.setFundIds(fundIds);
    }

    private List<MeetingRequest> loadOneToManyMeetingRequest(Map<String, String> parameterMap) {
        List<MeetingRequest> meetingRequests = getSqlMapClientTemplate().queryForList("loadAvailableOneToManyMeetingRequest", parameterMap);
        return meetingRequests;

    }

    public List<MeetingRequest> loadAvailableWholeDayCompanies(int[] timeFrames) {

        Map<String, String> parameterMap = initParameterMap(timeFrames, null);
        List<MeetingRequest> meetingRequests = getSqlMapClientTemplate().queryForList("loadAvailableWholeDayCompanies", parameterMap);


        //load small group  meeting request
        List<MeetingRequest> oneToManyMeetingRequests = loadOneToManyMeetingRequest(parameterMap);

        List<MeetingRequest> smallGroupMeetingRequests = loadSmallGroupMeetingRequest(parameterMap);

        meetingRequests.addAll(oneToManyMeetingRequests);
        meetingRequests.addAll(smallGroupMeetingRequests);

        Collections.sort(meetingRequests);

        Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
        Map<Integer, Fund> fundMap = new HashMap<Integer, Fund>();

        for (MeetingRequest meetingRequest : meetingRequests) {
            initCompany(meetingRequest, companyMap, timeFrames);
            if (meetingRequest instanceof OneOnOneMeetingRequest) {
                OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                initCompanyAndFund(oneOnOneMeetingRequest, companyMap, fundMap, timeFrames);
            } else if (meetingRequest instanceof SmallGroupMeetingRequest) {
                SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;

                initFundForSmallGroup(smallGroupMeetingRequest, fundMap, timeFrames);


            }
        }


        return meetingRequests;


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
        if (otherTimeFrames != null) {
            parameterMap.put("otherTimeFrame", convertArrayToINString(otherTimeFrames));
        }
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


    public Integer getNextAvailableTimeFrame(Company company, Fund fund) {
        List<Integer> availableTimeFrames = (List<Integer>) getSqlMapClientTemplate().queryForList("getNextAvailableTimeFrame", company);
        if (availableTimeFrames.isEmpty()) {
            return null;
        } else {
            for (Integer availableTimeFrameId : availableTimeFrames) {
                if (fund == null) {
                    return availableTimeFrameId;
                }
                if (checkTimeFrameAvailable(company, availableTimeFrameId) && fundDao.checkTimeFrameAvailable(fund, availableTimeFrameId)) {
                    return availableTimeFrameId;
                }
            }
            return null;
        }
    }

    public Integer getNextAvailableTimeFrame(Company company, List<Fund> funds) {
        List<Integer> availableTimeFrames = (List<Integer>) getSqlMapClientTemplate().queryForList("getNextAvailableTimeFrame", company);
        if (availableTimeFrames.isEmpty()) {
            return null;
        } else {
            for (Integer availableTimeFrameId : availableTimeFrames) {
                if (!checkTimeFrameAvailable(company, availableTimeFrameId)) {
                    continue;
                }
                boolean flag = true;

                for (Fund fund : funds) {
                    if (fundDao.checkTimeFrameAvailable(fund, availableTimeFrameId)) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    return availableTimeFrameId;
                }
            }
            return null;
        }
    }

    public boolean checkTimeFrameAvailable(Company company, Integer availableTimeFrameId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("company_id", company.getId());
        parameters.put("availableTimeFrameId", availableTimeFrameId);

        MeetingRequest meetingRequest = (MeetingRequest) getSqlMapClientTemplate().queryForObject("getOneOnOneMeetingRequestByCompanyIdAndtimeFrameId", parameters);
        if (meetingRequest == null) {
            return true;
        } else {
            return false;
        }

    }

    //save company changes from frontend
    public void saveCompanyChanges(List<CompanyChangeRow> rows) {

        Iterator<CompanyChangeRow> rowItr = rows.iterator();

        while (rowItr.hasNext()) {

            CompanyChangeRow row = rowItr.next();

            //if the change is for add
            if (row.get_state().equals("added")) {
                getSqlMapClientTemplate().insert("insertCompanyfromChanges", row);
            }

            //if the change is for modify

            if (row.get_state().equals("modified")) {
                getSqlMapClientTemplate().update("updateCompanyfromChanges", row);
            }

            //if the change is for remove
            if (row.get_state().equals("removed")) {
                getSqlMapClientTemplate().delete("deleteCompanyfromChanges", row);
            }
        }


    }
}
