package dfzq.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.common.BaseDao;

import dfzq.model.Fund;
import dfzq.model.Timeframe;

@Component
public class TimeframeDao extends BaseDao {
	
    public List<Timeframe> getTimeframeList() {
    	return (List<Timeframe>)getSqlMapClientTemplate().queryForList("getTimeframeList");
    }

    public Timeframe getTimeFrame(Integer timeFrameId) {
        return (Timeframe)getSqlMapClientTemplate().queryForObject("getTimeFrameById", timeFrameId);
    }

    public int[] getTimeFrameByRegion(String region) {
        List<Timeframe> list =  (List<Timeframe>)getSqlMapClientTemplate().queryForList("getTimeFrameByRegion", region);
        if(list.isEmpty()){
            return null;
        }
        int[] timeFrameIds = new int[list.size()];
        for(int i = 0 ; i<list.size(); i++){
            timeFrameIds[i]=list.get(i).getId();

        }
        return timeFrameIds;

    }
    
    public List<Timeframe> getLunchTimeList() {
    	return (List<Timeframe>)getSqlMapClientTemplate().queryForList("getLunchTimeList");
    }
}
