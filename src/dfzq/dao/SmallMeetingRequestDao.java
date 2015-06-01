package dfzq.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.common.BaseDao;

@Component
public class SmallMeetingRequestDao extends BaseDao{ 
	
    public void updateMeetingRequestStatus(Integer fundid, Integer companyid, Integer status, Integer meetingid) {
    	
        Map map = new HashMap();
        map.put("fundid", fundid);
        map.put("companyid", companyid);
        map.put("status", status);
        map.put("meetingid", meetingid);
    	
    	getSqlMapClientTemplate().update("updateSmallMeetingRequestStatus", map);
    }    

}
