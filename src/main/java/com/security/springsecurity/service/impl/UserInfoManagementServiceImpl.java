/*-----------------------------------------------------------------------------------------
 * NAME : UserInfoManagementServiceImpl.java
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
package com.security.springsecurity.service.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.springsecurity.dao.UserInfoDAO;
import com.security.springsecurity.service.UserInfoManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserInfoManagementServiceImpl
* @version   0.1, 2023-08-03
*/
@Service
public class UserInfoManagementServiceImpl implements UserInfoManagementService {
	
	@Autowired
	private UserInfoDAO userInfoDAO;
	private Logger logger = LoggerFactory.getLogger( UserInfoManagementServiceImpl.class );
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public DataUtil getUseInfoByUserInfo( DataUtil param ) throws Exception {
		
		try {
			DataUtil userInfo = userInfoDAO.retrieveUserInfo(param);
			if ( userInfo == null ) {
				throw new Exception( ResponseMessageTypeCode.USER_NOT_FOUND.getResultCode() );
			}
			return userInfo;
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> Get User Info By UserName & Password Error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public DataUtil getUseInfoByUserName( String userName ) throws Exception {
		
		try {
			DataUtil param = new DataUtil();
			param.setString("userName", userName );
			DataUtil userInfo = userInfoDAO.retrieveUserInfo(param);
			if ( userInfo == null ) {
				throw new Exception( ResponseMessageTypeCode.USER_NOT_FOUND.getResultCode() );
			}
			return userInfo;
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> Get User Info By UserName  Error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}

}
