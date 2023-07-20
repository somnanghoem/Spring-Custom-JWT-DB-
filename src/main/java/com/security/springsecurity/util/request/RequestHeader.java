package com.security.springsecurity.util.request;

public class RequestHeader {
	
	private String userName;
	private String userType;
	
	public RequestHeader( ) {
		super();
	}
	
	public RequestHeader(String userName, String userType) {
		super();
		this.userName = userName;
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	

}
