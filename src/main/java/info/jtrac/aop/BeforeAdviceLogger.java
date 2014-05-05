package main.java.info.jtrac.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * Class BeforeAdviceLogger that will function as a logger
 * @author tom.dhondt - created at : 16-February-2014
 */
public class BeforeAdviceLogger implements MethodBeforeAdvice {
	/**
	 * Method will log the state of the object before going further in the code
	 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		Logger logger = Logger.getLogger(arg1[0].getClass());
        if(null != arg1[0]){
        	logger.info(arg1[0].getClass() + " - " + arg0.getName() + " - " + arg1[0].toString());
        }
	}
}
