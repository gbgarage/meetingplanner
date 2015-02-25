package dfzq.dao;

import com.common.BaseDao;
import dfzq.constants.Status;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-8
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ArrangeMeetingDao extends BaseDao {
	
	public void clearArrangement() {
		getSqlMapClientTemplate().update("updateOneOnOneMeetingRequest");
		getSqlMapClientTemplate().delete("deleteTblSchedule");
	}

    public void saveArrangement(OneOnOneMeetingRequest oneOnOneMeetingRequest, int timeFrameId, int status) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("status", status);
        parameters.put("timeFrameId", timeFrameId);
        parameters.put("fund_id",oneOnOneMeetingRequest.getFundId());
        parameters.put("company_id",oneOnOneMeetingRequest.getCompanyId());

        getSqlMapClientTemplate().update("updateArrangement", parameters);


    }

    public void saveArrangement(OneOnOneMeetingRequest oneOnOneMeetingRequest, int status) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("status", status);
        parameters.put("timeFrameId", null);
        parameters.put("fund_id",oneOnOneMeetingRequest.getFundId());
        parameters.put("company_id",oneOnOneMeetingRequest.getCompanyId());

        getSqlMapClientTemplate().update("updateArrangement", parameters);


    }

    public OneOnOneMeetingRequest getArrangeMeeting(Integer fundId, Integer companyId) {
        Map map = new HashMap();
        map.put("fundId", fundId);
        map.put("companyId", companyId);

        return (OneOnOneMeetingRequest) getSqlMapClientTemplate().queryForObject("getArrangeMeeting", map);



    }

    public List<OneOnOneMeetingRequest> findInterestingFunds(Integer companyId, Integer timeframeId) {

        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("timeFrameId", timeframeId);
        map.put("status", Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);

        return (List<OneOnOneMeetingRequest>) getSqlMapClientTemplate().queryForList("findInterestingFunds", map);


    }

    public void updateMeetingStatus(OneOnOneMeetingRequest oneOnOneMeetingRequest) {
        getSqlMapClientTemplate().update("updateMeetingStatus", oneOnOneMeetingRequest);
    }

    public List<OneOnOneMeetingRequest> get1on1List(Integer fundId, int[] beforeTimeFrame) {
        Map map = new HashMap();
        map.put("fundId", fundId);
        map.put("timeFrame", convertArrayToINString(beforeTimeFrame));

        return getSqlMapClientTemplate().queryForList("get1on1List", map);
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
}
