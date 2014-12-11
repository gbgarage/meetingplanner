package dfzq.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.common.BaseDao;

import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.FundAvailability;
import dfzq.model.OneOnOneMeetingRequest;
import dfzq.model.Timeframe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-5
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@Component
public class FundDao extends BaseDao{

	@Autowired
	CompanyDao companyDao;
	
    public Integer saveFund(Fund fund){
       return (Integer)getSqlMapClientTemplate().insert("insertFund",fund);
    }


    public void saveOneOnOneMeetingRequest(OneOnOneMeetingRequest oneOnOneMeetingRequest){
        getSqlMapClientTemplate().insert("insertOneOnOneMeetingRequest",oneOnOneMeetingRequest);
    }
    
    public List<Fund> getFundList() {
    	return (List<Fund>)getSqlMapClientTemplate().queryForList("getFundList");
    }
    
    public List<Timeframe> getFundTime(Integer fundid) {
    	return (List<Timeframe>)getSqlMapClientTemplate().queryForList("getFundTime", fundid);
    }
    
    public List<Company> get1on1CompanyList(Integer fundid) {
    	return (List<Company>)getSqlMapClientTemplate().queryForList("get1on1CompanyList", fundid);
    }
    
    public Fund getFundById (Integer fundid) {
    	return (Fund)getSqlMapClientTemplate().queryForObject("getFundById", fundid);
    }
    
    public void save1on1Company(List<Integer> companyids, Integer fundid) {
    
    	getSqlMapClientTemplate().delete("deleteAll1on1Company", fundid);
    	
    	Fund fund = this.getFundById(fundid);
    
    	Iterator<Integer> companyItr = companyids.iterator();
    	
    	while (companyItr.hasNext()) {
    		
    		OneOnOneMeetingRequest request = new OneOnOneMeetingRequest(fund, companyDao.getCompanyById(companyItr.next()));
    		getSqlMapClientTemplate().insert("insert1on1Company", request);
    	}
    	
    	
    }
    
    public void saveTimeFund(List<Integer> timeids, Integer fundid) {
    	
    	getSqlMapClientTemplate().delete("deleteFundAllTimeslots", fundid);
    	
    	Fund fund = this.getFundById(fundid);
    
    	Iterator<Integer> timeItr = timeids.iterator();
    	
    	while (timeItr.hasNext()) {
    		
    		FundAvailability fundAvail = new FundAvailability(fund, timeItr.next());
    		getSqlMapClientTemplate().insert("insertFundTimeslot", fundAvail);
    	}
    }

    
}
