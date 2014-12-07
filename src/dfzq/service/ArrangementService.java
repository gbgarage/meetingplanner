package dfzq.service;

import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

      public void arrageMeeting(int[] timeFrames){
          Resource resource = dataLoadService.loadResource(timeFrames);


          arrageConflictCompany(resource.getConflictCompany());
          arrageConflictFunds(resource.getConflictFunds());
          arrageOtherCompany(resource.getOtherCompanies());







      }





    private void arrageConflictCompany(List<Company> conflictCompany) {
    }

    private void arrageConflictFunds(List<Fund> conflictFunds) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void arrageOtherCompany(List<Company> otherCompanies) {
    }

}
