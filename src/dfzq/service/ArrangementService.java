package dfzq.service;

import dfzq.constants.Status;
import dfzq.dao.ArrangeMeetingDao;
import dfzq.dao.CompanyDao;
import dfzq.dao.ScheduleDAO;
import dfzq.dao.TimeframeDao;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 飞
 * Date: 14-12-5
 * Time: 下午10:22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ArrangementService {
    @Autowired
    private DataLoadService dataLoadService;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ArrangeMeetingDao arrangeMeetingDao;
    @Autowired
    private TimeframeDao timeframeDao;
    @Autowired
    private ScheduleDAO scheduleDAO;

    private Map<Integer, Integer> locationMap = new HashMap<Integer,Integer>();

    public void arrageMeeting(int[] timeFrames) {
        int[] otherTimeFrames = getOtherTimeFrames(timeFrames);
        Resource resource = dataLoadService.loadResource(timeFrames, otherTimeFrames);

        arrageConflictCompany(resource, resource.getConflictCompany());
        arrageConflictFunds(resource.getConflictFunds());
        arrageOtherCompany(resource.getOtherCompanies());


    }

    private int[] getOtherTimeFrames(int[] timeFrames) {
        return new int[]{4, 5, 6, 7};  //To change body of created methods use File | Settings | File Templates.
    }


    private void arrageConflictCompany(Resource resource, Set<Company> conflictCompany) {
        for (Company company : conflictCompany) {
            for (OneOnOneMeetingRequest oneOnOneMeetingRequest : company.getOneOnOneMeetingRequestList()) {
                Fund fund = oneOnOneMeetingRequest.getFund();
                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company,fund);
                if (timeFrameId != null) {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_COMPANY_AND_ARRAGED);
                    scheduleMeeting(oneOnOneMeetingRequest,timeFrameId);

                    fund.decreaseAvailbility();
                    if (fund.isConflict()) {
                        resource.addConflictFund(fund);
                    }
                } else {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest,  Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);
                }

            }

        }


    }

    private void arrageConflictFunds(Set<Fund> conflictFunds) {
        for (Fund fund : conflictFunds) {
            for (OneOnOneMeetingRequest oneOnOneMeetingRequest : fund.getOneOnOneMeetingRequests()) {
                Company company = oneOnOneMeetingRequest.getCompany();

                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company,fund);
                if (timeFrameId != null) {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_FUND_AND_ARRAGED);
                    scheduleMeeting(oneOnOneMeetingRequest,timeFrameId);


                } else {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest,  Status.CONFLICT_FUND_AND_NOT_ARRAGED);
                }


            }


        }

    }

    private void arrageOtherCompany(Set<Company> otherCompanies) {
        for (Company company : otherCompanies) {
            for (OneOnOneMeetingRequest oneOnOneMeetingRequest : company.getOneOnOneMeetingRequestList()) {
                Fund fund = oneOnOneMeetingRequest.getFund();
                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company,fund);
                if (timeFrameId != null) {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.NOT_CONFLICT);
                    scheduleMeeting(oneOnOneMeetingRequest,timeFrameId);


                }

            }

        }

    }

    private void scheduleMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest,Integer timeFrameId){
        Schedule schedule  = new Schedule();
        schedule.setColor("-1");

        Company company = oneOnOneMeetingRequest.getCompany();

        Fund fund = oneOnOneMeetingRequest.getFund();

        schedule.setSubject(oneOnOneMeetingRequest.getCompany().getName()+"("+oneOnOneMeetingRequest.getCompany().getContact()+")"+"-"+ oneOnOneMeetingRequest.getFund().getFundName()+"("+oneOnOneMeetingRequest.getFund().getContactor()+")");
        schedule.setDescription(schedule.getSubject());
        Timeframe timeFrame =timeframeDao.getTimeFrame(timeFrameId);

        schedule.setStartTime(timeFrame.getStartTime());
        schedule.setEndTime(timeFrame.getEndTime());

        schedule.setLocation(getLocation(timeFrameId)+"");
        String attendee =","+"f"+fund.getId()+","+ "c"+company.getId()+",";
        schedule.setAttendee(attendee);
        scheduleDAO.addDetailedSchedule(schedule)  ;







    }

    private int getLocation(Integer timeFrameId) {
        Integer locationId = locationMap.get(timeFrameId);
        if(locationId==null){
            locationMap.put(timeFrameId,1);
            return 1;
        }else{
            locationMap.put(timeFrameId,locationId+1);
            return locationId;
        }

    }

}
