package dfzq.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dfzq.constants.Status;
import dfzq.model.MeetingRequest;
import dfzq.model.OneOnOneMeetingRequest;

import org.springframework.stereotype.Component;

import com.common.BaseDao;

@Component
public class OneOnOneMeetingRequestDao extends BaseDao{

    public List<MeetingRequest> getMeetReqList() {
    	return (List<MeetingRequest>)getSqlMapClientTemplate().queryForList("getMeetReqList");
    }
    
    public List<MeetingRequest> getMeetReqListForFund(Integer fundid) {
    	return (List<MeetingRequest>)getSqlMapClientTemplate().queryForList("getMeetReqListForFund", fundid);
    }
 
    public OneOnOneMeetingRequest getMeetReqForFundCompany(Integer fundid, Integer companyid) {
    	
        Map map = new HashMap();
        map.put("fundid", fundid);
        map.put("companyid", companyid);
        
    	return (OneOnOneMeetingRequest)getSqlMapClientTemplate().queryForObject("get11MeetReqListForFundCompany", map);
    }
    
    public void updateMeetingRequestStatus(Integer fundid, Integer companyid, Integer status, Integer lunchtimeid) {
    	
        Map map = new HashMap();
        map.put("fundid", fundid);
        map.put("companyid", companyid);
        map.put("status", status);
        map.put("lunchtimeid", lunchtimeid);
    	
    	getSqlMapClientTemplate().update("updateMeetingRequestStatus", map);
    }    
    
    
}
