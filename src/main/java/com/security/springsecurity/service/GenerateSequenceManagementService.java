/*-----------------------------------------------------------------------------------------
 * NAME : GenerateSequenceManagementService.java
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
package com.security.springsecurity.service;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName GenerateSequenceManagementService
* @version   0.1, 2023-07-31
*/

public interface GenerateSequenceManagementService {
	
	public String generateCommitSeqNo( DataUtil param ) throws Exception;

}
