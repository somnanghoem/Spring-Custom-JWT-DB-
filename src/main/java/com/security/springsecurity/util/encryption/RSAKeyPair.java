package com.security.springsecurity.util.encryption;

public class RSAKeyPair {
	
	private String publicKey = "";
	private String privateKey = "";
	private String publicKeyModulus = "";
	private String publicKeyExponent = "";

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKeyModulus() {
		return publicKeyModulus;
	}

	public void setPublicKeyModulus(String publicKeyModulus) {
		this.publicKeyModulus = publicKeyModulus;
	}

	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}

	public void setPublicKeyExponent(String publicKeyExponent) {
		this.publicKeyExponent = publicKeyExponent;
	}

}
