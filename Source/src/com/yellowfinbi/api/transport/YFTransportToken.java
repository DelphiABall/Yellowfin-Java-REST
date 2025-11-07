package com.yellowfinbi.api.transport;

import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONObject;

public class YFTransportToken extends YFTransport {

	public YFTransportToken(httpVerbType verbtype, String base_url, String appName, String entry_point, String security_token,
			String requestBody, int timeout) {
		super(verbtype, base_url, appName, entry_point, security_token, requestBody, timeout);
	}
	
	public YFTokenResponse executeToken() {
		YFTransportResponse data = super.execute();
		YFTokenResponse token = new YFTokenResponse();
		token.setData(data.getData());
		token.setStatus(data.getStatus());
		//if (token.status == Integer.toString(HttpStatus.SC_OK)) {
		if (token.getStatus() == HttpStatus.SC_OK) {
			JSONObject jsonResponseObject = new JSONObject(data.getData());
		    token.setToken(jsonResponseObject.getString("securityToken"));		    
		    		    
		} else { // Pass on the error as is...
			token.setToken(data.getData());
		}
		
		return token;
       
	}

}