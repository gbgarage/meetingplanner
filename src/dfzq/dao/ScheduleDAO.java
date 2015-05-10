package dfzq.dao;

import com.common.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dfzq.model.ConflictResult;
import dfzq.model.Schedule;
import dfzq.model.ScheduleByDVT;

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
public List<Schedule> listScheduleByRange(Timestamp st ,Timestamp et, String sAttendee) {
	   HashMap<String, Object> m = new HashMap<String, Object>();
	   m.put("st", st);
	   m.put("et", et);
	   m.put("attendee", "%,"+sAttendee.toLowerCase()+",%");
	   System.out.println("%,"+sAttendee.toLowerCase()+",%");
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
   
   @SuppressWarnings("unchecked")
   public List<ScheduleByDVT> listScheduleByDVT() {
   	   HashMap<String, Timestamp> m = new HashMap<String, Timestamp>();
       return (List<ScheduleByDVT>)getSqlMapClientTemplate().queryForList("listScheduleByDVT", m);
      }
   
   public List<ConflictResult> listConflicts() {
       return (List<ConflictResult>)getSqlMapClientTemplate().queryForList("listConflicts");
      }

    public void deleteSchedule(String attendee) {
        getSqlMapClientTemplate().delete("deleteScheduleByAttendee", attendee);
    }
    
    public List<Schedule> getschedule(int companyid) {

 	   return (List<Schedule>)getSqlMapClientTemplate().queryForList("getMeetingForCompany", "%,c" + companyid + ",%");
    }
    
    public void addFundToMeeting(int fund_id, int company_id, int meeting_id) {
    	
        Map map = new HashMap();
        map.put("fundid", fund_id);
        map.put("companyid", company_id);
        map.put("meetingid", meeting_id);
        String Attendee = (String) getSqlMapClientTemplate().queryForObject("getAttendeeForMeeting", meeting_id);     
        map.put("attendee", Attendee + "f" + fund_id + ",");
        getSqlMapClientTemplate().update("addFundToMeeting", map);
    }
    
     
}
