/*-----------------------------------------------------------------------------------------
 * NAME : SequenceNoDigit.java
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
* @logicalName SequenceNoDigit
* @version   0.1, 2023-07-31
*/

public enum SequenceNoDigit {

	FOUR( 4L ),
	EIGHT( 8L ),
	SIXTEEN( 16L );
	
	private long value;
	private SequenceNoDigit(long value) {
		this.value = value;
	}
	public long getValue() {
		return value;
	}
	
}
