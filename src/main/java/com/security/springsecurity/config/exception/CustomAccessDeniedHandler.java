package com.security.springsecurity.config.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.response.RenderUtil;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
		ResponseHeader header = new ResponseHeader();
		DataUtil body = new DataUtil();
		header.setSuccessYN( "N" );
		header.setResultCode(ResponseMessageTypeCode.ACCESSDENIED.getResultCode());
		header.setResultMessage(ResponseMessageTypeCode.ACCESSDENIED.getResultMessage());
		RenderUtil.renderJson( response, new ResponseData< DataUtil >(header, body) );
		
	}

}
