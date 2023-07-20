package com.security.springsecurity.util.resultmessage;

public enum ResponseMessageTypeCode {
	
	SUCCESS 			( "1000", "Success" ),
	FAILED				( "0000", "Failed" ),
	AUTHORIZATION_EMPTY ( "0001", "Authorization cannot be empty or null" ),
	UNAUTHORIZED 		( "0002", "Unauthorized"),
	ACCESSDENIED 		( "0003", "Access Denied"),
	GENERAL_ERROR		( "0004", "General Error" ),
	USERNAME_EMPTY		( "0005", "User name cannot be empty or null"),
	PASSWORD_EMPTY		( "0006", "Password cannot be empty or null"),
	USERTYPE_EMPTY		( "0007", "User type cannot be empty or null"),
	USER_NOT_FOUND		( "0008", "User not found"),
	UNABLE_GET_TOKEN	( "0009", "Unable to get token. Please try again."),
	TOKEN_EXPIRED		( "0010", "Token has expired."),
	TOKEN_SIGNATURE_DOES_NOT_MATCH( "0011", "Token signature does not match locally computed signature. Token validity cannot be asserted and should not be trusted.");
	
	private String resultCode;
	private String resultMessage;
	
	
	private ResponseMessageTypeCode(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	
	/**
	 * @param resultCode
	 * @return
	 */
	public ResponseMessageTypeCode getResultMessage( String resultCode ) {
		
		ResponseMessageTypeCode resultConst = null;
		if ( resultCode != null ) {
			for ( ResponseMessageTypeCode searchConst : values() ) {
				if ( searchConst.getResultCode().equals( resultCode ) ) {
					resultConst = searchConst;
					break;
				}
			}
		} 
		return resultConst;
		
	}
	

}
