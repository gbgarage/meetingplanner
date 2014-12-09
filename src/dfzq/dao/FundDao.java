package dfzq.dao;

import com.common.BaseDao;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-5
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@Component
public class FundDao extends BaseDao{

    public Integer saveFund(Fund fund){
       return (Integer)getSqlMapClientTemplate().insert("insertFund",fund);
    }


    public void saveOneOnOneMeetingRequest(OneOnOneMeetingRequest oneOnOneMeetingRequest){
        getSqlMapClientTemplate().insert("insertOneOnOneMeetingRequest",oneOnOneMeetingRequest);


    }

    public Fund getFundById(Integer fundId) {
        return (Fund)getSqlMapClientTemplate().queryForObject("getFundById",fundId);
    }


    public boolean checkTimeFrameAvailable(Fund fund, Integer availableTimeFrameId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fund_id", fund.getId());
        parameters.put("availableTimeFrameId", availableTimeFrameId);

        OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) getSqlMapClientTemplate().queryForObject("getOneOnOneMeetingRequestByFundIdAndtimeFrameId", parameters);
        if (oneOnOneMeetingRequest == null) {
            return true;
        } else {
            return false;
        }
    }
}
