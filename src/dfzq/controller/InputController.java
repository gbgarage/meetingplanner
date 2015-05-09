package dfzq.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dfzq.dao.CompanyDao;
import dfzq.dao.FundDao;
import dfzq.dao.OneOnOneMeetingRequestDao;
import dfzq.dao.TimeframeDao;
import dfzq.model.Availability;
import dfzq.model.Company;
import dfzq.model.CompanyChangeRow;
import dfzq.model.DataList;
import dfzq.model.Fund;
import dfzq.model.MeetingRequest;
import dfzq.model.OneOnOneMeetingRequest;
import dfzq.model.Timeframe;
import dfzq.model.FundChangeRow;
import dfzq.service.ArrangementService;

@Controller
@RequestMapping(value = "/input")
public class InputController {

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	FundDao fundDao;
	
	@Autowired
	TimeframeDao timeframeDao;
	
	@Autowired
	OneOnOneMeetingRequestDao oneOnoneMeetingRequestDao;
	
    @Autowired
    ArrangementService arrangementService;
	
	//fund config home page
	@RequestMapping(value = "/configfund")
	public String configFundPage(Locale locale, Model model) {
		return "input/config_fund";
	}	
	
	//return all fund list in JSON
	@ResponseBody
	@RequestMapping(value = "/getFundList")
	public DataList getFundList(
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			@RequestParam(value="key", defaultValue="%") String fundName,
			Model model) {
		List <Fund> totalFunds = fundDao.getFundList(fundName);
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalFunds.size()) toIndex = totalFunds.size();
		
		List <Fund> returnFunds = totalFunds.subList(fromIndex, toIndex);
		
		DataList dl = new DataList(totalFunds.size(),returnFunds);
		return dl;
	}	

	//return all company list in JSON, DAO DONE
	@ResponseBody
	@RequestMapping(value = "/getCompanyList")
	public DataList getCompanyList(
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			@RequestParam(value="key", defaultValue="%") String companyName,
			Model model) {
		List <Company> totalCompanies = companyDao.getCompanyList(companyName);
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalCompanies.size()) toIndex = totalCompanies.size();
		
		List <Company> returnCompanies = totalCompanies.subList(fromIndex, toIndex);
		
		DataList dl = new DataList(totalCompanies.size(),returnCompanies);
		
		return dl;
	}		
	
	//return company list which the fund wants 1v1 in JSON
	@ResponseBody
	@RequestMapping(value = "/get1on1CompanyList/{id}")
	public DataList get1on1CompanyList(@PathVariable("id") Integer fundid, 
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			Model model) {
//		List <Company> totalCompanies = fundDao.get1on1CompanyList(fundid);
//		
//		int fromIndex = pageIndex*pageSize;
//		int toIndex = pageIndex*pageSize+pageSize;
//		
//		if (toIndex > totalCompanies.size()) toIndex = totalCompanies.size();
//		
//		List <Company> returnCompanies = totalCompanies.subList(fromIndex, toIndex);
//		DataList dl = new DataList(totalCompanies.size(),returnCompanies);
//		
//		return dl;
		List <MeetingRequest> totalMeetReq = oneOnoneMeetingRequestDao.getMeetReqListForFund(fundid);
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalMeetReq.size()) toIndex = totalMeetReq.size();
		
		List <MeetingRequest> returnMeetReqs = totalMeetReq.subList(fromIndex, toIndex);
		
		DataList dl = new DataList(totalMeetReq.size(),returnMeetReqs);
		return dl;
		
	}		
	
	//return all time slots in JSON
	@ResponseBody
	@RequestMapping(value = "/getTimeframeList")
	public DataList getTimeframeList(
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			Model model) {
		List <Timeframe> totalTimeframes = timeframeDao.getTimeframeList();
		
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalTimeframes.size()) toIndex = totalTimeframes.size();
		
		List <Timeframe> returnTimeframes = totalTimeframes.subList(fromIndex, toIndex);
		DataList dl = new DataList(totalTimeframes.size(),returnTimeframes);
		
		return dl;
	}		
	
	//return current selected time slots for fund
	@ResponseBody
	@RequestMapping(value = "/getFundTime/{id}")
	public DataList getFundTime(@PathVariable("id") Integer fundid, 
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			Model model) {
		List <Timeframe> totalTimeframes = fundDao.getFundTime(fundid);
		
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalTimeframes.size()) toIndex = totalTimeframes.size();
		
		List <Timeframe> returnTimeframes = totalTimeframes.subList(fromIndex, toIndex);
		DataList dl = new DataList(totalTimeframes.size(),returnTimeframes);
				
		return dl;
	}	
	
	//company config home pages
	@RequestMapping(value = "/configcompany", method = RequestMethod.GET)
	public String configCompanyPage(Locale locale, Model model) {
		return "input/config_company";
	}	

	//return current selected time slots for company
	@ResponseBody
	@RequestMapping(value = "/getCompanyTime/{id}")
	public DataList getCompanyTime(@PathVariable("id") Integer companyid, 
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			Model model) {
		List <Timeframe> totalTimeframes = companyDao.getCompanyTime(companyid);
		
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalTimeframes.size()) toIndex = totalTimeframes.size();		
		
		List <Timeframe> returnTimeframes = totalTimeframes.subList(fromIndex, toIndex);
		DataList dl = new DataList(totalTimeframes.size(),returnTimeframes);
				
		return dl;
	}	
	
	//save 1v1 meeting request
	@ResponseBody
	@RequestMapping(value = "/save1on1Company/{id}")
	public int save1on1Company(@PathVariable("id") Integer fundid, @RequestBody OneOnOneMeetingRequest[] companyids, Model model) {

		List<OneOnOneMeetingRequest> companylist = Arrays.asList(companyids);
		List<OneOnOneMeetingRequest> oneOnoneCompanyList = new ArrayList<OneOnOneMeetingRequest>();
		List<OneOnOneMeetingRequest> smallCompanyList = new ArrayList<OneOnOneMeetingRequest>();

    	Iterator<OneOnOneMeetingRequest> mrItr = companylist.iterator();
    	
    	while (mrItr.hasNext()) {
    		OneOnOneMeetingRequest mr = mrItr.next();
    		
    		if (mr.isSmall() == true) smallCompanyList.add(mr);
    		else oneOnoneCompanyList.add(mr);

    	}
		
		fundDao.save1on1Company(oneOnoneCompanyList, fundid);
		fundDao.saveSmallCompany(smallCompanyList, fundid);
		return 1;
	}	
	
	//save availability for fund
	@ResponseBody
	@RequestMapping(value = "/saveTimeFund/{id}")
	public int saveTimeFund(@PathVariable("id") Integer fundid, @RequestBody List<Integer> timeids, Model model) {
		fundDao.saveTimeFund(timeids, fundid);
		return 1;
	}	
	
	//save availability for company
	@ResponseBody
	@RequestMapping(value = "/saveTimeCompany/{id}")
	public int saveTimeCompany(@PathVariable("id") Integer companyid, @RequestBody List<Integer> timeids, Model model) {
		companyDao.saveTimeCompany(timeids, companyid);
		return 1;
	}	
	
	//CRUD fund page
	@RequestMapping(value = "/crudFund")
	public String crudFundPage(Model model) {
		return "input/crud_fund";
	}	
	
	//CRUD fund
	@ResponseBody
	@RequestMapping(value = "/crudFund/submit")
	public int crudFundSubmit(@RequestBody FundChangeRow[] rows, Model model) {
		List<FundChangeRow> rowslist = Arrays.asList(rows);
		fundDao.saveFundChanges(rowslist);
		return 1;
	}	
	
	//CRUD company page
	@RequestMapping(value = "/crudCompany")
	public String crudCompanyPage(Model model) {
		return "input/crud_company";
	}	
	
	//CRUD company
	@ResponseBody
	@RequestMapping(value = "/crudCompany/submit")
	public int crudCompanySubmit(@RequestBody CompanyChangeRow[] rows, Model model) {
		List<CompanyChangeRow> rowslist = Arrays.asList(rows);
		companyDao.saveCompanyChanges(rowslist);
		return 1;
	}	
	
	//calendar configuration page
	@RequestMapping(value = "/configCalendar")
	public String configCalendarPage(Model model) {
		return "input/config_calendar";
	}	
	
	
	//calendar calculate page
	@RequestMapping(value = "/calCalendar")
	public String calCalendarPage(Model model) {
		return "input/calculate_calendar";
	}	
	
	//calendar calculate 
	@RequestMapping(value = "/calCalendar/calculate")
	public String calCalendar(Model model) {

		arrangementService.cauclatingArrangeMeeting();
		return "show_all_schedule";
	}	
	

	//return all one_on_one_meeting request list in JSON
	@ResponseBody
	@RequestMapping(value = "/getMeetReqList")
	public DataList getMeetReqList(
			@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize, 
			Model model) {
		List <MeetingRequest> totalMeetReq = oneOnoneMeetingRequestDao.getMeetReqList();
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalMeetReq.size()) toIndex = totalMeetReq.size();
		
		List <MeetingRequest> returnMeetReqs = totalMeetReq.subList(fromIndex, toIndex);
		
		DataList dl = new DataList(totalMeetReq.size(),returnMeetReqs);
		return dl;
	}	
	
	//calendar change page
	@RequestMapping(value = "/changeCalendar")
	public String changeCalendarPage(Model model) {
		return "input/change_calendar";
	}	

	//change calendar - cancel meeting for fund
	@RequestMapping(value = "/cancelMeetingForFund/{fundid}/{timeid}")
	public String cancelMeetingForFund(@PathVariable("fundid") Integer fundid, @PathVariable("timeid") Integer timeid, Model model) {
		
		arrangementService.fundCancel(fundid, 1, fundid);
		return "show_all_schedule";
	}	
	
	//change calendar - AM to PM for fund 
	@RequestMapping(value = "/AMtoPMForFund/{id}")
	public String AMtoPMForFund(@PathVariable("id") Integer fundid, Model model) {
		int beforeTimeFrame[] = {1,2,3};
		int afterTimeFrame[] = {4,5,6,7};
		arrangementService.fundReschedule(fundid, beforeTimeFrame, afterTimeFrame);
		return "show_all_schedule";
	}		
	
	//change calendar - cancel meeting for company
	@RequestMapping(value = "/cancelMeetingForCompany/{companyid}/{timeid}")
	public String cancelMeetingForCompany(@PathVariable("companyid") Integer companyid, @PathVariable("timeid") Integer timeid, Model model) {
		arrangementService.companyCancel(companyid, timeid);
		return "show_all_schedule";
	}	
	
	//change calendar - AM to PM for company 
	@RequestMapping(value = "/AMtoPMForCompany/{id}")
	public String AMtoPMForCompany(@PathVariable("id") Integer companyid, Model model) {
		int beforeTimeFrame[] = {1,2,3};
		int afterTimeFrame[] = {4,5,6,7};
		
		//no company reschedule available so far, placeholder!!!
		arrangementService.fundReschedule(companyid, beforeTimeFrame, afterTimeFrame);
		return "show_all_schedule";
	}			
	
}




