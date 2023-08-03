package com.security.springsecurity.controller.api.v1;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.controller.token.v1.TokenEndPointController;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.data.ListDataUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.YnTypeCode;

@RestController
@RequestMapping("/api/v1/user")
public class UserManagementController {

	private static final Logger logger  = LoggerFactory.getLogger( TokenEndPointController.class);
	
	
	/**
	 * -- Get User Information --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return outputData
	 * 		String	name
	 * 		String	department
	 * 		String	position
	 * 		String	phone
	 * 		String	email
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/get-user")
	public ResponseData<DataUtil> getUserInformation( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.debug(">>>>>>>>>> getUserInformation start >>>>>>>>>>");
			// Fake Data
			body.setString("name", "Ching");
			body.setString("department", "HR");
			body.setString("position", "Officer");
			body.setString("phone", "099876545");
			body.setString("email", "ching@hr.com.kh");
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> retrieve user infor error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.debug(">>>>>>>>>> getUserInformation end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
	
	/**
	 * -- Get List User Information --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return outputData
	 * 		ListDataUtil items
	 * 			String	name
	 * 			String	department
	 * 			String	position
	 * 			String	phone
	 * 			String	email		
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/get-list-user")
	public ResponseData<DataUtil> getListUserInformation( @RequestBody RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.debug(">>>>>>>>>> getListUserInformation start >>>>>>>>>>");
			// Fake Data
			ListDataUtil items = new ListDataUtil();
			DataUtil item = new DataUtil();
			item.setString("name", "Ching");
			item.setString("department", "HR");
			item.setString("position", "Officer");
			item.setString("phone", "099876545");
			item.setString("email", "ching@hr.com.kh");
			for ( int i = 0 ; i< 10 ; i ++ ) {
				items.add(item);
			}
			body.setListDataUtil("items", items);
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> retrieve list user infor error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.debug(">>>>>>>>>> getListUserInformation end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
}
