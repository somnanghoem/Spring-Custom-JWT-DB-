package com.security.springsecurity.dao;

import org.apache.ibatis.annotations.Mapper;

import com.security.springsecurity.util.data.DataUtil;

@Mapper
public interface JwtUserDAO {
	
	public DataUtil getRequestTokenByUserName(String userName);
	public long updateUserTokenInfo( DataUtil param );
	public long registerUserToken( DataUtil param );

}
