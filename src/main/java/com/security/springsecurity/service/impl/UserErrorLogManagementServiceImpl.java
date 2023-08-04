/*-----------------------------------------------------------------------------------------
 * NAME : UserErrorLogManagementServiceImpl.java
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
package com.security.springsecurity.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.springsecurity.dao.UserErrorLogDAO;
import com.security.springsecurity.service.UserErrorLogManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.request.RequestData;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName UserErrorLogManagementServiceImpl
* @version   0.1, 2023-08-04
*/
@Service
public class UserErrorLogManagementServiceImpl implements UserErrorLogManagementService {

	@Autowired
	private UserErrorLogDAO userErrorLogDAO;
	private Logger logger = LoggerFactory.getLogger(UserErrorLogManagementServiceImpl.class);
	/**
	 * -- Register User Error Log Info --
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
	public void registerUserErrorLogInfo(RequestData<DataUtil> requestData, String resultCode, String resultMessage,
			String ErrorException ) throws Exception {
		try {
			DataUtil registerParam = new DataUtil();
			registerParam.setString("userName", requestData.getHeader().getUserName() );
			registerParam.setString("userType", requestData.getHeader().getUserType());
			registerParam.setString("registerDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
			registerParam.setString("registerTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
			registerParam.setString("url", StringUtils.EMPTY );
			registerParam.setString("ipAddress", StringUtils.EMPTY );
			registerParam.setString("deviceName", StringUtils.EMPTY );
			registerParam.setString("errorCode", resultCode );
			registerParam.setString("errorDescription", resultMessage );
			registerParam.setString("errorCause", ErrorException );
			userErrorLogDAO.registerUserErrorLogInfo(registerParam);
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> register user error log error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e));
		}
	}

}
