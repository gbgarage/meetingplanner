package dfzq.service;

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

      public void arrageMeeting(int[] timeFrames){
          int[] otherTimeFrames = getOtherTimeFrames(timeFrames);
          Resource resource = dataLoadService.loadResource(timeFrames, otherTimeFrames);

          arrageConflictCompany(resource.getConflictCompany());
//          arrageConflictFunds(resource.getConflictFunds());
//          arrageOtherCompany(resource.getOtherCompanies());







      }

    private int[] getOtherTimeFrames(int[] timeFrames) {
            return new int[]{4,5,6,7};  //To change body of created methods use File | Settings | File Templates.
    }




    private void arrageConflictCompany(Set<Company> conflictCompany) {
        for(Company company: conflictCompany){
            for(OneOnOneMeetingRequest oneOnOneMeetingRequest : company.getOneOnOneMeetingRequestList()){
                int timeFrameId = companyDao.getNextAvailableTimeFrame(company);

            }

        }



    }

    private void arrageConflictFunds(List<Fund> conflictFunds) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void arrageOtherCompany(List<Company> otherCompanies) {
    }

}
