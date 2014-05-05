package main.java.info.jtrac.aop;

import java.lang.reflect.Method;


import org.springframework.aop.MethodBeforeAdvice;

/**
 * Class BeforeAdviceLogger that will function as a logger
 * @author tom.dhondt - created at : 16-February-2014
 */
public class BeforeAdviceAlreadyExists implements MethodBeforeAdvice {
	/**
	 * Method will log the state of the object before executing the method
	 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("hier");
    }
}

