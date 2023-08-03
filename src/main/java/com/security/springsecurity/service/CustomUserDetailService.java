package com.security.springsecurity.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.encryption.Sha256Util;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserInfoManagementService userInfoManagementService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		DataUtil userInfo = new DataUtil();
		try {
			userInfo = userInfoManagementService.getUseInfoByUserName ( username );
		} catch ( Exception e) {
			throw new UsernameNotFoundException("User not found + " + username );
		}
		if ( userInfo != null ) {
			try {
				String password = Sha256Util.encrypt( userInfo.getString("userPassword") , username );
				return new User( username, String.valueOf( password ), new ArrayList<>() );
			} catch ( Exception e) {
				throw new UsernameNotFoundException("User not found + " + username );
			}
			
		} else {
			throw new UsernameNotFoundException("User not found + " + username );
		}
	}

}
