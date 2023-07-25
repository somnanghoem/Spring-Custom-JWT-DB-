package com.security.springsecurity.util.type;

public enum YnTypeCode {
	
	YES( "Y", "YES" ),
	NO("N", "NO");
	private String value;
	private String description;
	
	private YnTypeCode(String value, String description) {
		this.value = value;
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	
	
}
