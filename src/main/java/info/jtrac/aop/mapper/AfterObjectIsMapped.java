package main.java.info.jtrac.aop.mapper;

import java.lang.reflect.Method;


import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
/**
 * Class BeforeAdviceLogger that will function as a logger
 * @author tom.dhondt - created at : 16-February-2014
 */
public class AfterObjectIsMapped implements AfterReturningAdvice {
	/**
	 * Method will log the state of the object before executing the method
	 */
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		try{
			Logger logger = Logger.getRootLogger();
			logger.info(this.getClass().getName() + " - " + arg3.getClass() + " - " + arg1.getName() + " - "  + arg0.toString());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
