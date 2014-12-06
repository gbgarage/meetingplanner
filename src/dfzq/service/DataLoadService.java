package dfzq.service;

import dfzq.dao.CompanyDao;
import dfzq.dao.FundDao;
import dfzq.model.Availability;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.OneOnOneMeetingRequest;
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
        for (OneOnOneMeetingRequest oneOnOneMeetingRequest : fund.getOneOnOneMeetingRequests()) {
            if (oneOnOneMeetingRequest != null) {
                Company company = oneOnOneMeetingRequest.getCompany();

                company = companyDao.saveOrGetId(company);
                oneOnOneMeetingRequest.setCompany(company);
                fundDao.saveOneOnOneMeetingRequest(oneOnOneMeetingRequest);
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


}
