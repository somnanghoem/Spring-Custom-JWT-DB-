package com.security.springsecurity.util.encryption;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	private final static String ALGORITHM_AES					= "AES";
	private final static int DEFAULT_KEY_SIZE 					= 128;
	private final static String ALGORITHM_AES_TRANSFORMATION 	= "AES/CBC/PKCS5Padding";
	

	/**
	 * @return
	 * @throws Exception
	 */
	public static String makeRandomKey() throws Exception {
		
		String randomKey = "";
		try {
			randomKey = makeRandomKey( DEFAULT_KEY_SIZE );
		} catch ( Exception e ) {
			throw e;
		}
		return randomKey;
		
	}
	
	/**
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String makeRandomKey( int size ) throws Exception {
		
		SecureRandom random;
		KeyGenerator generator;
		String aesRandomKey = null;
		
		try {
			random = new SecureRandom();
			generator = KeyGenerator.getInstance( ALGORITHM_AES );
			generator.init(size, random );
			final SecretKey key = generator.generateKey();
			aesRandomKey = Base64.getEncoder().encodeToString( key.getEncoded() ).replaceAll( "\n", "" );
			
		} catch (Exception e) {
			throw e;
		}
		return aesRandomKey;
		
	}
	
	/**
	 * @param value
	 * @param encodedBase64Key
	 * @param encodedBase64Iv
	 * @return
	 * @throws MaruException
	 */
	public static String encrypt( String value, String encodedBase64Key, String encodedBase64Iv ) throws Exception {
		
		byte[] byteValue;
		byte[] encodeKey;
		Key key;
		byte[] encodeIv;
		IvParameterSpec ivParameter;
		byte[] encrypted;
		String encryptedValue = null;
		
		try {
			byteValue = value.getBytes( StandardCharsets.UTF_8 );
			
			encodeKey = Base64.getDecoder().decode( encodedBase64Key );
			key = new SecretKeySpec( encodeKey, 0,  encodeKey.length, ALGORITHM_AES );
			
			encodeIv = Base64.getDecoder().decode( encodedBase64Iv );
			ivParameter = new IvParameterSpec( encodeIv );
			
			Cipher cipher = Cipher.getInstance( ALGORITHM_AES_TRANSFORMATION );
			cipher.init( Cipher.ENCRYPT_MODE, key, ivParameter );
			encrypted = cipher.doFinal( byteValue );
			
			encryptedValue = Base64.getEncoder().encodeToString( encrypted ).replaceAll( "\n", "" );
		} catch ( NoSuchAlgorithmException e ) {
			throw e;
		} catch ( NoSuchPaddingException e ) {
			throw e;
		} catch ( InvalidKeyException e ) {
			throw e;
		} catch ( InvalidAlgorithmParameterException e ) {
			throw e;
		} catch ( IllegalBlockSizeException e ) {
			throw e;
		} catch ( BadPaddingException e ) {
			throw e;
		} catch ( Exception e ) {
			throw e;
		}
		
		return encryptedValue;
	}
}
