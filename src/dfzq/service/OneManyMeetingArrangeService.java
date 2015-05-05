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
public class OneManyMeetingArrangeService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ArrangeMeetingDao arrangeMeetingDao;
    @Autowired
    private MeetingScheduleService meetingScheduleService;

    public void arrangeConflictCompany(Resource resource, OneManyMeetingRequest oneManyMeetingRequest, List<MeetingRequest> requests, int index) {


        Company company = oneManyMeetingRequest.getCompany();
        Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, null);
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(oneManyMeetingRequest, timeFrameId, Status.CONFLICT_COMPANY_AND_ARRAGED);
            meetingScheduleService.scheduleMeeting(oneManyMeetingRequest, timeFrameId);


        } else {
            arrangeMeetingDao.saveArrangement(oneManyMeetingRequest, Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);
        }


    }

    public void arrageOtherCompany(OneManyMeetingRequest oneManyMeetingRequest) {

        Company company = oneManyMeetingRequest.getCompany();
        Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, null);
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(oneManyMeetingRequest, timeFrameId, Status.NOT_CONFLICT);
            meetingScheduleService.scheduleMeeting(oneManyMeetingRequest, timeFrameId);
        }
    }


}
