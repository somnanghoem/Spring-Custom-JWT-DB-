/*-----------------------------------------------------------------------------------------
 * NAME : MenuMasterManagementController.java
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
package com.security.springsecurity.controller.api.v1.web;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.service.v1.web.MenuMasterManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.YnTypeCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName MenuMasterManagementController
* @version   0.1, 2023-08-02
*/
@RestController
@RequestMapping("/api/v1/web/menu-master")
public class MenuMasterManagementController {

	@Autowired
	private MenuMasterManagementService menuMasterManagementService;
	private final static Logger logger = LoggerFactory.getLogger( MenuMasterManagementController.class );
	
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
	@PostMapping("/register")
	public ResponseData<DataUtil> registerMenuMaster( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.debug(">>>>>>>>>> register menu master controller start >>>>>>>>>>");
			menuMasterManagementService.registerMenuMasterInfo( param.getBody() );
			body.setString("successYN", YnTypeCode.YES.getValue());
			logger.debug(">>>>>>>>>> register menu master controller end >>>>>>>>>>");
		} catch ( Exception e ) {
			body = new DataUtil();
			body.setString("successYN", YnTypeCode.NO.getValue());
			successYN = YnTypeCode.NO.getValue();
			if ( e.getMessage().length() > 4 ) {
				resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
				resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
			} else {
				ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage(  e.getMessage() );
				resultCode = resultMessageTypeCode.getResultCode();
				resultMessage = resultMessageTypeCode.getResultMessage();
			}
			logger.error(">>>>>>>>>>> register menu master controller error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
		}
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
}
