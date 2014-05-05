package main.java.info.jtrac.aop.manager;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

public class AfterObjectIsManaged implements AfterReturningAdvice {
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
