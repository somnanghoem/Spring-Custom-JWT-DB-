package com.security.springsecurity.util.type;

public enum SequenceNoStatusCode {
	
	DELETED( "00", "deleted"),
	NORMAL( "01", "Normal");
	
	private String value;
	private String description;
	
	private SequenceNoStatusCode(String value, String description) {
		this.value = value;
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
