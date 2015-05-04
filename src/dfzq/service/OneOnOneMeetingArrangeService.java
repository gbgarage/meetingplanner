package dfzq.service;

import dfzq.constants.Status;
import dfzq.dao.ArrangeMeetingDao;
import dfzq.dao.CompanyDao;
import dfzq.dao.ScheduleDAO;
import dfzq.dao.TimeframeDao;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 15-5-4
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OneOnOneMeetingArrangeService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ArrangeMeetingDao arrangeMeetingDao;
    @Autowired
    private MeetingScheduleService meetingScheduleService;

    public void arrangeConflictCompany(Resource resource, OneOnOneMeetingRequest oneOnOneMeetingRequest, List<MeetingRequest> requests, int index) {

        Fund fund = oneOnOneMeetingRequest.getFund();
        Company company = oneOnOneMeetingRequest.getCompany();
        Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, fund);
        if (timeFrameId != null) {
            String fundName = fund.getFundName();

            for (int j = index; j < company.getOneOnOneMeetingRequestList().size(); j++) {
                MeetingRequest request = requests.get(j);
                if (request instanceof OneOnOneMeetingRequest) {
                    OneOnOneMeetingRequest request1 = (OneOnOneMeetingRequest) request;
                    fund = request1.getFund();
                    if (fundName.equals(request1.getFund().getFundName())) {
                        arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_COMPANY_AND_ARRAGED);
                        meetingScheduleService.scheduleMeeting(oneOnOneMeetingRequest, timeFrameId);

                        fund.decreaseAvailbility();
                        if (fund.isConflict()) {
                            resource.addConflictFund(fund);
                        }
                    }
                }
            }
        } else {
            arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, Status.CONFLICT_COMPANY_AND_NOT_ARRAGED);
        }


    }


    public void arrageConflictFund(OneOnOneMeetingRequest oneOnOneMeetingRequest) {

        Company company = oneOnOneMeetingRequest.getCompany();
        Fund fund = oneOnOneMeetingRequest.getFund();

        Integer timeFrameId = companyDao.getNextAvailableTimeFrame(company, fund);
        if (timeFrameId != null) {
            arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, timeFrameId, Status.CONFLICT_FUND_AND_ARRAGED);
            meetingScheduleService.scheduleMeeting(oneOnOneMeetingRequest, timeFrameId);


        } else {
            arrangeMeetingDao.saveArrangement(oneOnOneMeetingRequest, Status.CONFLICT_FUND_AND_NOT_ARRAGED);
        }


    }


}
