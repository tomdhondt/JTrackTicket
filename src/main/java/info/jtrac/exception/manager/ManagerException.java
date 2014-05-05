package main.java.info.jtrac.exception.manager;

import main.java.info.jtrac.exception.JTrackException;

public class ManagerException extends JTrackException {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = -5312520030701149322L;
	/**
	 * Constructor for the Class
	 * @param messageCode
	 */
	public ManagerException(String messageCode) {
		super.setCaption(messageCode);
	}
}
