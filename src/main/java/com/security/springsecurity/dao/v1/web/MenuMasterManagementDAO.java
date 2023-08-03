/*-----------------------------------------------------------------------------------------
 * NAME : MenuMasterManagementDAO.java
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
package com.security.springsecurity.dao.v1.web;

import org.apache.ibatis.annotations.Mapper;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.data.ListDataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName MenuMasterManagementDAO
* @version   0.1, 2023-08-02
*/

@Mapper
public interface MenuMasterManagementDAO {
	public long registerMenuMasterInfo( DataUtil param );
	/*public long updateMenuMasterInfo( DataUtil param );
	public DataUtil retrieveMenuMasterInfo( DataUtil param );
	public ListDataUtil retrieveListMenuMasterInfo( DataUtil param );*/

}
