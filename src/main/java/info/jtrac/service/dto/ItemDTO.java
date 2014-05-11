package main.java.info.jtrac.service.dto;

public class ItemDTO extends AbstractItemDTO{
	/*
	 * Instance members
	 */
	private String id;
    private String space_Id;
    private String space_Type;
    private String space_PrefixCode;
    private String space_Name;
    private String space_Description;
    private boolean space_isGuestAllowed;
//    private Set<History> history;
//    private Set<Item> children;
//    private Set<Attachment> attachments;
    // should be ideally in form backing object but for convenience
    private String editReason;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the space_Id
	 */
	public String getSpace_Id() {
		return space_Id;
	}
	/**
	 * @return the space_Type
	 */
	public String getSpace_Type() {
		return space_Type;
	}
	/**
	 * @return the space_PrefixCode
	 */
	public String getSpace_PrefixCode() {
		return space_PrefixCode;
	}
	/**
	 * @return the space_Name
	 */
	public String getSpace_Name() {
		return space_Name;
	}
	/**
	 * @return the space_Description
	 */
	public String getSpace_Description() {
		return space_Description;
	}
	/**
	 * @return the space_isGuestAllowed
	 */
	public boolean isSpace_isGuestAllowed() {
		return space_isGuestAllowed;
	}
	/**
	 * @return the editReason
	 */
	public String getEditReason() {
		return editReason;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param space_Id the space_Id to set
	 */
	public void setSpace_Id(String space_Id) {
		this.space_Id = space_Id;
	}
	/**
	 * @param space_Type the space_Type to set
	 */
	public void setSpace_Type(String space_Type) {
		this.space_Type = space_Type;
	}
	/**
	 * @param space_PrefixCode the space_PrefixCode to set
	 */
	public void setSpace_PrefixCode(String space_PrefixCode) {
		this.space_PrefixCode = space_PrefixCode;
	}
	/**
	 * @param space_Name the space_Name to set
	 */
	public void setSpace_Name(String space_Name) {
		this.space_Name = space_Name;
	}
	/**
	 * @param space_Description the space_Description to set
	 */
	public void setSpace_Description(String space_Description) {
		this.space_Description = space_Description;
	}
	/**
	 * @param space_isGuestAllowed the space_isGuestAllowed to set
	 */
	public void setSpace_isGuestAllowed(boolean space_isGuestAllowed) {
		this.space_isGuestAllowed = space_isGuestAllowed;
	}
	/**
	 * @param editReason the editReason to set
	 */
	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((editReason == null) ? 0 : editReason.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDTO other = (ItemDTO) obj;
		if (editReason == null) {
			if (other.editReason != null)
				return false;
		} else if (!editReason.equals(other.editReason))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", space_Id=" + space_Id + ", space_Type="
				+ space_Type + ", space_PrefixCode=" + space_PrefixCode
				+ ", space_Name=" + space_Name + ", space_Description="
				+ space_Description + ", space_isGuestAllowed="
				+ space_isGuestAllowed + ", editReason=" + editReason + "]";
	}
    
}
