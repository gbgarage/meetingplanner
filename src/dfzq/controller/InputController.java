package dfzq.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
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
import dfzq.dao.TimeframeDao;
import dfzq.model.Company;
import dfzq.model.Fund;
import dfzq.model.Timeframe;

@Controller
@RequestMapping(value = "/input")
public class InputController {

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	FundDao fundDao;
	
	@Autowired
	TimeframeDao timeframeDao;
	
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
			Model model) {
		List <Fund> totalFunds = fundDao.getFundList();
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
			Model model) {
		List <Company> totalCompanies = companyDao.getCompanyList();
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
		List <Company> totalCompanies = fundDao.get1on1CompanyList(fundid);
		
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalCompanies.size()) toIndex = totalCompanies.size();
		
		List <Company> returnCompanies = totalCompanies.subList(fromIndex, toIndex);
		DataList dl = new DataList(totalCompanies.size(),returnCompanies);
		
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
	public int save1on1Company(@PathVariable("id") Integer fundid, @RequestBody List<Integer> companyids, Model model) {
		fundDao.save1on1Company(companyids, fundid);
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
}

class DataList <T> {
	private int total;
	private List<T> data;
	DataList(int total, List<T> data) {
		this.total = total;
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public List<T> getData() {
		return data;
	}
}
