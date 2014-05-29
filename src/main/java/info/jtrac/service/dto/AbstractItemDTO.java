package main.java.info.jtrac.service.dto;

import java.util.Date;

import main.java.info.jtrac.domain.Status;

/**
 * 
 * Class AbstractItem as DTO Object
 * @author Tom Dhondt - created at : 11-mei-2014
 *
 */
public class AbstractItemDTO {
	/*
	 * Instance members
	 */
	private String id;
    private String parent_Item_Id;
    private String parent_Item_EditReason;
    private String summary;
    private String detail;
    private String user_loggedBy_Id;
    private String user_loggedBy_loginName;
    private String user_loggedBy_name;
    private String user_loggedBy_email;
    private String user_assignedTo_Id;
    private String user_assignedTo_loginName;
    private String user_assignedTo_name;
    private String user_assignedTo_email;
    private Date timeStamp;					// Date
    private String plannedEffort;			// Double
    private Status status;					// Status
    private String severity;				// Integer
    private String priority;				// Integer
    private String cusInt01;				// Integer
    private String cusInt02;				// Integer
    private String cusInt03;				// Integer
    private String cusInt04;				// Integer
    private String cusInt05;				// Integer
    private String cusInt06;				// Integer
    private String cusInt07;				// Integer
    private String cusInt08;				// Integer
    private String cusInt09;				// Integer
    private String cusInt10;				// Integer
    private String cusDbl01;				// Double
    private String cusDbl02;				// Double
    private String cusDbl03;				// Double
    private String cusStr01;				// String
    private String cusStr02;				// String
	private String cusStr03;				// String
    private String cusStr04;				// String
    private String cusStr05;				// String
	private String cusTim01;				// Date
    private String cusTim02;				// Date
    private String cusTim03;    			// Date
    /**
     * Default constructor for the Class
     */
    public AbstractItemDTO(){
    }
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the parent_Item_Id
	 */
	public String getParent_Item_Id() {
		return parent_Item_Id;
	}
	/**
	 * @return the parent_Item_EditReason
	 */
	public String getParent_Item_EditReason() {
		return parent_Item_EditReason;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @return the user_loggedBy_Id
	 */
	public String getUser_loggedBy_Id() {
		return user_loggedBy_Id;
	}
	/**
	 * @return the user_loggedBy_loginName
	 */
	public String getUser_loggedBy_loginName() {
		return user_loggedBy_loginName;
	}
	/**
	 * @return the user_loggedBy_name
	 */
	public String getUser_loggedBy_name() {
		return user_loggedBy_name;
	}
	/**
	 * @return the user_loggedBy_email
	 */
	public String getUser_loggedBy_email() {
		return user_loggedBy_email;
	}
	/**
	 * @return the user_assignedTo_Id
	 */
	public String getUser_assignedTo_Id() {
		return user_assignedTo_Id;
	}
	/**
	 * @return the user_assignedTo_loginName
	 */
	public String getUser_assignedTo_loginName() {
		return user_assignedTo_loginName;
	}
	/**
	 * @return the user_assignedTo_name
	 */
	public String getUser_assignedTo_name() {
		return user_assignedTo_name;
	}
	/**
	 * @return the user_assignedTo_email
	 */
	public String getUser_assignedTo_email() {
		return user_assignedTo_email;
	}
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @return the plannedEffort
	 */
	public String getPlannedEffort() {
		return plannedEffort;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @return the cusInt01
	 */
	public String getCusInt01() {
		return cusInt01;
	}
	/**
	 * @return the cusInt02
	 */
	public String getCusInt02() {
		return cusInt02;
	}
	/**
	 * @return the cusInt03
	 */
	public String getCusInt03() {
		return cusInt03;
	}
	/**
	 * @return the cusInt04
	 */
	public String getCusInt04() {
		return cusInt04;
	}
	/**
	 * @return the cusInt05
	 */
	public String getCusInt05() {
		return cusInt05;
	}
	/**
	 * @return the cusInt06
	 */
	public String getCusInt06() {
		return cusInt06;
	}
	/**
	 * @return the cusInt07
	 */
	public String getCusInt07() {
		return cusInt07;
	}
	/**
	 * @return the cusInt08
	 */
	public String getCusInt08() {
		return cusInt08;
	}
	/**
	 * @return the cusInt09
	 */
	public String getCusInt09() {
		return cusInt09;
	}
	/**
	 * @return the cusInt10
	 */
	public String getCusInt10() {
		return cusInt10;
	}
	/**
	 * @return the cusDbl01
	 */
	public String getCusDbl01() {
		return cusDbl01;
	}
	/**
	 * @return the cusDbl02
	 */
	public String getCusDbl02() {
		return cusDbl02;
	}
	/**
	 * @return the cusDbl03
	 */
	public String getCusDbl03() {
		return cusDbl03;
	}
	/**
	 * @return the cusStr01
	 */
	public String getCusStr01() {
		return cusStr01;
	}
	/**
	 * @return the cusStr02
	 */
	public String getCusStr02() {
		return cusStr02;
	}
	/**
	 * @return the cusStr03
	 */
	public String getCusStr03() {
		return cusStr03;
	}
	/**
	 * @return the cusStr04
	 */
	public String getCusStr04() {
		return cusStr04;
	}
	/**
	 * @return the cusStr05
	 */
	public String getCusStr05() {
		return cusStr05;
	}
	/**
	 * @return the cusTim01
	 */
	public String getCusTim01() {
		return cusTim01;
	}
	/**
	 * @return the cusTim02
	 */
	public String getCusTim02() {
		return cusTim02;
	}
	/**
	 * @return the cusTim03
	 */
	public String getCusTim03() {
		return cusTim03;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param parent_Item_Id the parent_Item_Id to set
	 */
	public void setParent_Item_Id(String parent_Item_Id) {
		this.parent_Item_Id = parent_Item_Id;
	}
	/**
	 * @param parent_Item_EditReason the parent_Item_EditReason to set
	 */
	public void setParent_Item_EditReason(String parent_Item_EditReason) {
		this.parent_Item_EditReason = parent_Item_EditReason;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @param user_loggedBy_Id the user_loggedBy_Id to set
	 */
	public void setUser_loggedBy_Id(String user_loggedBy_Id) {
		this.user_loggedBy_Id = user_loggedBy_Id;
	}
	/**
	 * @param user_loggedBy_loginName the user_loggedBy_loginName to set
	 */
	public void setUser_loggedBy_loginName(String user_loggedBy_loginName) {
		this.user_loggedBy_loginName = user_loggedBy_loginName;
	}
	/**
	 * @param user_loggedBy_name the user_loggedBy_name to set
	 */
	public void setUser_loggedBy_name(String user_loggedBy_name) {
		this.user_loggedBy_name = user_loggedBy_name;
	}
	/**
	 * @param user_loggedBy_email the user_loggedBy_email to set
	 */
	public void setUser_loggedBy_email(String user_loggedBy_email) {
		this.user_loggedBy_email = user_loggedBy_email;
	}
	/**
	 * @param user_assignedTo_Id the user_assignedTo_Id to set
	 */
	public void setUser_assignedTo_Id(String user_assignedTo_Id) {
		this.user_assignedTo_Id = user_assignedTo_Id;
	}
	/**
	 * @param user_assignedTo_loginName the user_assignedTo_loginName to set
	 */
	public void setUser_assignedTo_loginName(String user_assignedTo_loginName) {
		this.user_assignedTo_loginName = user_assignedTo_loginName;
	}
	/**
	 * @param user_assignedTo_name the user_assignedTo_name to set
	 */
	public void setUser_assignedTo_name(String user_assignedTo_name) {
		this.user_assignedTo_name = user_assignedTo_name;
	}
	/**
	 * @param user_assignedTo_email the user_assignedTo_email to set
	 */
	public void setUser_assignedTo_email(String user_assignedTo_email) {
		this.user_assignedTo_email = user_assignedTo_email;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @param plannedEffort the plannedEffort to set
	 */
	public void setPlannedEffort(String plannedEffort) {
		this.plannedEffort = plannedEffort;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @param cusInt01 the cusInt01 to set
	 */
	public void setCusInt01(String cusInt01) {
		this.cusInt01 = cusInt01;
	}
	/**
	 * @param cusInt02 the cusInt02 to set
	 */
	public void setCusInt02(String cusInt02) {
		this.cusInt02 = cusInt02;
	}
	/**
	 * @param cusInt03 the cusInt03 to set
	 */
	public void setCusInt03(String cusInt03) {
		this.cusInt03 = cusInt03;
	}
	/**
	 * @param cusInt04 the cusInt04 to set
	 */
	public void setCusInt04(String cusInt04) {
		this.cusInt04 = cusInt04;
	}
	/**
	 * @param cusInt05 the cusInt05 to set
	 */
	public void setCusInt05(String cusInt05) {
		this.cusInt05 = cusInt05;
	}
	/**
	 * @param cusInt06 the cusInt06 to set
	 */
	public void setCusInt06(String cusInt06) {
		this.cusInt06 = cusInt06;
	}
	/**
	 * @param cusInt07 the cusInt07 to set
	 */
	public void setCusInt07(String cusInt07) {
		this.cusInt07 = cusInt07;
	}
	/**
	 * @param cusInt08 the cusInt08 to set
	 */
	public void setCusInt08(String cusInt08) {
		this.cusInt08 = cusInt08;
	}
	/**
	 * @param cusInt09 the cusInt09 to set
	 */
	public void setCusInt09(String cusInt09) {
		this.cusInt09 = cusInt09;
	}
	/**
	 * @param cusInt10 the cusInt10 to set
	 */
	public void setCusInt10(String cusInt10) {
		this.cusInt10 = cusInt10;
	}
	/**
	 * @param cusDbl01 the cusDbl01 to set
	 */
	public void setCusDbl01(String cusDbl01) {
		this.cusDbl01 = cusDbl01;
	}
	/**
	 * @param cusDbl02 the cusDbl02 to set
	 */
	public void setCusDbl02(String cusDbl02) {
		this.cusDbl02 = cusDbl02;
	}
	/**
	 * @param cusDbl03 the cusDbl03 to set
	 */
	public void setCusDbl03(String cusDbl03) {
		this.cusDbl03 = cusDbl03;
	}
	/**
	 * @param cusStr01 the cusStr01 to set
	 */
	public void setCusStr01(String cusStr01) {
		this.cusStr01 = cusStr01;
	}
	/**
	 * @param cusStr02 the cusStr02 to set
	 */
	public void setCusStr02(String cusStr02) {
		this.cusStr02 = cusStr02;
	}
	/**
	 * @param cusStr03 the cusStr03 to set
	 */
	public void setCusStr03(String cusStr03) {
		this.cusStr03 = cusStr03;
	}
	/**
	 * @param cusStr04 the cusStr04 to set
	 */
	public void setCusStr04(String cusStr04) {
		this.cusStr04 = cusStr04;
	}
	/**
	 * @param cusStr05 the cusStr05 to set
	 */
	public void setCusStr05(String cusStr05) {
		this.cusStr05 = cusStr05;
	}
	/**
	 * @param cusTim01 the cusTim01 to set
	 */
	public void setCusTim01(String cusTim01) {
		this.cusTim01 = cusTim01;
	}
	/**
	 * @param cusTim02 the cusTim02 to set
	 */
	public void setCusTim02(String cusTim02) {
		this.cusTim02 = cusTim02;
	}
	/**
	 * @param cusTim03 the cusTim03 to set
	 */
	public void setCusTim03(String cusTim03) {
		this.cusTim03 = cusTim03;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime
				* result
				+ ((user_loggedBy_Id == null) ? 0 : user_loggedBy_Id.hashCode());
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
		AbstractItemDTO other = (AbstractItemDTO) obj;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (user_loggedBy_Id == null) {
			if (other.user_loggedBy_Id != null)
				return false;
		} else if (!user_loggedBy_Id.equals(other.user_loggedBy_Id))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractItemDTO [id=" + id + ", parent_Item_Id="
				+ parent_Item_Id + ", parent_Item_EditReason="
				+ parent_Item_EditReason + ", summary=" + summary + ", detail="
				+ detail + ", user_loggedBy_Id=" + user_loggedBy_Id
				+ ", user_loggedBy_loginName=" + user_loggedBy_loginName
				+ ", user_loggedBy_name=" + user_loggedBy_name
				+ ", user_loggedBy_email=" + user_loggedBy_email
				+ ", user_assignedTo_Id=" + user_assignedTo_Id
				+ ", user_assignedTo_loginName=" + user_assignedTo_loginName
				+ ", user_assignedTo_name=" + user_assignedTo_name
				+ ", user_assignedTo_email=" + user_assignedTo_email
				+ ", timeStamp=" + timeStamp + ", plannedEffort="
				+ plannedEffort + ", status=" + status + ", severity="
				+ severity + ", priority=" + priority + ", cusInt01="
				+ cusInt01 + ", cusInt02=" + cusInt02 + ", cusInt03="
				+ cusInt03 + ", cusInt04=" + cusInt04 + ", cusInt05="
				+ cusInt05 + ", cusInt06=" + cusInt06 + ", cusInt07="
				+ cusInt07 + ", cusInt08=" + cusInt08 + ", cusInt09="
				+ cusInt09 + ", cusInt10=" + cusInt10 + ", cusDbl01="
				+ cusDbl01 + ", cusDbl02=" + cusDbl02 + ", cusDbl03="
				+ cusDbl03 + ", cusStr01=" + cusStr01 + ", cusStr02="
				+ cusStr02 + ", cusStr03=" + cusStr03 + ", cusStr04="
				+ cusStr04 + ", cusStr05=" + cusStr05 + ", cusTim01="
				+ cusTim01 + ", cusTim02=" + cusTim02 + ", cusTim03="
				+ cusTim03 + "]";
	}
    
}
