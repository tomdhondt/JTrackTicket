package main.java.info.jtrac.domain;
/**
 * Enum Status
 * @author Tom Dhondt - created at : 17-mei-2014
 */
public enum Status {
	NEW("STATUS.NEW",10),
	OPEN("STATUS.OPEN",20),
	CLOSED("STATUS.CLOSED",30);
	/* instance members */
	private String caption;
	private int state;
	/**
	 * Default constructor for the Class
	 * @param caption
	 * @param state
	 */
	Status(String caption, int state){
		this.state = state;
		this.caption = caption;
	}
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
}
