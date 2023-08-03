/*-----------------------------------------------------------------------------------------
 * NAME : MenuMasterManagementService.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-08-02   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.service.v1.web;

import org.springframework.stereotype.Service;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName MenuMasterManagementService
* @version   0.1, 2023-08-02
*/
@Service
public interface MenuMasterManagementService {
	
	public DataUtil registerMenuMasterInfo( DataUtil param ) throws Exception;

}
