package main.java.info.jtrac.exception.data;

import main.java.info.jtrac.exception.JTrackException;

/**
 * Class DataDAOException will be called when there is a problem with the data connection to the database
 * @author tom.dhondt - created at : 11-feb.-2014
 */
public class DataDAOException extends JTrackException{
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 6241214126170562235L;
	/**
	 * Constructor for the Class
	 * @param messageCode as String
	 */
	public DataDAOException(String messageCode){
		super.setCaption(messageCode);
	}
	/**
	 * Constructor for the Class
	 * @param messageCode as String
	 * @param stackTraceElements as StackTraceElements[]
	 */
	public DataDAOException(String messageCode, StackTraceElement[] stackTraceElements) {
		this(messageCode);
		super.setStackTrace(stackTraceElements);
	}
}
