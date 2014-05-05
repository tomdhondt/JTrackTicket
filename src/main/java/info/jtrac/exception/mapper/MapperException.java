package main.java.info.jtrac.exception.mapper;

import main.java.info.jtrac.exception.JTrackException;

/**
 * Class MapperException will be called when there is a problem with mapping a object to a DTO
 * @author tom.dhondt - created at : 11-feb.-2014
 */
public class MapperException extends JTrackException{
	/**
	 * Constructor for the Class
	 * @param messageCode
	 */
	public MapperException(String messageCode) {
		super.setCaption(messageCode);
	}
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 6241214126170562235L;
}
