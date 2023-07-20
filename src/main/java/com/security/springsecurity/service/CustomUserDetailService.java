package com.security.springsecurity.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.springsecurity.util.data.DataUtil;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private FakeUserInformation fakeUserInformation;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		DataUtil userInfo = new DataUtil();
		try {
			userInfo = fakeUserInformation.getUseInfoByUserName ( username );
		} catch ( Exception e) {
			throw new UsernameNotFoundException("User not found + " + username );
		}
		if ( userInfo != null ) {
			String password = bCryptPasswordEncoder.encode( userInfo.getString("password") );
			return new User( username, String.valueOf( password ), new ArrayList<>() );
		} else {
			throw new UsernameNotFoundException("User not found + " + username );
		}
	}

}
