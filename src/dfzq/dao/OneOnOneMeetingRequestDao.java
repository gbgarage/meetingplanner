package dfzq.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.common.BaseDao;

import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;

@Component
public class OneOnOneMeetingRequestDao extends BaseDao{

    public List<OneOnOneMeetingRequest> getMeetReqList() {
    	return (List<OneOnOneMeetingRequest>)getSqlMapClientTemplate().queryForList("getMeetReqList");
    }
}
