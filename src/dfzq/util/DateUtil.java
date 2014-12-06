package dfzq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
			System.out.println( getTimeLongList(30) );
//			System.out.println( getDateStr(0) );
//			System.out.println( getDateStr(1) );
//			System.out.println( getDateStr(2) );
//			System.out.println( getDateStr(3) );
//			System.out.println( getDateStr(4) );
//			System.out.println( getDateStr(5) );
//			System.out.println( getDateStr(6) );
//			System.out.println( getDateStr(7) );
//			System.out.println( getDateStr(8) );
	}

	public static String  getDateStr(int days) {
		Date date=new   Date();//取时间 
	     Calendar   calendar   =   new   GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(calendar.DATE,days);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		return new SimpleDateFormat("yyyyMMdd").format( date );
	}

	public static Map<Integer,String>  getTimeLongList(int sec ) throws ParseException {
		Map<Integer,String> map = new HashMap<Integer, String>();
		SimpleDateFormat dateDF= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time2DF= new SimpleDateFormat("HH:mm:ss");
        String todayStr =  dateDF.format( new Date() );
        Date today = dateDF.parse(todayStr);
		int ret = (int)60*24;
		for (int i = 0; i < (ret/sec)+1; i++) {
			long time0 = today.getTime() + ((i)*sec*60*1000);
			String timeStr = time2DF.format(new Date(time0)) ;
			map.put( i , timeStr);
		}
		return map;
	}
	
	public static Map<Integer,String>  getTimeList(int sec ) throws ParseException {
		Map<Integer,String> map = new HashMap<Integer, String>();
		SimpleDateFormat dateDF= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time2DF= new SimpleDateFormat("HH:mm:ss");
        String todayStr =  dateDF.format( new Date() );
        Date today = dateDF.parse(todayStr);
		int ret = (int)60*24;
		for (int i = 0; i < (ret/sec); i++) {
			long time0 = today.getTime() + ((i)*sec*60*1000);
			long time = today.getTime() + ((i+1)*sec*60*1000);
			String timeStr = time2DF.format(new Date(time0)) +"-"+ time2DF.format(new Date(time));
			map.put( i , timeStr);
		}
		return map;
	}
	public static Date  getDate(String dateStr) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( dateStr );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String  getDateStr(Date date) {
			if(date == null){
				return "";
			}else{
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( date );
			}
	}
	public static int  getMinuteIndexInSameDate(Date date) {
		int ret  = 0;
        String todayStr =  new SimpleDateFormat("yyyy-MM-dd").format( date );
		Date today;
		try {
			today = new SimpleDateFormat("yyyy-MM-dd").parse(todayStr);
			long d = date.getTime() - today.getTime();
			ret = (int)d/1000/60;
//		System.out.println(   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( now ) );
//		System.out.println(   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( today ) );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}

    public static  Calendar eraseHour(Calendar nextTimeJobCalendar) {
        Calendar parameterCalendar = (Calendar)nextTimeJobCalendar.clone();
        parameterCalendar.set(Calendar.HOUR_OF_DAY, 0);
        parameterCalendar.set(Calendar.MINUTE, 0);
        parameterCalendar.set(Calendar.SECOND, 0);
        parameterCalendar.set(Calendar.MILLISECOND,0);
        return parameterCalendar;
    }

}
