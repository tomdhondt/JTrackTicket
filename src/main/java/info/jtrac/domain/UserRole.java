package main.java.info.jtrac.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Class UserRole will represent the different userroles possible for a certain user.
 * @author Tom Dhondt - created at : 3-mei-2014
 */
public enum UserRole{
	ADMIN("ADMIN","CAP.ENU.ADMIN"),
	SUPERUSER("SUPERUSER","CAP.ENU.SUPERUSER"),
	USER("USER","CAP.ENU.USER"),
	GUEST("GUEST","CAP.ENU.GUEST");
	/* instance members */
	private String role;
	private String caption;
	/**
	 * Default constructor for the class
	 * @param role as String
	 * @param caption as String
	 */
	private UserRole(String role, String caption){
		this.role = role;
		this.caption = caption;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * Method will return the value
	 * @return
	 */
	public static List<UserRole> getValues(){
		return Arrays.asList(UserRole.values());
	}
}
