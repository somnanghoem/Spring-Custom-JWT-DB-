/**
 * 
 */
package com.security.springsecurity.controller.token;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.config.util.JwtTokenUtil;
import com.security.springsecurity.dao.JwtUserDAO;
import com.security.springsecurity.service.FakeUserInformation;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;

/**
 * @author Hoem Somnang
 *
 */
@RestController
@RequestMapping("/api/v1/get-token")
public class TokenEndPointController {
	
	@Autowired
	private JwtUserDAO jwtUserDAO;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private FakeUserInformation fakeUserInformation;
	/**
	 * @param requestData
	 * 			String	userName
	 * 			String	password
	 * 			String	userType
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	public ResponseData< DataUtil > generateUserToken( @RequestBody RequestData< DataUtil > requestData, HttpServletRequest request ) throws Exception {
		
		String successYN = "Y";
		DataUtil body = new DataUtil();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		try {
			// Validate request param
			DataUtil validateData = this.validateGenerateUserTokenParam( requestData );
			if ( "Y".equals( validateData.getString("validYN")) ) {
				
				Date date = new Date(); 
				String token = StringUtils.EMPTY;
				String ipAddress = request.getHeader("X-FORWARDED-FOR");  
				if (ipAddress == null) {  
					ipAddress = request.getRemoteAddr();  
				}
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");  
				
				DataUtil userTokenInfo = jwtUserDAO.getRequestTokenByUserName( requestData.getBody().getString("userName") );
				String password =  bCryptPasswordEncoder.encode( requestData.getBody().getString("password") );
				UserDetails userDetails = new User( requestData.getBody().getString("userName") ,  password , new ArrayList<>() );
				
				if ( userTokenInfo != null ) {
					long currentDateTime = Long.parseLong( dateFormatter.format( date ) );
					long tokenExpiredDateTime = Long.parseLong( userTokenInfo.getString("expirationDate") );
					long expired = currentDateTime - tokenExpiredDateTime;
					
					// in case token not yet expire
					if ( expired < 0 ) {
						token = userTokenInfo.getString("token");
						body.setString("token", token );
						body.setString("userName", userTokenInfo.getString("userName") );
						body.setString("issueDate", userTokenInfo.getString("issueDate") );
						body.setString("expirationDate", userTokenInfo.getString("expirationDate") );
						body.setString("role", StringUtils.EMPTY );
					}
					// in case token expired
					else {
						token = jwtTokenUtil.generateToken(userDetails);
						body.setString("token", token );
						body.setString("userName", jwtTokenUtil.getUsernameFromToken(token) );
						body.setString("issueDate", dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)) );
						body.setString("expirationDate", dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)) );
						body.setString("role", StringUtils.EMPTY );
						// data update
						DataUtil userTokenUpdate = new DataUtil();
						userTokenUpdate.setString("token", token );
						userTokenUpdate.setString("userName", jwtTokenUtil.getUsernameFromToken(token) );
						userTokenUpdate.setString("issueDate", dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)) );
						userTokenUpdate.setString("expirationDate", dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)) );
						userTokenUpdate.setString("remoteIP", ipAddress );
						//Update Token Info
						jwtUserDAO.updateUserTokenInfo( userTokenUpdate );
					}
				} else {
					
					token = jwtTokenUtil.generateToken(userDetails);
					body.setString("token", token );
					body.setString("userName", jwtTokenUtil.getUsernameFromToken(token) );
					body.setString("issueDate", dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)) );
					body.setString("expirationDate", dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)) );
					body.setString("role", StringUtils.EMPTY );
					// data update
					DataUtil userTokenRegister = new DataUtil();
					userTokenRegister.setString("token", token );
					userTokenRegister.setString("userName", jwtTokenUtil.getUsernameFromToken(token) );
					userTokenRegister.setString("issueDate", dateFormatter.format(jwtTokenUtil.getIssuedAtDateFromToken(token)) );
					userTokenRegister.setString("expirationDate", dateFormatter.format(jwtTokenUtil.getExpirationDateFromToken(token)) );
					userTokenRegister.setString("remoteIP", ipAddress );
					userTokenRegister.setString("userType", "01" );
					//Update Token Info
					jwtUserDAO.registerUserToken( userTokenRegister );
				}
				
			} else {
				successYN = "N";
				resultCode = validateData.getString("resultCode");
				resultMessage = validateData.getString("resultMessage");
			}
		} catch ( Exception e ) {
			body = new DataUtil();
			successYN = "N";
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		// set header
		ResponseHeader header = new ResponseHeader( successYN, resultCode, resultMessage );
		return new ResponseData< DataUtil >( header, body );
	
	}
	
	/**
	 * @param requestData
	 * @return
	 */
	private DataUtil validateGenerateUserTokenParam( RequestData< DataUtil > requestData ) {
		
		DataUtil result = new DataUtil();
		String validYN = "Y";
		String resultCode = StringUtils.EMPTY;
		String resultMessage = StringUtils.EMPTY;
		if ( StringUtils.isBlank( requestData.getBody().getString("userName") )
				|| StringUtils.isEmpty( requestData.getBody().getString("userName") )  ) {
			validYN = "N";
			resultCode = ResponseMessageTypeCode.USERNAME_EMPTY.getResultCode();
			resultMessage = ResponseMessageTypeCode.USERNAME_EMPTY.getResultMessage();
		} else if ( StringUtils.isBlank( requestData.getBody().getString("password") )
				|| StringUtils.isEmpty( requestData.getBody().getString("password") )  ) {
			validYN = "N";
			resultCode = ResponseMessageTypeCode.PASSWORD_EMPTY.getResultCode();
			resultMessage = ResponseMessageTypeCode.PASSWORD_EMPTY.getResultMessage();
		} else if ( StringUtils.isBlank( requestData.getBody().getString("userType") )
				|| StringUtils.isEmpty( requestData.getBody().getString("userType") )  ) {
			validYN = "N";
			resultCode = ResponseMessageTypeCode.USERTYPE_EMPTY.getResultCode();
			resultMessage = ResponseMessageTypeCode.USERTYPE_EMPTY.getResultMessage();
		} else if ( StringUtils.isNoneEmpty( requestData.getBody().getString("userName") ) && StringUtils.isNotEmpty( requestData.getBody().getString("password") ) ) {
			DataUtil userParam = new DataUtil();
			userParam.setString("userName", requestData.getBody().getString("userName"));
			userParam.setString("password", requestData.getBody().getString("password") );
			try {
				fakeUserInformation.getUseInfoByUserNamePassword( userParam );
			} catch ( Exception e ) {
				validYN = "N";
				resultCode = ResponseMessageTypeCode.USER_NOT_FOUND.getResultCode();
				resultMessage = ResponseMessageTypeCode.USER_NOT_FOUND.getResultMessage();
			}
		}
		result.setString("validYN", validYN);
		result.setString("resultCode", resultCode);
		result.setString("resultMessage", resultMessage);
		return result;
	}

}
