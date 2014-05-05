package main.java.info.jtrac.service.dto;

import nl.knowlogy.validation.annotations.ValidateClass;
import nl.knowlogy.validation.annotations.ValidateIsNotBlank;
import nl.knowlogy.validation.annotations.ValidatePattern;

@ValidateClass
public class UserDTO {
    private String id;
    private boolean active;
    private String type;
    private UserDTO parent;
    private String loginName;
    private String name;
    private String password;
    private String email;
    private String userRole;
//    private MetadataDTO metadata;
    private String locale;
    private boolean locked;
//    private Set<UserSpaceRole> userSpaceRoles = new HashSet<UserSpaceRole>();
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the parent
	 */
	public UserDTO getParent() {
		return parent;
	}
	/**
	 * @return the loginName
	 */
	 @ValidateIsNotBlank(errorCode = "loginname.caption.cantBeBlank")
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the userRole
	 */
	 @ValidateIsNotBlank(errorCode = "userrole.caption.cantBeBlank")
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the email
	 */
	@ValidatePattern(pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$", errorCode = "email.caption.regex")
	public String getEmail() {
		return email;
	}
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(UserDTO parent) {
		this.parent = parent;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (locked != other.locked)
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	/**
	 * Method will return the state of the object as a String
	 */
	@Override
	public String toString() {
		StringBuilder state = new StringBuilder();
		String sep = " - ";
		state.append(sep);
		state.append(this.id);
		state.append(sep);
		state.append(this.isActive());
		state.append(sep);
		state.append(this.type);
		state.append(sep);
		state.append(this.parent);
		state.append(sep);
		state.append(this.loginName);
		state.append(sep);
		state.append(this.name);
		state.append(sep);
		state.append(this.userRole);
		state.append(sep);
		state.append(this.password);
		state.append(sep);
		state.append(this.email);
		state.append(sep);
		state.append(this.locale);
		state.append(sep);
		state.append(this.locked);
		return state.toString();
	}
}
