package dfzq.service;

import dfzq.constants.Status;
import dfzq.dao.ArrangeMeetingDao;
import dfzq.dao.CompanyDao;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
import dfzq.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


                }

            }

        }

    }
}
