/*-----------------------------------------------------------------------------------------
 * NAME : UserInfoManagementService.java
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
package com.security.springsecurity.service;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserInfoManagementService
* @version   0.1, 2023-08-03
*/

public interface UserInfoManagementService {

	public DataUtil getUseInfoByUserInfo( DataUtil param ) throws Exception;
	public DataUtil getUseInfoByUserName( String userName ) throws Exception;
	
}
