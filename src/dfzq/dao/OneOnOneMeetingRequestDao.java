package dfzq.dao;

import java.util.List;

import dfzq.model.MeetingRequest;
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
   
}
