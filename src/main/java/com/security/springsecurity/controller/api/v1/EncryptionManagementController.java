/*-----------------------------------------------------------------------------------------
 * NAME : EncryptionManagementController.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-26   Hoem Somnang          creation
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

import com.security.springsecurity.service.UserErrorLogManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.encryption.RSAKeyPair;
import com.security.springsecurity.util.encryption.RSAUtil;
import com.security.springsecurity.util.encryption.Sha256Util;
import com.security.springsecurity.util.encryption.Sha512Util;
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
* @logicalName EncryptionManagementController
* @version   0.1, 2023-07-26
*/
@RestController
@RequestMapping("/api/v1/encryption")
public class EncryptionManagementController {
	
	@Autowired
	private UserErrorLogManagementService userErrorLogManagementService;
	private final static Logger logger = LoggerFactory.getLogger( EncryptionManagementController.class );
	
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
	@PostMapping("/rsa")
	public ResponseData<DataUtil> rSAEncryption( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		
		try {
			logger.error(">>>>>>>>>>> using RSA encryption start >>>>>>>>>>");
			
			RSAKeyPair keypair = RSAUtil.generateRSAKey();
			String encrypt = RSAUtil.encryptRSA( "somnang", keypair.getPublicKey() );
			String decrypt = RSAUtil.decryptRSA( encrypt, keypair.getPrivateKey() );
			// Set Body Data
			body.setString("publicKey", keypair.getPublicKey() );
			body.setString("privaeKey", keypair.getPrivateKey() );
			body.setString("encrypt", encrypt);
			body.setString("decrypt", decrypt);
			
		} catch ( Exception e ) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>> using RSA encryption error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			successYN = YnTypeCode.NO.getValue();
			if ( e.getMessage().length() > 4 ) {
				resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
				resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
			} else {
				ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage(  e.getMessage() );
				resultCode = resultMessageTypeCode.getResultCode();
				resultMessage = resultMessageTypeCode.getResultMessage();
			}
			/*============================================
			 * Every controller must to call this Service 
			 *============================================*/
			userErrorLogManagementService.registerUserErrorLogInfo( param, resultCode, resultMessage, ExceptionUtils.getStackTrace(e) );
		}
		logger.error(">>>>>>>>>>> using RSA encryption end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
		
	}
	
	/**
	 * -- Sha512 Encryption --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/sha512")
	public ResponseData<DataUtil> sha512Encryption( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		
		try {
			logger.error(">>>>>>>>>>> using sha512 encryption start >>>>>>>>>>");
			String encrypt = Sha512Util.encrypt( "somnang" );
			String encrptWithSecretKey = Sha512Util.encrypt( "somnang", "nana" );
			body.setString("encrypt", encrypt);
			body.setString("encrptWithSecretKey", encrptWithSecretKey );
		} catch ( Exception e ) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>> using sha512 encryption error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			successYN = YnTypeCode.NO.getValue();
			if ( e.getMessage().length() > 4 ) {
				resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
				resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
			} else {
				ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage(  e.getMessage() );
				resultCode = resultMessageTypeCode.getResultCode();
				resultMessage = resultMessageTypeCode.getResultMessage();
			}
			/*============================================
			 * Every controller must to call this Service 
			 *============================================*/
			userErrorLogManagementService.registerUserErrorLogInfo( param, resultCode, resultMessage, ExceptionUtils.getStackTrace(e) );
		}
		logger.error(">>>>>>>>>>> using sha512 encryption end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
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
	@PostMapping("/sha256")
	public ResponseData<DataUtil> sha256Encryption( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		
		try {
			logger.error(">>>>>>>>>>> using sha256 encryption start >>>>>>>>>>");
			String encrypt = Sha256Util.encrypt( "somnang" );
			String encrptWithSecretKey = Sha256Util.encrypt( "somnang", "nana" );
			body.setString("encrypt", encrypt);
			body.setString("encrptWithSecretKey", encrptWithSecretKey );
		} catch ( Exception e ) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>> using sha256 encryption error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			successYN = YnTypeCode.NO.getValue();
			if ( e.getMessage().length() > 4 ) {
				resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
				resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
			} else {
				ResponseMessageTypeCode resultMessageTypeCode = ResponseMessageTypeCode.getResultMessage(  e.getMessage() );
				resultCode = resultMessageTypeCode.getResultCode();
				resultMessage = resultMessageTypeCode.getResultMessage();
			}
			/*============================================
			 * Every controller must to call this Service 
			 *============================================*/
			userErrorLogManagementService.registerUserErrorLogInfo( param, resultCode, resultMessage, ExceptionUtils.getStackTrace(e) );
		}
		logger.error(">>>>>>>>>>> using sha256 encryption end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}

}
