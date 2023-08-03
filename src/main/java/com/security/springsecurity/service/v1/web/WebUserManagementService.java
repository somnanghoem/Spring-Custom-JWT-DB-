/*-----------------------------------------------------------------------------------------
 * NAME : WebUserManagementService.java
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
package com.security.springsecurity.service.v1.web;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName WebUserManagementService
* @version   0.1, 2023-08-03
*/

public interface WebUserManagementService {
	
	public DataUtil registerUserInformation( DataUtil param ) throws Exception;

}
