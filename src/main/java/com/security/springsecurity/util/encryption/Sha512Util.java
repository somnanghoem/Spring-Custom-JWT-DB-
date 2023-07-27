/*-----------------------------------------------------------------------------------------
 * NAME : Sha512Util.java
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
package com.security.springsecurity.util.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName Sha512Util
* @version   0.1, 2023-07-26
*/

public class Sha512Util {

	private static final String ALGORITHM_SHA512 		= "SHA-512";
	private final static int RADIX						= 16;
	
	/**
	 * -- encrypt --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param plainText
	 * @return
	 * @throws MaruException
	 * @exception 
	 * @fullPath 
	 */
	public static String encrypt( String plainText ) throws Exception {
		
		String result = null;
		byte[] resultBytes = null;
		byte[] passwordBytes = null;
		MessageDigest sha512 = null;
		StringBuilder resultBuffer = new StringBuilder();
		
		try {
			passwordBytes = plainText.getBytes( StandardCharsets.UTF_8 );
			sha512 = MessageDigest.getInstance( ALGORITHM_SHA512 );
			sha512.reset();
			sha512.update( passwordBytes );
			resultBytes = sha512.digest();
		
			for ( int i = 0; i < resultBytes.length; i++ ) {
				resultBuffer.append( Integer.toString( ( resultBytes[i] & 0xf0 ) >> 4, RADIX ) );
				resultBuffer.append( Integer.toString( resultBytes[i] & 0x0f, RADIX ) );
			}
			result = resultBuffer.toString();
		} catch ( NoSuchAlgorithmException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * -- encrypt --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param plainText
	 * @param secretKey
	 * @return
	 * @throws MaruException
	 * @exception 
	 * @fullPath 
	 */
	public static String encrypt( String plainText, String secretKey ) throws Exception {
		
		String result = null;
		byte[] resultBytes = null;
		byte[] passwordBytes = null;
		MessageDigest sha512 = null;
		StringBuilder resultBuffer = new StringBuilder();
		
		try {
			
			passwordBytes = plainText.getBytes( StandardCharsets.UTF_8 );
			sha512 = MessageDigest.getInstance( ALGORITHM_SHA512 );
			sha512.reset();
			sha512.update( passwordBytes );
			resultBytes = sha512.digest( secretKey.getBytes( StandardCharsets.UTF_8 ) );
		
			for ( int i = 0; i < resultBytes.length; i++ ) {
				resultBuffer.append( Integer.toString( ( resultBytes[i] & 0xf0 ) >> 4, RADIX ) );
				resultBuffer.append( Integer.toString( resultBytes[i] & 0x0f, RADIX ) );
			}
			result = resultBuffer.toString();
		} catch ( NoSuchAlgorithmException e ) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
}
