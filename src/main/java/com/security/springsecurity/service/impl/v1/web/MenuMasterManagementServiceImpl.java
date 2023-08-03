/*-----------------------------------------------------------------------------------------
 * NAME : MenuMasterManagementServiceImpl.java
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
package com.security.springsecurity.service.impl.v1.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.springsecurity.dao.v1.web.MenuMasterManagementDAO;
import com.security.springsecurity.service.GenerateSequenceManagementService;
import com.security.springsecurity.service.v1.web.MenuMasterManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.SeqNoUniqueCode;
import com.security.springsecurity.util.type.SequenceCodeName;
import com.security.springsecurity.util.type.SequenceNoDigit;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName MenuMasterManagementServiceImpl
* @version   0.1, 2023-08-02
*/
@Service
public class MenuMasterManagementServiceImpl implements MenuMasterManagementService {

	@Autowired
	private MenuMasterManagementDAO menuMasterManagementDAO;
	@Autowired
	private GenerateSequenceManagementService generateSequenceManagementService;
	private final static Logger logger = LoggerFactory.getLogger( MenuMasterManagementServiceImpl.class );
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public DataUtil registerMenuMasterInfo(DataUtil param) throws Exception {
		
		try {
			logger.error(">>>>>>>>>> service register menu master start >>>>>>>>>>" );
			// Validate Request Param
			ValidateRegisterParam(param);
			// Generate SeqNo
			Long menuSeqNo = generateMenuSeqNo();
			DataUtil menuParam = new DataUtil();
			menuParam.setLong("menuSeqNo", menuSeqNo);
			menuParam.setString("menuCode", param.getString("menuCode"));
			menuParam.setString("menuDescription", param.getString("menuDescription"));
			menuParam.setString("menuURL", param.getString("menuURL"));
			menuParam.setString("createBy", param.getString("userName"));
			menuParam.setString("updateBy", param.getString("userName"));
			menuParam.setString("registerDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
			menuParam.setString("registerTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
			menuParam.setString("updateDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
			menuParam.setString("updateTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
			try {
				menuMasterManagementDAO.registerMenuMasterInfo(menuParam);
			} catch ( Exception e ) {
				throw new Exception( ResponseMessageTypeCode.REGISTER_MENU_MASTER_ERROR.getResultCode() );
			}
			logger.error(">>>>>>>>>> service register menu master end >>>>>>>>>>" );
		} catch ( Exception e) {
			logger.error(">>>>>>>>>> service register menu master param >>>>>>>>>>" + param );
			logger.error(">>>>>>>>>> service register menu master error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e));
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
	private void ValidateRegisterParam( DataUtil param ) throws Exception {
		if ( StringUtils.isEmpty( param.getString("menuCode") ) || StringUtils.isBlank( param.getString("menuCode") ) ) {
			throw new Exception( ResponseMessageTypeCode.MENU_CODE_EMPTY.getResultCode() );
		} else if ( StringUtils.isEmpty( param.getString("menuDescription") ) || StringUtils.isBlank( param.getString("menuDescription") ) ) {
			throw new Exception( ResponseMessageTypeCode.MENU_DESCRIPTION_EMPTY.getResultCode() );
		} else if ( StringUtils.isEmpty( param.getString("menuURL") ) || StringUtils.isBlank( param.getString("menuURL") ) ) {
			throw new Exception( ResponseMessageTypeCode.MENU_URL_EMPTY.getResultCode() );
		} 
	}
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	private long generateMenuSeqNo() throws Exception {
		DataUtil param = new DataUtil();
		param.setString("seqNoCode", SequenceCodeName.MENU_MASTER_SEQNO.getCode() );
		param.setString("seqNoName", SequenceCodeName.MENU_MASTER_SEQNO.getName() );
		param.setString("seqNoUniqueCode", SeqNoUniqueCode.MENU_MASTER );
		param.setString("userName", "system" );
		param.setLong("seqNoDigit", SequenceNoDigit.TEN.getValue() );
		param.setLong("seqNoStart", 10L );
		param.setLong("seqNoEnd", 999999099L );
		String seqNo = generateSequenceManagementService.generateCommitSeqNo(param);
		return Long.valueOf( seqNo ).longValue();
	}

}
