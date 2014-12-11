package dfzq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatter {
	
	public static java.util.Date js2Date(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date date = sdf.parse(s);
		return date;
	}
	
	public static java.util.Date js2Datetime(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		java.util.Date date = sdf.parse(s);
		return date;
	}
	
	public static String toJsDatetimeString(java.util.Date d) {
		String jsPattern = "MM/dd/yyyy HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(jsPattern);
		return sdf.format(d);
	}
}
