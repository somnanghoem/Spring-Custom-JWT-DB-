package com.security.springsecurity.util.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Util {
	
	private static final String ALGORITHM_SHA256 		= "SHA-256";
	private final static int RADIX						= 16;
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param plainText
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String encrypt( String plainText ) throws Exception {
		
		byte[] passwordBytes = null;
		MessageDigest sha256 = null;
		byte[] resultBytes = null;
		StringBuilder resultBuffer = new StringBuilder();
		String result = null;
		
		try {
			passwordBytes = plainText.getBytes( StandardCharsets.UTF_8 );
			sha256 = MessageDigest.getInstance( ALGORITHM_SHA256 );
			sha256.reset();
			sha256.update( passwordBytes );
			resultBytes = sha256.digest();
		
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
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param plainText
	 * @param secretKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String encrypt( String plainText, String secretKey ) throws Exception {
		
		String result = null;
		byte[] resultBytes = null;
		byte[] passwordBytes = null;
		MessageDigest sha256 = null;
		StringBuilder resultBuffer = new StringBuilder();
		try {
			
			passwordBytes = plainText.getBytes( StandardCharsets.UTF_8 );
			sha256 = MessageDigest.getInstance( ALGORITHM_SHA256 );
			sha256.reset();
			sha256.update( passwordBytes );
			resultBytes = sha256.digest( secretKey.getBytes( StandardCharsets.UTF_8 ) );
		
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
