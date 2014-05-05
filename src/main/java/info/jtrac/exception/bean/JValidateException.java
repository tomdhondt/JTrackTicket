package main.java.info.jtrac.exception.bean;

import main.java.info.jtrac.exception.JTrackException;

/**
 * Class JValidateException will signal a problem with the state of the object
 * @author tom.dhondt - created at : 16-feb.-2014
 *
 */
public class JValidateException extends JTrackException{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 5901089133613730123L;
	/**
	 * Constructor for the Class
	 * @param messageCode as String
	 */
	public JValidateException(String messageCode){
		super.setCaption(messageCode);
	}
}
