/*-----------------------------------------------------------------------------------------
 * NAME : SendTelegramNotificationService.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-28   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.service;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName SendTelegramNotificationService
* @version   0.1, 2023-07-28
*/

public interface SendTelegramNotificationService {
	
	public DataUtil sendTelegramMessage( DataUtil param ) throws Exception;

}
