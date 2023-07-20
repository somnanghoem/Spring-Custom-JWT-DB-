package com.security.springsecurity.config.exception;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.response.RenderUtil;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	
	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		String authorization = request.getHeader("Authorization");
		ResponseHeader header = new ResponseHeader();
		DataUtil body = new DataUtil();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
		if ( authorization == null ) {
			header.setSuccessYN( "N" );
			header.setResultCode(ResponseMessageTypeCode.AUTHORIZATION_EMPTY.getResultCode());
			header.setResultMessage(ResponseMessageTypeCode.AUTHORIZATION_EMPTY.getResultMessage());
			RenderUtil.renderJson( response, new ResponseData< DataUtil >(header, body) );
			return;
		} else {
			header.setSuccessYN( "N" );
			header.setResultCode(ResponseMessageTypeCode.UNAUTHORIZED.getResultCode());
			header.setResultMessage(ResponseMessageTypeCode.UNAUTHORIZED.getResultMessage());
			RenderUtil.renderJson( response, new ResponseData<DataUtil>(header, body) );
			return;
		}

	}
}
