package dfzq.service;

import dfzq.dao.CompanyDao;
import dfzq.dao.FundDao;
import dfzq.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-12-4
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataLoadService {

    @Autowired
    private FundDao fundDao;
    @Autowired
    private CompanyDao companyDao;


    public void saveFund(Fund fund) {
        Integer id = fundDao.saveFund(fund);
        fund.setId(id);
        for (MeetingRequest meetingRequest : fund.getOneOnOneMeetingRequests()) {
            if (meetingRequest != null) {
                Company company = meetingRequest.getCompany();

                company = companyDao.saveOrGetId(company);
                meetingRequest.setCompany(company);
                fundDao.saveOneOnOneMeetingRequest(meetingRequest);
            }
        }

    }

    public void saveCompany(Company company, List<Integer> availableTimes) {
        company = companyDao.saveOrGetId(company);

        for (int availableTime : availableTimes) {
            Availability availability = new Availability(company, availableTime);
            companyDao.saveCompanyAvailablility(availability);


        }


    }


    public Resource loadResource(int[] timeFrames, int[] otherTimeFrames) {
        List<MeetingRequest> meetingRequests = companyDao.loadAvailableCompanies(timeFrames, otherTimeFrames);
        return createResource(meetingRequests);


    }

    private Resource createResource(List<MeetingRequest> meetingRequests) {
        Resource resource = new Resource();
        for (MeetingRequest meetingRequest : meetingRequests) {
            Company company = meetingRequest.getCompany();
            if (company.isConflict()) {
                resource.addConflictCompany(company);
            } else {
                resource.addNoneConflictCompany(company);
            }
            if (meetingRequest instanceof OneOnOneMeetingRequest) {
                OneOnOneMeetingRequest oneOnOneMeetingRequest = (OneOnOneMeetingRequest) meetingRequest;
                Fund fund = oneOnOneMeetingRequest.getFund();
                if (fund.isConflict()) {
                    resource.addConflictFund(fund);
                }
            }

        }
        return resource;
    }

    public Resource loadAvailableWholeDayCompanies(int[] timeFrames) {
        List<MeetingRequest> meetingRequests = companyDao.loadAvailableWholeDayCompanies(timeFrames);
        return createResource(meetingRequests);


    }
}
