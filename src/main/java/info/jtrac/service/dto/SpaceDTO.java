package main.java.info.jtrac.service.dto;
/**
 * Class SpaceDTO data translate object
 * @author Tom Dhondt - created at : 3-mei-2014
 */
public class SpaceDTO {
	/* Instance members */
    private String id;
    private String type;
    private String prefixCode;
    private String name;
    private String description;
    private boolean guestAllowed;
    private String spaceSequence_id;
    private String spaceSequence_nextSeqNum;
    private String md_Id;
    private String md_type;
	private String md_name;
	private String md_description;
	private String md_xmlString;
	private String mdp_Id;
    private String mdp_type;
	private String mdp_name;
	private String mdp_description;
	private String mdp_xmlString;
    /**
     * Default constructor for the class
     */
    public SpaceDTO(){
    }
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
	 * @return the prefixCode
	 */
	public String getPrefixCode() {
		return prefixCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the guestAllowed
	 */
	public boolean isGuestAllowed() {
		return guestAllowed;
	}
	/**
	 * @return the spaceSequence_id
	 */
	public String getSpaceSequence_id() {
		return spaceSequence_id;
	}
	/**
	 * @return the spaceSequence_nextSeqNum
	 */
	public String getSpaceSequence_nextSeqNum() {
		return spaceSequence_nextSeqNum;
	}
	/**
	 * @return the md_Id
	 */
	public String getMd_Id() {
		return md_Id;
	}
	/**
	 * @return the md_type
	 */
	public String getMd_type() {
		return md_type;
	}
	/**
	 * @return the md_name
	 */
	public String getMd_name() {
		return md_name;
	}
	/**
	 * @return the md_description
	 */
	public String getMd_description() {
		return md_description;
	}
	/**
	 * @return the md_xmlString
	 */
	public String getMd_xmlString() {
		return md_xmlString;
	}
	/**
	 * @return the mdp_Id
	 */
	public String getMdp_Id() {
		return mdp_Id;
	}
	/**
	 * @return the mdp_type
	 */
	public String getMdp_type() {
		return mdp_type;
	}
	/**
	 * @return the mdp_name
	 */
	public String getMdp_name() {
		return mdp_name;
	}
	/**
	 * @return the mdp_description
	 */
	public String getMdp_description() {
		return mdp_description;
	}
	/**
	 * @return the mdp_xmlString
	 */
	public String getMdp_xmlString() {
		return mdp_xmlString;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param prefixCode the prefixCode to set
	 */
	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param guestAllowed the guestAllowed to set
	 */
	public void setGuestAllowed(boolean guestAllowed) {
		this.guestAllowed = guestAllowed;
	}
	/**
	 * @param spaceSequence_id the spaceSequence_id to set
	 */
	public void setSpaceSequence_id(String spaceSequence_id) {
		this.spaceSequence_id = spaceSequence_id;
	}
	/**
	 * @param spaceSequence_nextSeqNum the spaceSequence_nextSeqNum to set
	 */
	public void setSpaceSequence_nextSeqNum(String spaceSequence_nextSeqNum) {
		this.spaceSequence_nextSeqNum = spaceSequence_nextSeqNum;
	}
	/**
	 * @param md_Id the md_Id to set
	 */
	public void setMd_Id(String md_Id) {
		this.md_Id = md_Id;
	}
	/**
	 * @param md_type the md_type to set
	 */
	public void setMd_type(String md_type) {
		this.md_type = md_type;
	}
	/**
	 * @param md_name the md_name to set
	 */
	public void setMd_name(String md_name) {
		this.md_name = md_name;
	}
	/**
	 * @param md_description the md_description to set
	 */
	public void setMd_description(String md_description) {
		this.md_description = md_description;
	}
	/**
	 * @param md_xmlString the md_xmlString to set
	 */
	public void setMd_xmlString(String md_xmlString) {
		this.md_xmlString = md_xmlString;
	}
	/**
	 * @param mdp_Id the mdp_Id to set
	 */
	public void setMdp_Id(String mdp_Id) {
		this.mdp_Id = mdp_Id;
	}
	/**
	 * @param mdp_type the mdp_type to set
	 */
	public void setMdp_type(String mdp_type) {
		this.mdp_type = mdp_type;
	}
	/**
	 * @param mdp_name the mdp_name to set
	 */
	public void setMdp_name(String mdp_name) {
		this.mdp_name = mdp_name;
	}
	/**
	 * @param mdp_description the mdp_description to set
	 */
	public void setMdp_description(String mdp_description) {
		this.mdp_description = mdp_description;
	}
	/**
	 * @param mdp_xmlString the mdp_xmlString to set
	 */
	public void setMdp_xmlString(String mdp_xmlString) {
		this.mdp_xmlString = mdp_xmlString;
	}
	/**
	 * Method will return the state of the object as a String
	 * @return state as String
	 */
	@Override
	public String toString() {
		StringBuilder state = new StringBuilder();
		String sep = " - ";
		state.append(this.id);
		state.append(sep);
		state.append(this.type);
		state.append(sep);
		state.append(prefixCode);
		state.append(sep);
		state.append(name);
		state.append(sep);
		state.append(description);
		state.append(sep);
		state.append(guestAllowed);
		state.append(sep);
		state.append(spaceSequence_id);
		state.append(sep);
		state.append(spaceSequence_nextSeqNum);
		state.append(sep);
		state.append(md_Id);
		state.append(sep);
		state.append(mdp_Id);
		return state.toString();
	}
    
}
