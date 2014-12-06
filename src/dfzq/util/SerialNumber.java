package dfzq.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * ����������̺�
 */
public class SerialNumber {

	private  int num = 0;
	private SerialNumber(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("mmss");
		String mmssStr = df.format(date);
		int mmssInt = Integer.parseInt(mmssStr);
		num = mmssInt;
	}

	static Random r1 = new Random();
	private static SerialNumber instance = null;
	public static SerialNumber getInstance(){
		if(instance==null){
			synchronized (SerialNumber.class) {
				if(instance==null){
					instance = new SerialNumber();
				}
			}
		}
		return instance;
	}

	public synchronized int getNum(){
		if(num >= 9999){
			num = 0;
		}
		return ++num;
	}

	public static String getNumStr(){
		StringBuffer retStr = new StringBuffer();
		int num = SerialNumber.getInstance().getNum();
		retStr.append( num+"");
		if(retStr.length()<4){
			for (int i = retStr.length(); i < 4; i++) {
				retStr.append(" ");
			}
		}
		return retStr.toString();
	}

	public static String getNumStrForClientId(){
		StringBuffer retStr = new StringBuffer();
		int num = r1.nextInt(99);
		retStr.append( num+"");
		if(retStr.length()<2){
			for (int i = retStr.length(); i < 2; i++) {
				retStr.append("0");
			}
		}
		return retStr.toString();
	}
	

//	public static void main(String[] args) {
//		String test = getNumStrForClientId();
//		System.out.println( "["+test+"]");
//		System.out.println( test.length());
//	}
}
