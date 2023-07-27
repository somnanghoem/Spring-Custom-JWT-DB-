/*-----------------------------------------------------------------------------------------
 * NAME : CurrencyTypeCode.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-27   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.util.type;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName CurrencyTypeCode
* @version   0.1, 2023-07-27
*/

public enum CurrencyCodeType {
	
	USD("USD", "US Dollar"),
	KHR("KHR", "Khmer Riel");
	
	private String value;
	private String description;
	
	CurrencyCodeType(String value, String description) {
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
