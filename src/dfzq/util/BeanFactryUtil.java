package dfzq.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactryUtil {
	public static ApplicationContext ctx = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext(
	    		new String[] {"file:./WebRoot/WEB-INF/applicationContext.xml",
	    					"file:./WebRoot/WEB-INF/springmvc-servlet.xml"});
//		LogDetailDao d1 = (LogDetailDao)ctx.getBean("logDetailDao");
//		LogDetailDao d2 = (LogDetailDao)ctx.getBean("logDetailDao");
	}

	public static Object getBean(String beanName) {
		  if(ctx == null ){
				ctx = new ClassPathXmlApplicationContext(
			    		new String[] {"file:./WebRoot/WEB-INF/applicationContext.xml",
			    					"file:./WebRoot/WEB-INF/springmvc-servlet.xml"});
		  }
          return ctx.getBean(beanName);
	}
	
	
	

}
