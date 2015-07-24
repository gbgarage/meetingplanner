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
    @Autowired
    private OneOnOneMeetingArrangeService oneOnOneMeetingArrangeService;
    @Autowired
    private MeetingScheduleService meetingScheduleService;
    @Autowired
    private OneManyMeetingArrangeService oneManyMeetingArrangeService;
    @Autowired
    private SmallGroupMeetingArrangeService smallGroupMeetingArrangeService;


    public void cauclatingArrangeMeeting() {

        arrangeMeetingDao.clearArrangement();
        meetingScheduleService.cleanLocalMap();
        for (int i = 1; i <= 2; i++) {
            int[] morningtimeFrames = timeframeDao.getTimeFrameByRegion(i + "a");
            int[] afternoonTimeFrames = timeframeDao.getTimeFrameByRegion(i + "b");
            if (morningtimeFrames == null | afternoonTimeFrames == null) {
                continue;

            }
            arrageMeeting(morningtimeFrames, afternoonTimeFrames);
            arrageMeeting(afternoonTimeFrames, morningtimeFrames);
            int[] wholeDayArray = combineTwoArrays(morningtimeFrames, afternoonTimeFrames);
            arrageWholeMeeting(wholeDayArray);


        }


    }

    private int[] combineTwoArrays(int[] morningtimeFrames, int[] afternoonTimeFrames) {
        int[] result = new int[morningtimeFrames.length + afternoonTimeFrames.length];
        int i = 0;
        for (int value : morningtimeFrames) {
            result[i++] = value;
        }

        for (int value : afternoonTimeFrames) {
            result[i++] = value;

        }
        return result;
    }

    public void fundCancel(Integer fundId, Integer companyId, Integer timeframeId) {
        OneOnOneMeetingRequest oneOnOneMeetingRequest = arrangeMeetingDao.getArrangeMeeting(fundId, companyId);
        cancelMeeting(oneOnOneMeetingRequest, Status.FUND_CANCEL);

        Company company = companyDao.getCompanyById(companyId);


        List<OneOnOneMeetingRequest> interestingFundRequests = arrangeMeetingDao.findInterestingFunds(companyId, timeframeId);
        for (OneOnOneMeetingRequest interestingFundRequest : interestingFundRequests) {
            Fund fund = fundDao.getFundById(interestingFundRequest.getFundId());
            if (fundDao.checkTimeFrameAvailable(fund, timeframeId)) {

                interestingFundRequest.setTimeFrameId(timeframeId);
                interestingFundRequest.setStatus(Status.FUND_CANCEL_RESCHEDULE);
                interestingFundRequest.setFund(fund);
                interestingFundRequest.setCompany(company);
                arrangeMeeting(interestingFundRequest);
                return;
            }


        }

        //todo promote one to many meeting


    }

    public void fundReschedule(Integer fundId, int[] beforeTimeFrame, int[] afterTimeFrame) {
        List<OneOnOneMeetingRequest> meetingRequests = arrangeMeetingDao.get1on1List(fundId, beforeTimeFrame);

        Fund fund = fundDao.getFundById(fundId);
        for (OneOnOneMeetingRequest oneOnOneMeetingRequest : meetingRequests) {
            cancelMeeting(oneOnOneMeetingRequest, Status.FUND_CANCEL);


        }

        List<Company> companies = fundDao.get1on1CompanyList(fundId);


        for (Company company : companies) {
            for (int timeFrameId : afterTimeFrame) {
                if (checkAvailable(fund, company, timeFrameId)) {
                    OneOnOneMeetingRequest oneOnOneMeetingRequest = arrangeMeetingDao.getArrangeMeeting(fundId, company.getId());
                    oneOnOneMeetingRequest.setTimeFrameId(timeFrameId);
                    oneOnOneMeetingRequest.setCompany(company);
                    oneOnOneMeetingRequest.setFund(fund);
                    oneOnOneMeetingRequest.setStatus(Status.FUND_RESCHEDULE);
                    arrangeMeeting(oneOnOneMeetingRequest);


                }

            }
        }


    }

    private boolean checkAvailable(Fund fund, Company company, int timeFrameId) {
        return companyDao.checkTimeFrameAvailable(company, timeFrameId) && fundDao.checkTimeFrameAvailable(fund, timeFrameId);
    }


    public void companyCancel(Integer companyId, Integer timeFrameId) {

        OneOnOneMeetingRequest oneOnOneMeetingRequest = arrangeMeetingDao.getArrangeMeetingByCompanyAndTime(companyId, timeFrameId);

        cancelMeeting(oneOnOneMeetingRequest, Status.COMPANY_CANCEL);

        Fund fund = fundDao.getFundById(oneOnOneMeetingRequest.getFundId());

        List<Company> companies = fundDao.getInterestedAndUnScheduleCompanies(oneOnOneMeetingRequest.getFundId());

        for (Company company : companies) {
            if (checkAvailable(fund, company, timeFrameId)) {
                OneOnOneMeetingRequest pendingRequest = arrangeMeetingDao.getArrangeMeeting(fund.getId(), company.getId());
                pendingRequest.setStatus(Status.COMPANY_CANCEL_RESCHEDULE);
                pendingRequest.setTimeFrameId(timeFrameId);
                pendingRequest.setFund(fund);
                pendingRequest.setCompany(company);
                arrangeMeeting(pendingRequest);
                return;
            }

        }


    }

    private void arrangeMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest) {
        arrangeMeetingDao.updateMeetingStatus(oneOnOneMeetingRequest);
        meetingScheduleService.scheduleMeeting(oneOnOneMeetingRequest, oneOnOneMeetingRequest.getTimeFrameId(), null);
    }

    private void cancelMeeting(OneOnOneMeetingRequest oneOnOneMeetingRequest, int status) {
        oneOnOneMeetingRequest.setStatus(status);
        arrangeMeetingDao.updateMeetingStatus(oneOnOneMeetingRequest);
        scheduleDAO.deleteSchedule(meetingScheduleService.generateAttendee(oneOnOneMeetingRequest.getCompanyId(), oneOnOneMeetingRequest.getFundId()));


    }


    private void arrageMeeting(int[] timeFrames, int[] otherTimeFrames) {

        Resource resource = dataLoadService.loadResource(timeFrames, otherTimeFrames);

        arrageConflictCompany(resource, resource.getConflictCompany());
        arrageConflictFunds(resource.getConflictFunds());
        arrageOtherCompany(resource.getOtherCompanies());


    }


    private void arrageWholeMeeting(int[] timeFrames) {

        Resource resource = dataLoadService.loadAvailableWholeDayCompanies(timeFrames);

        arrageConflictCompany(resource, resource.getConflictCompany());
        arrageConflictFunds(resource.getConflictFunds());
        arrageOtherCompany(resource.getOtherCompanies());


    }

//    private int[] getOtherTimeFrames(int[] timeFrames) {
//        return new int[]{4, 5, 6, 7};  //To change body of created methods use File | Settings | File Templates.
//    }


    private void arrageConflictCompany(Resource resource, Set<Company> conflictCompany) {
        for (Company company : conflictCompany) {
            List<MeetingRequest> requests = new ArrayList<MeetingRequest>(company.getOneOnOneMeetingRequestList());
            for (int i = 0; i < requests.size(); i++) {
                MeetingRequest meetingRequest = requests.get(i);
                if (meetingRequest instanceof OneOnOneMeetingRequest) {
                    OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                    oneOnOneMeetingArrangeService.arrangeConflictCompany(resource, oneOnOneMeetingRequest, requests, i);
                } else if (meetingRequest instanceof OneManyMeetingRequest) {
                    OneManyMeetingRequest oneManyMeetingRequest = (OneManyMeetingRequest) meetingRequest;
                    oneManyMeetingArrangeService.arrangeConflictCompany(resource, oneManyMeetingRequest, requests, i);


                } else if (meetingRequest instanceof SmallGroupMeetingRequest) {
                    SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;
                    smallGroupMeetingArrangeService.arrangeConflictCompany(resource, smallGroupMeetingRequest, requests, i);


                }


            }

        }


    }

    public void arrageConflictFunds(Set<Fund> conflictFunds) {
        for (Fund fund : conflictFunds) {
            for (MeetingRequest meetingRequest : fund.getOneOnOneMeetingRequests()) {
                if (meetingRequest instanceof OneOnOneMeetingRequest) {
                    OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                    oneOnOneMeetingArrangeService.arrageConflictFund(oneOnOneMeetingRequest);

                } else if (meetingRequest instanceof SmallGroupMeetingRequest) {
                    SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;
                    smallGroupMeetingArrangeService.arrageConflictFund(smallGroupMeetingRequest);

                }
            }
        }
    }

    private void arrageOtherCompany(Set<Company> otherCompanies) {
        for (Company company : otherCompanies) {
            for (MeetingRequest meetingRequest : company.getOneOnOneMeetingRequestList()) {
                if (meetingRequest instanceof OneOnOneMeetingRequest) {
                    OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                    oneOnOneMeetingArrangeService.arrageOtherCompany(oneOnOneMeetingRequest);
                } else if (meetingRequest instanceof OneManyMeetingRequest) {
                    OneManyMeetingRequest oneManyMeetingRequest = (OneManyMeetingRequest) meetingRequest;
                    oneManyMeetingArrangeService.arrageOtherCompany(oneManyMeetingRequest);


                } else if (meetingRequest instanceof SmallGroupMeetingRequest) {
                    SmallGroupMeetingRequest smallGroupMeetingRequest = (SmallGroupMeetingRequest) meetingRequest;
                    smallGroupMeetingArrangeService.arrageOtherCompany(smallGroupMeetingRequest);


                }

            }

        }

    }


}
