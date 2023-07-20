package com.security.springsecurity.util.response;

public class ResponseHeader {
	
	private String successYN = "";
	private String resultCode = "";
	private String resultMessage = "";
	public String getSuccessYN() {
		return successYN;
	}
	
	public ResponseHeader( ) {
		super();
	}
	
	public ResponseHeader(String successYN, String resultCode, String resultMessage) {
		super();
		this.successYN = successYN;
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public void setSuccessYN(String successYN) {
		this.successYN = successYN;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	

}
