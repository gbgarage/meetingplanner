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
 * Date: 14-12-8
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ArrangeMeetingDao extends BaseDao {

    public void saveArrangement(OneOnOneMeetingRequest oneOnOneMeetingRequest, int timeFrameId, int status) {
        Map<String, Integer> parameters = new HashMap<String, Integer>();
        parameters.put("status", status);
        parameters.put("timeFrameId", timeFrameId);
        parameters.put("fund_id",oneOnOneMeetingRequest.getFundId());
        parameters.put("company_id",oneOnOneMeetingRequest.getCompanyId());

        getSqlMapClientTemplate().update("updateArrangement", parameters);


    }

}
