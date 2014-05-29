package main.java.info.jtrac.service.dto;

public class AttachmentDTO {
	/* instance members */
	private String id;
    private String previous_Att_Id;
    private String filePrefix;
    private String fileName;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the previous_Att_Id
	 */
	public String getPrevious_Att_Id() {
		return previous_Att_Id;
	}
	/**
	 * @return the filePrefix
	 */
	public String getFilePrefix() {
		return filePrefix;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param previous_Att_Id the previous_Att_Id to set
	 */
	public void setPrevious_Att_Id(String previous_Att_Id) {
		this.previous_Att_Id = previous_Att_Id;
	}
	/**
	 * @param filePrefix the filePrefix to set
	 */
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    @Override
    public String toString() {
    	StringBuilder output = new StringBuilder();
		String sepa = " - ";
    	output.append(this.getId());
		output.append(sepa);
    	output.append(this.getFileName());
		output.append(sepa);
    	output.append(this.getFilePrefix());
		output.append(sepa);
    	output.append(this.getPrevious_Att_Id());
    	return output.toString();
    }
}
