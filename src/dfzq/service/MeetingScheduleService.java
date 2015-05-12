package dfzq.service;

import dfzq.dao.ScheduleDAO;
import dfzq.dao.TimeframeDao;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 15-5-4
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MeetingScheduleService {

    private Map<Integer, Integer> locationMap = new HashMap<Integer, Integer>();

    @Autowired
    private TimeframeDao timeframeDao;
    @Autowired
    private ScheduleDAO scheduleDAO;

    public void scheduleMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest, Integer timeFrameId) {
        Schedule schedule = new Schedule();
        schedule.setColor("-1");

        Company company = oneOnOneMeetingRequest.getCompany();

        Fund fund = oneOnOneMeetingRequest.getFund();

        schedule.setSubject(oneOnOneMeetingRequest.getCompany().getName() + "(" + oneOnOneMeetingRequest.getCompany().getContact() + ")" + "-" + oneOnOneMeetingRequest.getFund().getFundName() + "(" + oneOnOneMeetingRequest.getFund().getContactor() + ")");
        schedule.setDescription(schedule.getSubject());
        Timeframe timeFrame = timeframeDao.getTimeFrame(timeFrameId);

        schedule.setStartTime(timeFrame.getStartTime());
        schedule.setEndTime(timeFrame.getEndTime());

        schedule.setLocation(getLocation(timeFrameId) + "");
        String attendee = generateAttendee(company.getId(), fund.getId());
        schedule.setAttendee(attendee);
        schedule.setType(Schedule.ONE_ON_ONE);
        scheduleDAO.addDetailedSchedule(schedule);


    }

    public void scheduleMeeting(OneManyMeetingRequest oneManyMeetingRequest, Integer timeFrameId) {
        Schedule schedule = new Schedule();
        schedule.setColor("-1");

        Company company = oneManyMeetingRequest.getCompany();


        schedule.setSubject(oneManyMeetingRequest.getCompany().getName() + "(" + oneManyMeetingRequest.getCompany().getContact() + ")");
        schedule.setDescription(schedule.getSubject());
        Timeframe timeFrame = timeframeDao.getTimeFrame(timeFrameId);

        schedule.setStartTime(timeFrame.getStartTime());
        schedule.setEndTime(timeFrame.getEndTime());

        schedule.setLocation(getLocation(timeFrameId) + "");
        String attendee = generateAttendee(company.getId(), 0);
        schedule.setAttendee(attendee);
        schedule.setType(Schedule.ONE_MANY);
        scheduleDAO.addDetailedSchedule(schedule);


    }

    public void scheduleMeeting(SmallGroupMeetingRequest smallGroupMeetingRequest, Integer timeFrameId) {
        Schedule schedule = new Schedule();
        schedule.setColor("-1");

        Company company = smallGroupMeetingRequest.getCompany();


        schedule.setSubject(smallGroupMeetingRequest.getCompany().getName() + "(" + smallGroupMeetingRequest.getCompany().getContact() + ")");
        schedule.setDescription(schedule.getSubject());
        Timeframe timeFrame = timeframeDao.getTimeFrame(timeFrameId);

        schedule.setStartTime(timeFrame.getStartTime());
        schedule.setEndTime(timeFrame.getEndTime());

        schedule.setLocation(getLocation(timeFrameId) + "");
        String attendee = generateAttendee(company.getId(),smallGroupMeetingRequest.getFundIds() );
        schedule.setAttendee(attendee);
        schedule.setType(Schedule.SMALL_GROUP);
        scheduleDAO.addDetailedSchedule(schedule);


    }

    public String generateAttendee(Integer companyId, Integer fundId) {


        return "," + "f" + fundId + "," + "c" + companyId + ",";
    }

    public String generateAttendee(Integer companyId, List<Integer> fundIds) {
        StringBuffer sb = new StringBuffer();
        for (Integer fundId : fundIds) {
            sb.append(",f");
            sb.append(fundId);
        }
        sb.append(",c");
        sb.append(companyId);
        sb.append(",");

        return sb.toString();
    }

    private int getLocation(Integer timeFrameId) {
        Integer locationId = locationMap.get(timeFrameId);
        if (locationId == null) {
            locationMap.put(timeFrameId, 1);
            return 1;
        } else {
            locationMap.put(timeFrameId, locationId + 1);
            return locationId;
        }

    }

}
