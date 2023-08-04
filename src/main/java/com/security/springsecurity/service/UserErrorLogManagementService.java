/*-----------------------------------------------------------------------------------------
 * NAME : UserErrorLogManagementService.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-08-04   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.service;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.request.RequestData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserErrorLogManagementService
* @version   0.1, 2023-08-04
*/

public interface UserErrorLogManagementService {
	public void registerUserErrorLogInfo( RequestData<DataUtil> requestData, String resultCode, String resultMessage , String ErrorException ) throws Exception;
}
