package main.java.info.jtrac.service.dto;

import main.java.info.jtrac.util.MappingUtil;

/**
 * Class SpaceSequenceDTO
 * @author Tom Dhondt - created at : 3-mei-2014
 */
public class SpaceSequenceDTO {
	/* Instance members */
    private String id;
    private String nextSeqNum;
    private String space_id;
    private String space_Name;
    /**
     * Default constructor for the Class
     */
    public SpaceSequenceDTO(){
    	this.nextSeqNum = "1";
    }
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the nextSeqNum
	 */
	public String getNextSeqNum() {
		return nextSeqNum;
	}
	/**
	 * @return the space_id
	 */
	public String getSpace_id() {
		return space_id;
	}
	/**
	 * @return the space_Name
	 */
	public String getSpace_Name() {
		return space_Name;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param nextSeqNum the nextSeqNum to set
	 */
	public void setNextSeqNum(String nextSeqNum) {
		this.nextSeqNum = nextSeqNum;
	}
	/**
	 * @param space_id the space_id to set
	 */
	public void setSpace_id(String space_id) {
		this.space_id = space_id;
	}
	/**
	 * @param space_Name the space_Name to set
	 */
	public void setSpace_Name(String space_Name) {
		this.space_Name = space_Name;
	}
	/**
	 * Method will increase the value of the nextSequenceNumber with one
	 * @return nextSeqNum as String
	 */
    public String next() {
       long next =  MappingUtil.mapStringToLong(this.nextSeqNum);
       return MappingUtil.mapLongToString(++next);
    }
	/**
	 * Method will return the state of the object as a String
	 * @return state as String
	 */
	@Override
	public String toString() {
		StringBuilder state = new StringBuilder();
		String sep = " - ";
		state.append(id);
		state.append(sep);
		state.append(nextSeqNum);
		state.append(sep);
		state.append(space_id);
		state.append(sep);
		state.append(space_Name);
		return state.toString();
	}
    
}
