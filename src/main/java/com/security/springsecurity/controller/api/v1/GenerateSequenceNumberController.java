/*-----------------------------------------------------------------------------------------
 * NAME : GenerateSequenceNumberController.java
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
package com.security.springsecurity.controller.api.v1;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.service.GenerateSequenceManagementService;
import com.security.springsecurity.service.UserErrorLogManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.SequenceCodeName;
import com.security.springsecurity.util.type.SequenceNoDigit;
import com.security.springsecurity.util.type.YnTypeCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName GenerateSequenceNumberController
* @version   0.1, 2023-07-31
*/

@RestController
@RequestMapping("/api/v1/sequence")
public class GenerateSequenceNumberController {
	
	@Autowired
	private UserErrorLogManagementService userErrorLogManagementService;
	@Autowired
	private GenerateSequenceManagementService generateSequenceManagementService;
	private final static Logger logger = LoggerFactory.getLogger( GenerateSequenceNumberController.class );
	
	/**
	 * -- Generate Commit SeqNo --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param requestData
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/commit")
	public ResponseData<DataUtil> generateCommitSeqNo( @RequestBody RequestData<DataUtil> requestData ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		
		try {
			logger.error(">>>>>>>>>>> Generate Commit SeqNo Start >>>>>>>>>>" );
			DataUtil param = new DataUtil();
			param.setString("seqNoCode", SequenceCodeName.TELEGRAM_SEQNO.getCode() );
			param.setString("seqNoName", SequenceCodeName.TELEGRAM_SEQNO.getName() );
			param.setString("seqNoUniqueCode", DateUtil.getCurrentFormatDate( DateUtil.DATE ) );
			param.setString("userName", "system" );
			param.setLong("seqNoDigit", SequenceNoDigit.EIGHT.getValue() );
			param.setLong("seqNoStart", 1L );
			param.setLong("seqNoEnd", 99999999L );
			String seqNo = generateSequenceManagementService.generateCommitSeqNo(param);
			body.setString("seqNo", seqNo);
			logger.error(">>>>>>>>>>> Generate Commit SeqNo end >>>>>>>>>>"  );
		} catch ( Exception e ) {
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			if ( e.getMessage().length() > 4 ) {
				resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
				resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
			} else {
				ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage(  e.getMessage() );
				resultCode = resultMessageTypeCode.getResultCode();
				resultMessage = resultMessageTypeCode.getResultMessage();
			}
			logger.error(">>>>>>>>>>> Generate Commit SeqNo error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			/*============================================
			 * Every controller must to call this Service 
			 *============================================*/
			userErrorLogManagementService.registerUserErrorLogInfo( requestData , resultCode, resultMessage, ExceptionUtils.getStackTrace(e) );
		}
		
		logger.debug(">>>>>>>>>> getUserInformation end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
		
	}

}
