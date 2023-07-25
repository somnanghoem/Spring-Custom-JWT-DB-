package com.security.springsecurity.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.springsecurity.config.util.JwtTokenUtil;
import com.security.springsecurity.service.CustomUserDetailService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.response.RenderUtil;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.YnTypeCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, SignatureException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String userName = "";
		String jwtToken = "";
		if ( requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") ) {
			
			jwtToken = requestTokenHeader.substring(7);
			DataUtil body = new DataUtil();
			ResponseHeader header = new ResponseHeader();
			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				header.setSuccessYN( YnTypeCode.NO.getValue() );
				header.setResultCode(ResponseMessageTypeCode.UNABLE_GET_TOKEN.getResultCode());
				header.setResultMessage(ResponseMessageTypeCode.UNABLE_GET_TOKEN.getResultMessage());
				RenderUtil.renderJson( response, new ResponseData< DataUtil >(header, body) );
				return;
			} catch ( ExpiredJwtException e) {
				header.setSuccessYN( YnTypeCode.NO.getValue() );
				header.setResultCode(ResponseMessageTypeCode.TOKEN_EXPIRED.getResultCode());
				header.setResultMessage(ResponseMessageTypeCode.TOKEN_EXPIRED.getResultMessage());
				RenderUtil.renderJson( response, new ResponseData<DataUtil>(header, body) );
				return;
			}catch (SignatureException e) {
				header.setSuccessYN( YnTypeCode.NO.getValue() );
				header.setResultCode(ResponseMessageTypeCode.TOKEN_SIGNATURE_DOES_NOT_MATCH.getResultCode());
				header.setResultMessage(ResponseMessageTypeCode.TOKEN_SIGNATURE_DOES_NOT_MATCH.getResultMessage());
				RenderUtil.renderJson( response, new ResponseData<DataUtil>(header, body) );
				return;
			} catch ( Exception e ) {
				header.setSuccessYN( YnTypeCode.NO.getValue() );
				header.setResultCode(ResponseMessageTypeCode.TOKEN_SIGNATURE_DOES_NOT_MATCH.getResultCode());
				header.setResultMessage(ResponseMessageTypeCode.TOKEN_SIGNATURE_DOES_NOT_MATCH.getResultMessage());
				RenderUtil.renderJson( response, new ResponseData<DataUtil>(header, body) );
				return;
			}
		}
		//Once we get the token validate it.
		if ( StringUtils.isNoneEmpty( userName ) && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.customUserDetailService.loadUserByUsername( userName );
			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken( jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

}
