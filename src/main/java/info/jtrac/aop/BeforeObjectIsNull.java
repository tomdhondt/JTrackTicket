package main.java.info.jtrac.aop;

import java.lang.reflect.Method;

import main.java.info.jtrac.exception.data.DataDAOException;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

/**
 * Class BeforeAdviceLogger that will function as a logger
 * @author tom.dhondt - created at : 16-February-2014
 */
public class BeforeObjectIsNull implements MethodBeforeAdvice {
	/**
	 * Method will check that the object isn't null.  A null object can't be persisted to the database.
	 * @param Method 
	 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
        if(null != arg1[0]){
        	Logger.getRootLogger().info(arg1[0].getClass() + " - " + arg0.getName() + " - " + arg1[0].toString());
        }else{
        	throw new DataDAOException("Object.isNull");
        }
    }
}
