/*-----------------------------------------------------------------------------------------
 * NAME : SequenceNoMaster.java
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
package com.security.springsecurity.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.springsecurity.util.data.DataUtil;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName SequenceNoMaster
* @version   0.1, 2023-07-31
*/

@Mapper
public interface SequenceNoMaster {
	
	public long regsiterSequenceMasterInfo ( DataUtil param );
	
	public DataUtil retrieveSequenceMasterInfo( DataUtil param ) throws Exception;

}
