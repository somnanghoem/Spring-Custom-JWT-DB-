/*-----------------------------------------------------------------------------------------
 * NAME : UserErrorLogDAO.java
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
package com.security.springsecurity.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserErrorLogDAO
* @version   0.1, 2023-08-04
*/
@Mapper
public interface UserErrorLogDAO {
	public long registerUserErrorLogInfo( DataUtil param ) throws Exception;
}
