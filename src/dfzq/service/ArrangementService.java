package dfzq.service;

import dfzq.constants.Status;
import dfzq.dao.*;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private FundDao fundDao;

    private Map<Integer, Integer> locationMap = new HashMap<Integer, Integer>();

    public void cauclatingArrangeMeeting() {

        arrangeMeetingDao.clearArrangement();
        for (int i = 1; i <= 2; i++) {
            int[] morningtimeFrames = timeframeDao.getTimeFrameByRegion(i + "a");
            int[] afternoonTimeFrames = timeframeDao.getTimeFrameByRegion(i + "b");
            if (morningtimeFrames == null | afternoonTimeFrames == null) {
                continue;

            }
            arrageMeeting(morningtimeFrames, afternoonTimeFrames);
            arrageMeeting(afternoonTimeFrames, morningtimeFrames);
            arrageWholeMeeting(morningtimeFrames, afternoonTimeFrames);


        }


    }

    public void fundCancel(Long fundId, Integer companyId, Integer timeframeId){
        OneOnOneMeetingRequest oneOnOneMeetingRequest =  arrangeMeetingDao.getArrangeMeeting(fundId, companyId, timeframeId);
        cancelMeeting(oneOnOneMeetingRequest);

        Company company = companyDao.getCompanyById(companyId);


       List<OneOnOneMeetingRequest> interestingFundRequests = arrangeMeetingDao.findInterestingFunds(companyId, timeframeId);
       for(OneOnOneMeetingRequest interestingFundRequest: interestingFundRequests){
           Fund fund = fundDao.getFundById(interestingFundRequest.getFundId());
           if(fundDao.checkTimeFrameAvailable(fund, timeframeId)){

               interestingFundRequest.setTimeFrameId(timeframeId);
               interestingFundRequest.setStatus(Status.CONFLICT_COMPANY_AND_ARRAGED);
               interestingFundRequest.setFund(fund);
               interestingFundRequest.setCompany(company);
               arrangeMeeting(interestingFundRequest);
               return;
           }


       }

        //todo promote small group meeting


    }

    private void arrangeMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest) {
       arrangeMeetingDao.updateMeetingStatus(oneOnOneMeetingRequest);
       scheduleMeeting(oneOnOneMeetingRequest, oneOnOneMeetingRequest.getTimeFrameId());
    }

    private void cancelMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest) {
        oneOnOneMeetingRequest.setStatus(Status.FUND_CANCEL);
        arrangeMeetingDao.updateMeetingStatus(oneOnOneMeetingRequest);
        scheduleDAO.deleteSchedule(generateAttendee(oneOnOneMeetingRequest.getCompanyId(), oneOnOneMeetingRequest.getFundId()));


        
    }


    private void arrageMeeting(int[] timeFrames, int[] otherTimeFrames) {

        Resource resource = dataLoadService.loadResource(timeFrames, otherTimeFrames);

        arrageConflictCompany(resource, resource.getConflictCompany());
        arrageConflictFunds(resource.getConflictFunds());
        arrageOtherCompany(resource.getOtherCompanies());


    }


    private void arrageWholeMeeting(int[] timeFrames, int[] otherTimeFrames) {

        Resource resource = dataLoadService.loadAvailableWholeDayCompanies(timeFrames, otherTimeFrames);

        arrageConflictCompany(resource, resource.getConflictCompany());
        arrageConflictFunds(resource.getConflictFunds());
        arrageOtherCompany(resource.getOtherCompanies());


    }

    private int[] getOtherTimeFrames(int[] timeFrames) {
        return new int[]{4, 5, 6, 7};  //To change body of created methods use File | Settings | File Templates.
    }


    private void arrageConflictCompany(Resource resource, Set<Company> conflictCompany) {
        for (Company company : conflictCompany) {
            List<OneOnOneMeetingRequest> requests = new ArrayList<OneOnOneMeetingRequest>(company.getOneOnOneMeetingRequestList());
            for (int i = 0; i < requests.size(); i++) {
                OneOnOneMeetingRequest oneOnOneMeetingRequest = requests.get(i);
                Fund fund = oneOnOneMeetingRequest.getFund();
                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, fund);
                if (timeFrameId != null) {
                    String fundName = fund.getFundName();
                    for (int j = i + 1; j < requests.size(); j++) {
                        fund = requests.get(j).getFund();
                        if (fundName.equals(requests.get(j).getFund().getFundName())) {
                            arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_COMPANY_AND_ARRAGED);
                            scheduleMeeting(oneOnOneMeetingRequest, timeFrameId);

                            fund.decreaseAvailbility();
                            if (fund.isConflict()) {
                                resource.addConflictFund(fund);
                            }
                        }
                    }
                } else {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);
                }

            }

        }


    }

    private void arrageConflictFunds(Set<Fund> conflictFunds) {
        for (Fund fund : conflictFunds) {
            for (OneOnOneMeetingRequest oneOnOneMeetingRequest : fund.getOneOnOneMeetingRequests()) {
                Company company = oneOnOneMeetingRequest.getCompany();

                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, fund);
                if (timeFrameId != null) {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_FUND_AND_ARRAGED);
                    scheduleMeeting(oneOnOneMeetingRequest, timeFrameId);


                } else {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, Status.CONFLICT_FUND_AND_NOT_ARRAGED);
                }


            }


        }

    }

    private void arrageOtherCompany(Set<Company> otherCompanies) {
        for (Company company : otherCompanies) {
            for (OneOnOneMeetingRequest oneOnOneMeetingRequest : company.getOneOnOneMeetingRequestList()) {
                Fund fund = oneOnOneMeetingRequest.getFund();
                Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, fund);
                if (timeFrameId != null) {
                    arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.NOT_CONFLICT);
                    scheduleMeeting(oneOnOneMeetingRequest, timeFrameId);


                }

            }

        }

    }

    private void scheduleMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest, Integer timeFrameId) {
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
        scheduleDAO.addDetailedSchedule(schedule);


    }

    private String generateAttendee(Integer  companyId, Integer fundId) {
        return "," + "f" +fundId + "," + "c" + companyId + ",";
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
