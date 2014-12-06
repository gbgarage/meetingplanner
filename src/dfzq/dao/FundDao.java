package dfzq.dao;

import com.common.BaseDao;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
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

    public Integer saveFund(Fund fund){
       return (Integer)getSqlMapClientTemplate().insert("insertFund",fund);
    }


    public void saveOneOnOneMeetingRequest(OneOnOneMeetingRequest oneOnOneMeetingRequest){
        getSqlMapClientTemplate().insert("insertOneOnOneMeetingRequest",oneOnOneMeetingRequest);


    }
}
