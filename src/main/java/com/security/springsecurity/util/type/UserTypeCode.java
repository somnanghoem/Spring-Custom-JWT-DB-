/*-----------------------------------------------------------------------------------------
 * NAME : UserTypeCode.java
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
* @logicalName UserTypeCode
* @version   0.1, 2023-08-03
*/

public enum UserTypeCode {
	
	MOBILE	( "01", "Mobile User Type"),
	WEB		( "02", "Web User Type");
	
	private String value;
	private String description;
	private UserTypeCode(String value, String description) {
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
