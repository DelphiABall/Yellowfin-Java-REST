package com.yellowfinbi.api.transport;

public class YFTransportResponse {

	private String data;
	// Status is the HTTP code or error message
	private Integer status;

	public String getData() {
		return data;
	}
		
	public void setData(String data) {
		this.data = data;
	}	
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
