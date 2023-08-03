/*-----------------------------------------------------------------------------------------
 * NAME : SequenceTypeName.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-31   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.util.type;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName SequenceTypeName
* @version   0.1, 2023-07-31
*/

public enum SequenceCodeName {
	
	TELEGRAM_SEQNO( "TLG001", "Telegram SequenceNo"),
	MENU_MASTER_SEQNO( "MNU001", "Menu Master SequenceNo");
	
	private String name;
	private String code;
	private SequenceCodeName( String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
