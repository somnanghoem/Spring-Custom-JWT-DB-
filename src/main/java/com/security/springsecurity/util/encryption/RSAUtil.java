/**
 * 
 */
package com.security.springsecurity.util.encryption;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
/**
 * @author Hoem Somnang
 *
 */
public class RSAUtil {

	private static int RADIX = 16;
	private static int DEFAULT_KEY_SIZE = 1024;
	private static String ALGORITHM_RSA = "RSA";
	private static String ALGORITHM_RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static RSAKeyPair generateRSAKey() throws Exception {

		try {

			RSAKeyPair keyPair = new RSAKeyPair();
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
			generator.initialize(DEFAULT_KEY_SIZE);
			KeyFactory keyFactory = null;

			KeyPair pair = generator.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();

			String privateKeyToText = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			String publicKeytoText = Base64.getEncoder().encodeToString(publicKey.getEncoded());

			keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			String publicKeyModulus = publicSpec.getModulus().toString(RADIX);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(RADIX);

			keyPair.setPrivateKey(privateKeyToText);
			keyPair.setPublicKey(publicKeytoText);
			keyPair.setPublicKeyExponent(publicKeyExponent);
			keyPair.setPublicKeyModulus(publicKeyModulus);

			return keyPair;

		} catch (Exception e) {
			System.err.println(">>>>>>>>>> generate RSA key error >>>>>>>>>>" + e.getMessage());
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param size
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static RSAKeyPair generateRSAKey(int size) throws Exception {

		try {

			RSAKeyPair keyPair = new RSAKeyPair();
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
			generator.initialize(size);
			KeyFactory keyFactory = null;

			KeyPair pair = generator.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();

			String privateKeyToText = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			String publicKeytoText = Base64.getEncoder().encodeToString(publicKey.getEncoded());

			keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			String publicKeyModulus = publicSpec.getModulus().toString(RADIX);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(RADIX);

			keyPair.setPrivateKey(privateKeyToText);
			keyPair.setPublicKey(publicKeytoText);
			keyPair.setPublicKeyExponent(publicKeyExponent);
			keyPair.setPublicKeyModulus(publicKeyModulus);

			return keyPair;

		} catch (Exception e) {
			System.err.println(">>>>>>>>>> generate RSA key error >>>>>>>>>>" + e.getMessage());
			throw e;
		}
	}


	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param publicKeyModulus
	 * @param publicKeyExponent
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static PublicKey generatePublicKey(String publicKeyModulus, String publicKeyExponent) throws Exception {
		try {

			BigInteger modulus = new BigInteger(publicKeyModulus, RADIX);
			BigInteger exponent = new BigInteger(publicKeyExponent, RADIX);
			RSAPublicKeySpec publicKey = new RSAPublicKeySpec(modulus, exponent);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			PublicKey key = keyFactory.generatePublic(publicKey);

			return key;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param base64PublicKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static PublicKey toPublicKey(String base64PublicKey) throws Exception {

		try {
			PublicKey key = null;
			byte[] bytePublicKey = null;
			KeyFactory keyFactory = null;
			X509EncodedKeySpec publicKey = null;

			bytePublicKey = Base64.getDecoder().decode(base64PublicKey);
			keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			publicKey = new X509EncodedKeySpec(bytePublicKey);
			key = keyFactory.generatePublic(publicKey);
			return key;
		} catch (Exception e) {
			throw e;
		}
	}


	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param base64PrivateKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static PrivateKey toPrivateKey(String base64PrivateKey) throws Exception {

		try {

			PrivateKey key = null;
			byte[] bytePrivateKey = null;
			KeyFactory keyFactory = null;
			PKCS8EncodedKeySpec privateKey = null;

			bytePrivateKey = Base64.getDecoder().decode(base64PrivateKey);
			keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			privateKey = new PKCS8EncodedKeySpec(bytePrivateKey);
			key = keyFactory.generatePrivate(privateKey);
			return key;

		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param publicKeySpec
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String getPublicKeyModulus(RSAPublicKeySpec publicKeySpec) throws Exception {
		try {
			return publicKeySpec.getModulus().toString(RADIX);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String getPublicKeyModulus(PrivateKey privateKey) throws Exception {
		try {
			return ((RSAPrivateCrtKey) privateKey).getModulus().toString(RADIX);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String getPublicExponent(PrivateKey privateKey) throws Exception {
		try {
			return ((RSAPrivateCrtKey) privateKey).getPublicExponent().toString(RADIX);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param publicSpec
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String getPublicExponent(RSAPublicKeySpec publicSpec) throws Exception {
		try {
			return publicSpec.getPublicExponent().toString(RADIX);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static RSAPublicKeySpec getPublicKeySpec(PublicKey publicKey) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			return publicSpec;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param base64PrivateKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String generatePublicKeyByPrivateKey(String base64PrivateKey) throws Exception {
		
		PrivateKey privateKey = toPrivateKey(base64PrivateKey);
		String modulus = getPublicKeyModulus(privateKey);
		String exponent = getPublicExponent(privateKey);
		PublicKey publicKey = generatePublicKey(modulus, exponent);
		String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		
		return publicKeyStr;
	}

	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param plainText
	 * @param base64PublicKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String encryptRSA(String plainText, String base64PublicKey) throws Exception {

		try {

			byte[] encrypt = null;
			String encryptString = "";
			byte[] byteText = plainText.getBytes(StandardCharsets.UTF_8);
			PublicKey publicKey = toPublicKey(base64PublicKey);

			Cipher cipher = Cipher.getInstance(ALGORITHM_RSA_TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encrypt = cipher.doFinal(byteText);
			encryptString = Base64.getEncoder().encodeToString(encrypt);
			return encryptString;
		} catch (Exception e) {
			System.err.println(">>>>>>>>>> encrypt RSA error >>>>>>>>>>" + e.getMessage());
			throw e;
		}

	}


	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param encodeText
	 * @param base64PrivateKey
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	public static String decryptRSA(String encodeText, String base64PrivateKey) throws Exception {

		try {
			
			byte[] decrypt = null;
			String decryptString = "";
			byte[] encryptBytes = Base64.getDecoder().decode(encodeText);
			PrivateKey privateKey = toPrivateKey(base64PrivateKey);

			Cipher cipher = Cipher.getInstance(ALGORITHM_RSA_TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decrypt = cipher.doFinal(encryptBytes);

			decryptString = new String(decrypt, StandardCharsets.UTF_8);
			return decryptString;

		} catch (Exception e) {
			System.err.println(">>>>>>>>>> decrypt error >>>>>>>>>> " + e.getMessage());
			throw e;
		}

	}
}
