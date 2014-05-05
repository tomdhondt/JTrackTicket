package main.java.info.jtrac.aop.mapper;

import java.lang.reflect.Method;

import main.java.info.jtrac.exception.mapper.MapperException;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * Class BeforeAdviceLogger that will function as a logger
 * @author tom.dhondt - created at : 16-February-2014
 */
public class BeforeObjectIsNull implements MethodBeforeAdvice {
	/**
	 * Method will log the state of the object before executing the method
	 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		Logger logger = Logger.getRootLogger();
        if(null != arg1[0]){
        	logger.info(this.getClass().getName() + " - " + arg1[0].getClass() + " - " + arg0.getName() + " - " + arg1[0].toString());
        }else{
        	logger.info(this.getClass().getName() + " - " + arg2.getClass() + " - " + "Object is null!");
        	throw new MapperException("Object.isNull");
        }
    }
}
