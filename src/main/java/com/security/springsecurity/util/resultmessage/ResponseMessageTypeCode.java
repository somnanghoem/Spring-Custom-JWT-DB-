package com.security.springsecurity.util.resultmessage;

import com.security.springsecurity.util.data.DataUtil;

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
	TOKEN_SIGNATURE_DOES_NOT_MATCH( "0011", "Token signature does not match locally computed signature. Token validity cannot be asserted and should not be trusted."),
	SEQUENCE_CODE_EMPTY ( "0012", "Sequence code cannot be empty or null "),
	SEQUENCE_UNIQUE_CODE_EMPTY ( "0013", "Sequence unique code cannot be empty or null "),
	SEQUENCE_CODE_ERROR ( "0014", "Register sequence master error" ),
	SEQUENCE_CODE_DETIAL_ERROR ( "0015", "Register sequence detail error" ),
	SEQUENCE_CODE_NAME_EMPTY ( "0016", "Sequence code name cannot be empty or null" ),
	REGISTER_MENU_MASTER_ERROR( "0017", "Register menu master error"),
	MENU_CODE_EMPTY ( "0018", "Menu code cannot be empty or null"),
	MENU_DESCRIPTION_EMPTY ( "0019", "Menu description cannot be empty or null"),
	MENU_URL_EMPTY ( "0020", "Menu URL cannot be empty or null"),
	REGISTER_USER_INFO_ERROR( "0021", "Register user information error"),
	USER_PASSWORD_INVLIAD ( "0022", "Invalid user password");
	
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
	public static ResponseMessageTypeCode getResultMessage( String resultCode ) {
		
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
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param errorCode
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static DataUtil prepareErrorResult( String errorCode ) {
		DataUtil outputData = new DataUtil();
		if ( errorCode.length() > 4 ) {
			outputData.setString("resultCode", ResponseMessageTypeCode.GENERAL_ERROR.getResultCode());
			outputData.setString("resultMessage", ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage());
		} else {
			ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage( errorCode );
			outputData.setString("resultCode", resultMessageTypeCode.getResultCode() );
			outputData.setString("resultMessage", resultMessageTypeCode.getResultMessage() );
		}
		return outputData;
	}
	

}
