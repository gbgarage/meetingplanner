package dfzq.dao;

import java.util.List;
import com.common.BaseDao;

import org.springframework.stereotype.Component;

import dfzq.model.MeetingRequestExcelItem;

@Component
public class MeetingRequestExcelDao extends BaseDao{
	
    public List<MeetingRequestExcelItem> getFundRequest(int fundid) {
    	return (List<MeetingRequestExcelItem>)getSqlMapClientTemplate().queryForList("getFundRequest", fundid);
    }
    
    public List<MeetingRequestExcelItem> getCompanyRequest(int companyid) {
    	return (List<MeetingRequestExcelItem>)getSqlMapClientTemplate().queryForList("getCompanyRequest", companyid);
    }

}
