package dfzq.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import dfzq.dao.CompanyDao;
import dfzq.dao.FundDao;
import dfzq.dao.OneOnOneMeetingRequestDao;
import dfzq.dao.ScheduleDAO;
import dfzq.dao.TimeframeDao;
import dfzq.model.ConflictResult;
import dfzq.model.DataList;
import dfzq.model.MeetingRequest;
import dfzq.model.OneOnOneMeetingRequest;
import dfzq.model.Schedule;
import dfzq.model.ScheduleByDVT;
import dfzq.model.Timeframe;
import dfzq.service.MeetingScheduleService;
import dfzq.util.DateFormatter;
import dfzq.util.StringUtil;

@Controller
public class ScheduleController {
	
	@Autowired
	TimeframeDao timeframeDao;
	
	@Autowired
	FundDao fundDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	OneOnOneMeetingRequestDao oneononemeetingrequestDao;
	
	
	@Autowired
	MeetingScheduleService meetingScheduleService;

	//private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("ScheduleController");

	public JSONObject addSchedule(String sScheduleStartTime,
			String sScheduleEndTime, String sScheduleTitle,
			String sIsAllDayEvent, String sAttendee) {

		logger.info("addSchedule()");
		logger.info("   sScheduleStartTime=" + sScheduleStartTime);
		logger.info("   sScheduleEndTime=" + sScheduleEndTime);
		logger.info("   sScheduleTitle=" + sScheduleTitle);
		logger.info("   sIsAllDayEvent=" + sIsAllDayEvent);
		logger.info("   sAttendee=" + sAttendee);
		logger.info("");

		JSONObject jsonResult = new JSONObject();
		try {
			ScheduleDAO sd = getScheduleDAO();
			Schedule params = new Schedule();
			
			//System.out.println(sScheduleTitle);
			//System.out.println(StringUtil.toUTF8String(sScheduleTitle));
			
			params.setSubject(sScheduleTitle);
			//params.setSubject(StringUtil.toUTF8String(sScheduleTitle));
			params.setStartTime(new java.sql.Timestamp(DateFormatter
					.js2Datetime(sScheduleStartTime).getTime()));
			params.setEndTime(new java.sql.Timestamp(DateFormatter.js2Datetime(
					sScheduleEndTime).getTime()));
			params.setIsAllDayEvent(Integer.parseInt(sIsAllDayEvent));
			if (sAttendee!=null) {
			    params.setAttendee(","+sAttendee+",");
			} else {
				params.setAttendee(null);
			}
			
			long id = sd.addSchedule(params);
			if (id >= 0) {
				jsonResult.put("IsSuccess", true);
				jsonResult.put("Msg", "add success");
				jsonResult.put("Data", id);
			} else {
				jsonResult.put("IsSuccess", false);
				jsonResult.put("Msg", "sql error");
			}
		} catch (Exception e) {
			jsonResult.put("IsSuccess", false);
			jsonResult.put("Msg", e.getMessage());
		}
		return jsonResult;
	}

	public JSONObject listSchedule(String sShowDate, String sViewType, String sAttendee) {
		logger.info("listSchedule()");
		logger.info("   sShowDate=" + sShowDate);
		logger.info("   sViewType=" + sViewType);
		logger.info("   sAttendee=" + sAttendee);
		logger.info("");

		java.util.Calendar c = java.util.Calendar.getInstance();
		Date jTime = new Date();
		try {
			jTime = DateFormatter.js2Date(sShowDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(jTime);

		Date st = new Date(), et = new Date();

		if (sViewType.equals("month")) {
			c.set(java.util.Calendar.DATE, +1);
			st = c.getTime();
			c.add(java.util.Calendar.MONTH, +1);
			c.add(java.util.Calendar.SECOND, -1);
			et = c.getTime();
		} else if (sViewType.equals("week")) {
			c.setTime(jTime);
			c.get(java.util.Calendar.DAY_OF_WEEK);
			int iDayOfWeek = (c.get(java.util.Calendar.DAY_OF_WEEK) + 5) % 7 + 1;
			c.add(java.util.Calendar.DATE, 1 - iDayOfWeek);
			st = c.getTime();
			c.add(java.util.Calendar.DATE, +7);
			c.add(java.util.Calendar.SECOND, -1);
			et = c.getTime();
		} else if (sViewType.equals("day")) {
			c.setTime(jTime);
			st = c.getTime();
			c.add(java.util.Calendar.DATE, +1);
			c.add(java.util.Calendar.SECOND, -1);
			et = c.getTime();
		}

		return listScheduleByRange(st, et, sAttendee);
	}

	private JSONObject listScheduleByRange(Date st, Date et, String sAttendee) {
		logger.info("listScheduleByRange()");
		logger.info("   st:" + st);
		logger.info("   et:" + et);
		logger.info("   sAttendee:" + sAttendee);

		JSONObject jsonResult = new JSONObject();
		JSONArray events = new JSONArray();

		try {

			ScheduleDAO sd = getScheduleDAO();

			List<Schedule> ls = (List<Schedule>) sd.listScheduleByRange(
					new java.sql.Timestamp(st.getTime()),
					new java.sql.Timestamp(et.getTime()),
					sAttendee);

			for (int i = 0; i < ls.size(); i++) {
				JSONArray event = new JSONArray();
				Schedule s = (Schedule) ls.get(i);

				event.put(s.getId() + "");
				//event.put(StringUtil.toUTF8String(s.getSubject()));
				
				event.put(s.getSubject());
				event.put(DateFormatter.toJsDatetimeString(s.getStartTime()));
				event.put(DateFormatter.toJsDatetimeString(s.getEndTime()));
				event.put(s.getIsAllDayEvent() + "");
				event.put(0);// more than one day event
				event.put(0);// Recurring event,
				event.put(s.getColor() + "");
				event.put(1);// editable
				event.put(s.getLocation() + "");
				event.put("");// $attends
				events.put(event);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			jsonResult.put("error", e.getMessage());
		}

		jsonResult.put("events", events);
		jsonResult.put("issort", true);
		jsonResult.put("start", DateFormatter.toJsDatetimeString(st));
		jsonResult.put("end", DateFormatter.toJsDatetimeString(et));
		jsonResult.put("error", (String) null);

		return jsonResult;
	}

	public JSONObject updateSchedule(String sScheduleId,
			String sScheduleStartTime, String sScheduleEndTime) {
		logger.info("updateSchedule()");
		logger.info("   sScheduleId=" + sScheduleId);
		logger.info("   sScheduleStartTime=" + sScheduleStartTime);
		logger.info("   sScheduleEndTime=" + sScheduleEndTime);
		logger.info("");

		JSONObject jsonResult = new JSONObject();
		try {

			ScheduleDAO sd = getScheduleDAO();

			Schedule params = new Schedule();
			params.setStartTime(new java.sql.Timestamp(DateFormatter
					.js2Datetime(sScheduleStartTime).getTime()));
			params.setEndTime(new java.sql.Timestamp(DateFormatter.js2Datetime(
					sScheduleEndTime).getTime()));
			params.setId(Integer.parseInt(sScheduleId));

			sd.updateSchedule(params);

			jsonResult.put("IsSuccess", true);
			jsonResult.put("Msg", "Succefully");

		} catch (Exception e) {
			jsonResult.put("IsSuccess", false);
			jsonResult.put("Msg", e.getMessage());
		}
		return jsonResult;
	}

	public JSONObject removeSchedule(String sScheduleId) {
		logger.info("removeSchedule()");
		logger.info("   sScheduleId=" + sScheduleId);
		logger.info("");

		JSONObject jsonResult = new JSONObject();
		try {
			ScheduleDAO sd = getScheduleDAO();
			sd.removeSchedule(Integer.parseInt(sScheduleId));

			jsonResult.put("IsSuccess", true);
			jsonResult.put("Msg", "Succefully");

		} catch (Exception e) {
			jsonResult.put("IsSuccess", false);
			jsonResult.put("Msg", e.getMessage());
		}
		return jsonResult;
	}

	public JSONObject updateDetailedSchedule(String sScheduleId,
			String sStartTime, String sEndTime, String sSubject,
			int iIsAllDayEvent, String sDescription, String sLocation,
			String sColorValue, String sTimeZone) {
		logger.info("updateDetailedSchedule()");
		logger.info("   sScheduleId=" + sScheduleId);
		logger.info("   sStartTime=" + sStartTime);
		logger.info("   sEndTime=" + sEndTime);
		logger.info("   sSubject=" + sSubject);
		logger.info("   iIsAllDayEvent=" + iIsAllDayEvent);
		logger.info("   sDescription=" + sDescription);
		logger.info("   sLocation=" + sLocation);
		logger.info("   sColorValue=" + sColorValue);
		logger.info("   sTimeZone=" + sTimeZone);
		logger.info("");

		JSONObject jsonResult = new JSONObject();
		try {
			ScheduleDAO sd = getScheduleDAO();

			Schedule params = new Schedule();
			params.setStartTime(new java.sql.Timestamp(DateFormatter
					.js2Datetime(sStartTime).getTime()));
			params.setEndTime(new java.sql.Timestamp(DateFormatter.js2Datetime(
					sEndTime).getTime()));

			params.setSubject(sSubject);
			params.setIsAllDayEvent(iIsAllDayEvent);
			params.setDescription(sDescription);
			params.setLocation(sLocation);
			params.setColor(sColorValue);
			params.setId(Integer.parseInt(sScheduleId));

			sd.updateDetailedSchedule(params);

			jsonResult.put("IsSuccess", true);
			jsonResult.put("Msg", "Succefully");

		} catch (Exception e) {
			jsonResult.put("IsSuccess", false);
			jsonResult.put("Msg", e.getMessage());
		}
		return jsonResult;

	}

	public JSONObject addDetailedSchedule(String sStartTime, String sEndTime,
			String sSubject, int iIsAllDayEvent, String sDescription,
			String sLocation, String sColorValue, String sTimeZone, String sAttendee) {
		logger.info("addDetailedSchedule()");
		logger.info("   sStartTime=" + sStartTime);
		logger.info("   sEndTime=" + sEndTime);
		logger.info("   sSubject=" + sSubject);
		logger.info("   iIsAllDayEvent=" + iIsAllDayEvent);
		logger.info("   sDescription=" + sDescription);
		logger.info("   sLocation=" + sLocation);
		logger.info("   sColorValue=" + sColorValue);
		logger.info("   sTimeZone=" + sTimeZone);
		logger.info("   sAttendee=" + sAttendee);
		logger.info("");

		JSONObject jsonResult = new JSONObject();
		try {
			ScheduleDAO sd = getScheduleDAO();

			Schedule params = new Schedule();
			params.setSubject(sSubject);
			params.setStartTime(new java.sql.Timestamp(DateFormatter
					.js2Datetime(sStartTime).getTime()));
			params.setEndTime(new java.sql.Timestamp(DateFormatter.js2Datetime(
					sEndTime).getTime()));
			params.setIsAllDayEvent(iIsAllDayEvent);
			params.setDescription(sDescription);
			params.setLocation(sLocation);
			params.setColor(sColorValue);
			params.setColor(sAttendee);

			long id = sd.addDetailedSchedule(params);
			if (id >= 0) {
				jsonResult.put("IsSuccess", true);
				jsonResult.put("Msg", "add success");
				jsonResult.put("Data", id);
			} else {
				jsonResult.put("IsSuccess", false);
				jsonResult.put("Msg", "sql error");
			}

		} catch (Exception e) {
			jsonResult.put("IsSuccess", false);
			jsonResult.put("Msg", e.getMessage());
		}

		return jsonResult;
	}

	public ScheduleDAO getScheduleDAO() {
		WebApplicationContext context = ContextLoader
				.getCurrentWebApplicationContext();
		return (ScheduleDAO) context.getBean("scheduleDAO");
	}

	@RequestMapping(value = "/schedule", method = RequestMethod.POST)
	public @ResponseBody String responseSchedule(
			@RequestParam(value = "method", required = false) String method,

			@RequestParam(value = "CalendarStartTime", required = false) String sScheduleStartTime,
			@RequestParam(value = "CalendarEndTime", required = false) String sScheduleEndTime,
			@RequestParam(value = "CalendarTitle", required = false) String sScheduleTitle,
			@RequestParam(value = "IsAllDayEvent", required = false) String sIsAllDayEvent,
			@RequestParam(value = "showdate", required = false) String sShowDate,
			@RequestParam(value = "viewtype", required = false) String sViewType,

			@RequestParam(value = "calendarId", required = false) String sScheduleId,

			@RequestParam(value = "stpartdate", required = false) String stPartDate,
			@RequestParam(value = "stparttime", required = false) String stPartTime,
			@RequestParam(value = "etpartdate", required = false) String etPartDate,
			@RequestParam(value = "etparttime", required = false) String etPartTime,

			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "Subject", required = false) String sSubject,
			@RequestParam(value = "Description", required = false) String sDescription,
			@RequestParam(value = "Location", required = false) String sLocation,
			@RequestParam(value = "colorvalue", required = false) String sColorvalue,
			@RequestParam(value = "timezone", required = false) String sTimezone,
			
			@RequestParam(value = "attendee", required = false) String sAttendee
			) {

		JSONObject jsonResult = null;

		logger.info(method);

		if (method.equals("add")) {
			jsonResult = addSchedule(sScheduleStartTime, sScheduleEndTime,
					sScheduleTitle, sIsAllDayEvent, sAttendee);
		} else if (method.equals("list")) {
			jsonResult = listSchedule(sShowDate, sViewType, sAttendee);
		} else if (method.equals("update")) {
			jsonResult = updateSchedule(sScheduleId, sScheduleStartTime,
					sScheduleEndTime);
		} else if (method.equals("remove")) {
			jsonResult = removeSchedule(sScheduleId);
		} else if (method.equals("adddetails")) {
			String st = stPartDate + " " + stPartTime;
			String et = etPartDate + " " + etPartTime;
			if (id != null) {
				jsonResult = updateDetailedSchedule(id, st, et, sSubject,
						(sIsAllDayEvent != null) ? 1 : 0, sDescription,
						sLocation, sColorvalue, sTimezone);
			} else {
				jsonResult = addDetailedSchedule(st, et, sSubject,
						(sIsAllDayEvent != null) ? 1 : 0, sDescription,
						sLocation, sColorvalue, sTimezone, sAttendee);
			}
		}

		String result = StringUtil.toUnicodeFormat(jsonResult.toString());
		System.out.println(result);
		logger.info(result);

		return result;
	}

	@RequestMapping(value = "/listdvt", method = RequestMethod.POST)
	public @ResponseBody String listScheduleByDVT() {
		logger.info("listScheduleByDVT()");
		logger.info("");
		ScheduleDAO scheduleDAO = getScheduleDAO();
		List<ScheduleByDVT> listOfScheduleItems = scheduleDAO.listScheduleByDVT();
		JSONArray resultJsonArray = new JSONArray();
		String currentDateVenueCombination = "";
		JSONObject dateVenueJsonObject = new JSONObject();
		
		for (int i=0;i<listOfScheduleItems.size();i++) {
			ScheduleByDVT scheduleResultItem = listOfScheduleItems.get(i);
			if (!currentDateVenueCombination.equals(scheduleResultItem.getMeetingDate()+scheduleResultItem.getVenue())) {
				if (!currentDateVenueCombination.equals("")) {
					//output
					resultJsonArray.put(dateVenueJsonObject);
				}
				//create a new combination
				currentDateVenueCombination = scheduleResultItem.getMeetingDate()+scheduleResultItem.getVenue();
				
				//set up the key Date and Venue in the new JSONObject
				dateVenueJsonObject = new JSONObject();
				dateVenueJsonObject.put("MeetingDate", scheduleResultItem.getMeetingDate());
				dateVenueJsonObject.put("MeetingVenue", scheduleResultItem.getVenue());
			}
			
			// for all the time put T0900, Subject into the defined dateVenueJsonObject
			dateVenueJsonObject.put("T"+scheduleResultItem.getMeetingTime(), 
					scheduleResultItem.getSubject()==null?"":scheduleResultItem.getSubject()
					);
		}
		//in case any left during the loop
		if (!currentDateVenueCombination.equals("")) {
			resultJsonArray.put(dateVenueJsonObject);
		}
		//replace Unicode chars in the JSON string
		return StringUtil.toUnicodeFormat(resultJsonArray.toString());//.replaceAll("\\\\\\\\", "\\\\");
	}
	
	
	/*, method = RequestMethod.GET*/
	@RequestMapping(value = "/listconflicts",  method = RequestMethod.POST)
	public @ResponseBody String listConflicts() {
		logger.info("listConflicts()");
		logger.info("");
		
		ScheduleDAO scheduleDAO = getScheduleDAO();
		List<ConflictResult> listOfConflicts = scheduleDAO.listConflicts();
		JSONArray resultJsonArray = new JSONArray();
		
		for (int i=0;i<listOfConflicts.size();i++) {
			ConflictResult conflictItem = listOfConflicts.get(i);
			JSONObject jo = new JSONObject();
			jo.put("CompanyName", conflictItem.getCompanyName());
			jo.put("FundName", conflictItem.getFundName());
			jo.put("StatusCode", (conflictItem.getConflictStatusCode()==2?"上市公司 资源冲突":"基金公司 资源冲突"));
			jo.put("fund_id", conflictItem.getFund_id());
			jo.put("company_id", conflictItem.getCompany_id());
			resultJsonArray.put(jo);
		}
		return StringUtil.toUnicodeFormat(resultJsonArray.toString());//.replaceAll("\\\\\\\\", "\\\\");
	}
	
	//add lunch, input is fund id and company id, add lunch
	@ResponseBody
	@RequestMapping(value = "/addlunch", method = RequestMethod.POST)
	public int addlunch(@RequestParam("fund_id") int fund_id, @RequestParam("company_id") int company_id,
			@RequestParam("lunchtime_id") int lunchtime_id) {
//		print out the add lunch request detail
//		System.out.println("received add lunch request, fund_id= " + fund_id + " company_id= " 
//				+ company_id + " lunchtime_id= " + lunchtime_id);
		
		// construct the 1 one 1 meeting request
		OneOnOneMeetingRequest request = oneononemeetingrequestDao.getMeetReqForFundCompany(fund_id, company_id);
	
		// add one meeting for this meeting request into the schedule table
		meetingScheduleService.scheduleMeeting(request, lunchtime_id);
		
		// update this 1 one 1 request status in  1 one 1 meeting request table to success status
		oneononemeetingrequestDao.updateMeetingRequestStatus(fund_id, company_id, 5);
//		
		//1 for success
		return 1;
	}
	
	//return lunch time window, could be more than 1
	@ResponseBody
	@RequestMapping(value = "/schedule/getLunchTime")
	public DataList getLunchTime(@RequestParam(value="pageIndex", defaultValue="0") int pageIndex, 
			@RequestParam(value="pageSize", defaultValue="999") int pageSize) {
		
		List <Timeframe> totalTimeframes = timeframeDao.getLunchTimeList();
		 
		int fromIndex = pageIndex*pageSize;
		int toIndex = pageIndex*pageSize+pageSize;
		
		if (toIndex > totalTimeframes.size()) toIndex = totalTimeframes.size();		
		
		List <Timeframe> returnTimeframes = totalTimeframes.subList(fromIndex, toIndex);
		DataList dl = new DataList(totalTimeframes.size(),returnTimeframes);
				
		return dl;
	}
	
	
}


