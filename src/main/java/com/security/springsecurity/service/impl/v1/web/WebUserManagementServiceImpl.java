/*-----------------------------------------------------------------------------------------
 * NAME : WebUserManagementServiceImpl.java
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
package com.security.springsecurity.service.impl.v1.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.security.springsecurity.dao.UserInfoDAO;
import com.security.springsecurity.service.v1.web.WebUserManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.encryption.Sha256Util;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.UserStatusCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName WebUserManagementServiceImpl
* @version   0.1, 2023-08-03
*/
@Service
public class WebUserManagementServiceImpl implements WebUserManagementService {

	@Autowired
	private UserInfoDAO userInfoDAO;
	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	private Logger logger = LoggerFactory.getLogger( WebUserManagementServiceImpl.class );
	
	/**
	 * -- Register User Information --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * 		String	userName
	 * 		String	userType
	 * 		String	userPassword
	 * 		String	masterUserName
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public DataUtil registerUserInformation(DataUtil param) throws Exception {
		
		// Open New Transaction
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction( new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW ) );
		try {
			logger.error(">>>>>>>>>> Register user information service start >>>>>>>>>>");
			// validate param
			validateRegsiterParam(param);
			// prepare data register user infomation
			String password = Sha256Util.encrypt( param.getString("userPassword"), param.getString("userName") );
			DataUtil registerParam = new DataUtil();
			registerParam.setString("userName", param.getString("userName"));
			registerParam.setString("userType", param.getString("userType"));
			registerParam.setString("userStatus", UserStatusCode.NORMAL.getValue() );
			registerParam.setString("userPassword", password );
			registerParam.setString("passwordChangeDate", StringUtils.EMPTY );
			registerParam.setString("passwordChangeTime", StringUtils.EMPTY );
			registerParam.setString("firstLoginDate", StringUtils.EMPTY );
			registerParam.setString("firstLoginTime", StringUtils.EMPTY );
			registerParam.setString("lastLoginDate", StringUtils.EMPTY );
			registerParam.setString("lastLoginTime", StringUtils.EMPTY );
			registerParam.setString("masterUserName", StringUtils.trimToEmpty(param.getString("masterUserName")) );
			registerParam.setString("employeeID", StringUtils.EMPTY );
			registerParam.setString("createBy", param.getString("userName"));
			registerParam.setString("updateBy", param.getString("userName"));
			registerParam.setString("registerDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
			registerParam.setString("registerTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
			registerParam.setString("updateDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
			registerParam.setString("updateTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
			try {
				userInfoDAO.registerUserInfo(registerParam);
			} catch (Exception e) {
				logger.error(">>>>>>>>>> Register user information service error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
				throw new Exception( ResponseMessageTypeCode.REGISTER_USER_INFO_ERROR.getResultCode() );
			}
			// prepare register user detail
			// prepare register user menu
			logger.error(">>>>>>>>>> Register user information service end >>>>>>>>>>");
			platformTransactionManager.commit(transactionStatus);
		} catch ( Exception e ) {
			platformTransactionManager.rollback(transactionStatus);
			throw e;
		}
		
		return new DataUtil();
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	private void validateRegsiterParam( DataUtil param ) throws Exception {
		
	}

}
