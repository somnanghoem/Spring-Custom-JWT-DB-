package com.security.springsecurity.service;

import org.springframework.stereotype.Service;

import com.security.springsecurity.util.data.DataUtil;

@Service
public class FakeUserInformation {
	
	
	public DataUtil getUseInfoByUserNamePassword( DataUtil param ) throws Exception {
		
		DataUtil outputData = new DataUtil();
		if ( "somnang".equals( param.getString( "userName" ) ) 
				&& "123456".equals( param.getString( "password" ) ) ) {
			outputData = new DataUtil( param );	
		} else {
			throw new Exception( "Not found user" );
		}
		
		return outputData;
	}
	
	
	public DataUtil getUseInfoByUserName( String userName ) throws Exception {
		
		DataUtil outputData = new DataUtil();
		if ( "somnang".equals( userName ) ) {
			outputData.setString("userName", userName);
			outputData.setString("password", "123456");
		} else {
			throw new Exception( "Not found user" );
		}
		
		return outputData;
	}

}
