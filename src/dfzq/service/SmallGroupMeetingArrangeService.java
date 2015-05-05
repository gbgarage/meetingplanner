package dfzq.service;

import dfzq.constants.Status;
import dfzq.dao.ArrangeMeetingDao;
import dfzq.dao.CompanyDao;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 15-5-4
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SmallGroupMeetingArrangeService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ArrangeMeetingDao arrangeMeetingDao;
    @Autowired
    private MeetingScheduleService meetingScheduleService;

    public void arrangeConflictCompany(Resource resource, SmallGroupMeetingRequest smallGroupMeetingRequest, List<MeetingRequest> requests, int index) {

        List<Fund> funds = smallGroupMeetingRequest.getFunds();
        Company company = smallGroupMeetingRequest.getCompany();
        Integer timeFrameId = companyDao.getNextAvailableTimeFrameForSmallGroup(company, funds);
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest, timeFrameId, Status.CONFLICT_COMPANY_AND_ARRAGED);
            meetingScheduleService.scheduleMeeting(smallGroupMeetingRequest, timeFrameId);
            for (Fund fund : funds) {
                fund.decreaseAvailbility();
                if (fund.isConflict()) {
                    resource.addConflictFund(fund);
                }
            }


        } else {
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest, Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);
        }


    }


    public void arrageConflictFund(SmallGroupMeetingRequest smallGroupMeetingRequest) {

        Company company = smallGroupMeetingRequest.getCompany();


        Integer timeFrameId = companyDao.getNextAvailableTimeFrameForSmallGroup(company, smallGroupMeetingRequest.getFunds());
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest, timeFrameId, Status.CONFLICT_FUND_AND_ARRAGED);
            meetingScheduleService.scheduleMeeting(smallGroupMeetingRequest, timeFrameId);


        } else {
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest, Status.CONFLICT_FUND_AND_NOT_ARRAGED);
        }


    }


    public void arrageOtherCompany(SmallGroupMeetingRequest smallGroupMeetingRequest) {
        List<Fund> funds = smallGroupMeetingRequest.getFunds();
        Company company = smallGroupMeetingRequest.getCompany();
        Integer timeFrameId = companyDao.getNextAvailableTimeFrameForSmallGroup(company, funds);
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest, timeFrameId, Status.NOT_CONFLICT);
            meetingScheduleService.scheduleMeeting(smallGroupMeetingRequest, timeFrameId);
        }else{
            arrangeMeetingDao.saveArrangement(smallGroupMeetingRequest,Status.CONFLICT_FUND_AND_NOT_ARRAGED);
        }
    }


}
