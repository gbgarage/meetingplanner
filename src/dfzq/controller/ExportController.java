package dfzq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dfzq.dao.MeetingRequestExcelDao;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
	
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public String test(Model model,
//			@RequestParam(value="id", defaultValue="1") int id
//			) {
//		return "input/config_fund";
//	}
	
	@Autowired
	MeetingRequestExcelDao mrxDao;
	
	@RequestMapping(value = "/excelschedule", method = RequestMethod.GET)
	public ModelAndView exportExcelSchedule(
			@RequestParam(value="usertype", defaultValue="1") String userType,
			@RequestParam(value="id", required=false) Integer id,
			Model model
			) {
	
		if (userType.equals("fundmanager")) 
			return new ModelAndView ("MeetingRequestExcel", "mrxItemList", mrxDao.getFundRequest(id));
		
		if (userType.equals("companymanager"))
			return new ModelAndView ("MeetingRequestExcel", "mrxItemList", mrxDao.getCompanyRequest(id));
		
		if (userType.equals("organizer"))
			return new ModelAndView ("MeetingRequestExcel", "mrxItemList", mrxDao.getAllRequest());
				
		return null;
		
	}
	
}
