package dfzq.dao;

import com.common.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import dfzq.model.Schedule;

public class ScheduleDAO extends BaseDao {
	
   public int addSchedule(Schedule s) {
	   int i = (Integer)getSqlMapClientTemplate().insert("addSchedule", s);
	   return i;
   }
   
   public void removeSchedule(int id) {
	   Schedule s = new Schedule();
	   s.setId(id);
	   getSqlMapClientTemplate().delete("removeSchedule", s);
   }
   
   public void updateSchedule(Schedule s) {
	   getSqlMapClientTemplate().update("updateSchedule", s);
   }
   
   @SuppressWarnings("unchecked")
public List<Schedule> listScheduleByRange(Timestamp st ,Timestamp et) {
	   HashMap<String, Timestamp> m = new HashMap<String, Timestamp>();
	   m.put("st", st);
	   m.put("et", et);
	   return (List<Schedule>)getSqlMapClientTemplate().queryForList("listScheduleByRange", m);
   }
   
   
   
   public Schedule listSchedule(int id) {
	   HashMap<String, Integer> m = new HashMap<String, Integer>();
	   m.put("id", id);
	   return (Schedule)getSqlMapClientTemplate().queryForObject("listSchedule", m);
   }
   
   public int addDetailedSchedule(Schedule s) {
	   int i = (Integer)getSqlMapClientTemplate().insert("addDetailedSchedule", s);
	   return i;
   }
   
   
   public void updateDetailedSchedule(Schedule s) {
	   getSqlMapClientTemplate().update("updateDetailedSchedule", s);
   }
}
