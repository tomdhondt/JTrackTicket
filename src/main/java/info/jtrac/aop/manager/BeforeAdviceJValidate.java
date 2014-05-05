package main.java.info.jtrac.aop.manager;

import java.lang.reflect.Method;

import main.java.info.jtrac.exception.manager.ManagerException;
import nl.knowlogy.validation.Message;
import nl.knowlogy.validation.Messages;
import nl.knowlogy.validation.MessagesImpl;
import nl.knowlogy.validation.ValidationEngine;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
/**
 * Class BeforeJValidateInterceptor will JValidtate the state of the object.
 * @author tom.dhondt - created at : 16-February-2014
 */
public class BeforeAdviceJValidate implements MethodBeforeAdvice {
	/**
	 * Method will validate the Bean with JValidate implementation
	 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		/*
		 * instance members 
		 */
        Messages messages = new MessagesImpl();
        ValidationEngine.validate(arg1[0], messages);
        Logger logger = Logger.getLogger(arg1[0].getClass());
        /*
         * Retrieve the messages in that contain the 
         */
        Message msg = messages.getMessage(arg1[0], false);
        if(null != msg){
        	logger.info(msg.getMessageCode());
        	throw new ManagerException(msg.getMessageCode());
        }
	}

}
