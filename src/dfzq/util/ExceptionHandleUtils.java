package dfzq.util;


import java.util.ArrayList;
import java.util.List;

/**
 * �쳣����Ĺ�����
 */
public class ExceptionHandleUtils {
	
	public static String getExceptionDetailInfor(Throwable ex){
		if(ex == null){
			return "";
		}
		List<String> templist = getExceptionDetailInfor( ex,4);
		return templist.toString();
	}
	public static List<String> getExceptionDetailInfor(Throwable ex,int stackCount){
		   List<String> templist = new ArrayList<String>(); 
			if(ex == null){
				return templist;
			}
		   templist.add( ex.toString() );
		   StackTraceElement [] messages = ex.getStackTrace();
		   int length=messages.length;
		   for(int i=0;i<length;i++){
	//		    System.out.println("ClassName:"+messages[i].getClassName());
	//		    System.out.println("getFileName:"+messages[i].getFileName());
	//		    System.out.println("getLineNumber:"+messages[i].getLineNumber());
	//		    System.out.println("getMethodName:"+messages[i].getMethodName());
//			    System.out.println("at "+messages[i].toString());
			    if(templist.size()-1 >= stackCount){
			    	break;
			    }
			    templist.add("at "+messages[i].toString() );
		    }
			return templist;
	}
}