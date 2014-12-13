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
}
