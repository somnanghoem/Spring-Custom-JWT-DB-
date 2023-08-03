/*-----------------------------------------------------------------------------------------
 * NAME : UserInfoDAO.java
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
package com.security.springsecurity.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserInfoDAO
* @version   0.1, 2023-08-03
*/

@Mapper
public interface UserInfoDAO {

	public long registerUserInfo( DataUtil param ) throws Exception;
	public long updateUserInfo( DataUtil param ) throws Exception;
	public DataUtil retrieveUserInfo( DataUtil param ) throws Exception;
	
}
