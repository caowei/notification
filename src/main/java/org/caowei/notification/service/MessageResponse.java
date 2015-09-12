package org.caowei.notification.service;

public class MessageResponse {
	public static final int RESPONSE_NOT_SEND = 0;
	public static final int RESPONSE_SUCCESS = 200;
	
	
	private int status;
	private String responseBody;

	public MessageResponse() {
		super();
		this.status = RESPONSE_NOT_SEND;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	
}
