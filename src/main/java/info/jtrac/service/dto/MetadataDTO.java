package main.java.info.jtrac.service.dto;

/**
 * Class MetadataDTO
 * @author Tom Dhondt - created at : 3-mei-2014
 */
public class MetadataDTO {
	private String id;
    private String type;
    private String name;
    private String description;
    private String xmlString;
	private String parent_Id;
    private String parent_Type;
    private String parent_Name;
    private String parent_Description;
    private String parent_XmlString;
    /**
     * Default constructor for the Class
     */
    public MetadataDTO(){
    }
    /**
     * Constructor for the class
     * @param name as String
     * @param description as String
     * @param xmlString as String
     */
    public MetadataDTO(String name, String description, String xmlString){
    	this();
    	this.name = name;
    	this.description = description;
    	this.xmlString = xmlString;
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
	 * @return the xmlString
	 */
	public String getXmlString() {
		return xmlString;
	}
	/**
	 * @return the parent_Id
	 */
	public String getParent_Id() {
		return parent_Id;
	}
	/**
	 * @return the parent_Type
	 */
	public String getParent_Type() {
		return parent_Type;
	}
	/**
	 * @return the parent_Name
	 */
	public String getParent_Name() {
		return parent_Name;
	}
	/**
	 * @return the parent_Description
	 */
	public String getParent_Description() {
		return parent_Description;
	}
	/**
	 * @return the parent_XmlString
	 */
	public String getParent_XmlString() {
		return parent_XmlString;
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
	 * @param xmlString the xmlString to set
	 */
	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
	/**
	 * @param parent_Id the parent_Id to set
	 */
	public void setParent_Id(String parent_Id) {
		this.parent_Id = parent_Id;
	}
	/**
	 * @param parent_Type the parent_Type to set
	 */
	public void setParent_Type(String parent_Type) {
		this.parent_Type = parent_Type;
	}
	/**
	 * @param parent_Name the parent_Name to set
	 */
	public void setParent_Name(String parent_Name) {
		this.parent_Name = parent_Name;
	}
	/**
	 * @param parent_Description the parent_Description to set
	 */
	public void setParent_Description(String parent_Description) {
		this.parent_Description = parent_Description;
	}
	/**
	 * @param parent_XmlString the parent_XmlString to set
	 */
	public void setParent_XmlString(String parent_XmlString) {
		this.parent_XmlString = parent_XmlString;
	}
	/**
	 * Method will return the state of the object as a String
	 * @return state as String
	 */
    public String toString(){
		StringBuilder state = new StringBuilder();
		String sep = " - ";
		state.append(this.id);
		state.append(sep);
		state.append(type);
		state.append(sep);
		state.append(name);
		state.append(sep);
		state.append(description);
		state.append(sep);
		state.append(xmlString);
		state.append(sep);
		state.append(parent_Id);
		state.append(sep);
		state.append(parent_Type);
		state.append(sep);
		state.append(parent_Name);
		state.append(sep);
		state.append(parent_Description);
		state.append(sep);
		state.append(parent_XmlString);
		return state.toString();
	}
	
}
