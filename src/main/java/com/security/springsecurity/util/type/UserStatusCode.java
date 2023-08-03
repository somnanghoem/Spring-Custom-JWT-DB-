/*-----------------------------------------------------------------------------------------
 * NAME : UserStatusCode.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-08-03   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.util.type;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserStatusCode
* @version   0.1, 2023-08-03
*/

public enum UserStatusCode {

	LOCKED	( "00", "User Locked"),
	NORMAL	( "01", "User Normal"),
	DELETED	( "99", "User Delete");
	
	private String value;
	private String description;
	private UserStatusCode(String value, String description) {
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
