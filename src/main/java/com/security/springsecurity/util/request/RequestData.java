package com.security.springsecurity.util.request;

public class RequestData<T> {
	
	private RequestHeader header;
	private T body;
	
	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
	
}
