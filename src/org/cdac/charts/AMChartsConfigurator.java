package org.cdac.charts;

public class AMChartsConfigurator {
	private String uploadDirectory;
	private String staticDataDirectory;
	private String datePattern;
	private int dateCell;
	private int dateRow;
	private int totalValueRow;
	
	private int cbseSchoolIndex;
	private int cbseSchoolTrainedIndex;
	private int teacherTrainedIndex;
	
	private int stateSchoolIndex;
	private int stateSchoolTrainedIndex;
	private int stateTeacherTrainedIndex;
	
	private int statesNameIndex;
	private int startRowOfStates;
	private int lastRowOfStates;
	private int yourMaxMemorySize;
	private String firstRowDataValidator;
	private String username;
	private String password;

	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getPassword() {
		return password;
	}
	public void setUploadDirectory(String uploadDirectory) {
		this.uploadDirectory = uploadDirectory;
	}

	public void setStaticDataDirectory(String staticDataDirectory) {
		this.staticDataDirectory = staticDataDirectory;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	public void setDateCell(int dateCell) {
		this.dateCell = dateCell;
	}

	
	
	
	
	
	public int getStateSchoolIndex() {
		return stateSchoolIndex;
	}

	public void setStateSchoolIndex(int stateSchoolIndex) {
		this.stateSchoolIndex = stateSchoolIndex;
	}

	public int getStateSchoolTrainedIndex() {
		return stateSchoolTrainedIndex;
	}

	public void setStateSchoolTrainedIndex(int stateSchoolTrainedIndex) {
		this.stateSchoolTrainedIndex = stateSchoolTrainedIndex;
	}

	public int getStateTeacherTrainedIndex() {
		return stateTeacherTrainedIndex;
	}

	public void setStateTeacherTrainedIndex(int stateTeacherTrainedIndex) {
		this.stateTeacherTrainedIndex = stateTeacherTrainedIndex;
	}

	
	
	
	
	
	public void setDateRow(int dateRow) {
		this.dateRow = dateRow;
	}

	public void setTotalValueRow(int totalValueRow) {
		this.totalValueRow = totalValueRow;
	}

	public void setCbseSchoolIndex(int cbseSchoolIndex) {
		this.cbseSchoolIndex = cbseSchoolIndex;
	}

	public void setCbseSchoolTrainedIndex(int cbseSchoolTrainedIndex) {
		this.cbseSchoolTrainedIndex = cbseSchoolTrainedIndex;
	}

	public void setTeacherTrainedIndex(int teacherTrainedIndex) {
		this.teacherTrainedIndex = teacherTrainedIndex;
	}

	public void setStatesNameIndex(int statesNameIndex) {
		this.statesNameIndex = statesNameIndex;
	}

	public void setStartRowOfStates(int startRowOfStates) {
		this.startRowOfStates = startRowOfStates;
	}

	public void setLastRowOfStates(int lastRowOfStates) {
		this.lastRowOfStates = lastRowOfStates;
	}

	public void setYourMaxMemorySize(int yourMaxMemorySize) {
		this.yourMaxMemorySize = yourMaxMemorySize;
	}

	public void setFirstRowDataValidator(String firstRowDataValidator) {
		this.firstRowDataValidator = firstRowDataValidator;
	}

	public String getUploadDirectory() {
		return uploadDirectory;
	}

	public String getStaticDataDirectory() {
		return staticDataDirectory;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public int getDateCell() {
		return dateCell;
	}

	public int getDateRow() {
		return dateRow;
	}

	public int getTotalValueRow() {
		return totalValueRow;
	}

	public int getCbseSchoolIndex() {
		return cbseSchoolIndex;
	}

	public int getCbseSchoolTrainedIndex() {
		return cbseSchoolTrainedIndex;
	}

	public int getTeacherTrainedIndex() {
		return teacherTrainedIndex;
	}

	public int getStatesNameIndex() {
		return statesNameIndex;
	}

	public int getStartRowOfStates() {
		return startRowOfStates;
	}

	public int getLastRowOfStates() {
		return lastRowOfStates;
	}

	public int getYourMaxMemorySize() {
		return yourMaxMemorySize;
	}

	public String getFirstRowDataValidator() {
		return firstRowDataValidator;
	}

}