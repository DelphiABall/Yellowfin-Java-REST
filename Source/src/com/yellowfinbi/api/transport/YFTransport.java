package com.yellowfinbi.api.transport;

//import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHeaders;
//import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.json.JSONObject;

public class YFTransport {
	
  protected CloseableHttpClient httpClient;
  protected HttpUriRequestBase httpMethod;
		
	public enum httpVerbType {
		Get,
		Post,
		Put,
		Patch,
		Delete
	}
	
	public YFTransport(httpVerbType verbtype, 			
          String base_url,
          String appName,
          String entry_point,
          String security_token,          
          String requestBody,  // Post, Patch, Delete only            
          int timeout) {
		
		super();
		RequestConfig config = RequestConfig.custom()
  			  .setResponseTimeout(timeout * 1000, TimeUnit.MILLISECONDS)
  			  .setConnectionRequestTimeout(timeout * 1000, TimeUnit.MILLISECONDS).build();
  	
		this.httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
  	
  	String url;
  			
  	if (base_url.endsWith("/") == false) {
  		url = base_url+"/"+entry_point;
		} else {
			url = base_url+entry_point;
		}
  	
  	if (verbtype == httpVerbType.Get) { 
	    	// No body!	
	    	this.httpMethod = new HttpGet(url);	    	    
      } else if (verbtype == httpVerbType.Post) {
	    	// Can have a body    	    	
	    	this.httpMethod = buildRequestWithBody(requestBody, new HttpPost(url));    	    		   
      } else if (verbtype == httpVerbType. Put) {
	        // Can have a body
	        this.httpMethod = buildRequestWithBody(requestBody, new HttpPut(url));	         	
		} else if (verbtype == httpVerbType. Patch) {
	        // Can have a body
	        this.httpMethod = buildRequestWithBody(requestBody, new HttpPatch(url));
		} else if (verbtype == httpVerbType. Delete) {
	        // No body!	
	        this.httpMethod = new HttpDelete(url);      	
   	}  // 

      // Set headers
      if (security_token.isBlank()) {
          this.httpMethod.setHeader(HttpHeaders.AUTHORIZATION, appName+" ts=" + YFTransport.epoch() + ", nonce=" + java.util.UUID.randomUUID());
      } else {
          this.httpMethod.setHeader(HttpHeaders.AUTHORIZATION, appName+" ts=" + YFTransport.epoch() + ", nonce=" + java.util.UUID.randomUUID() + ", token=" + security_token);
      }
      this.httpMethod.setHeader(HttpHeaders.ACCEPT, "application/vnd.yellowfin.api-v2+json");
      this.httpMethod.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		
	}
	
  public static String epoch() {    	
      //Instantiating the Date class
   	Date date = new Date();
   	long msec = date.getTime();
   	return Long.toString(msec);
  }
  
  public static int defaultTimeout(){
		return 6;
	}
  
  protected static HttpUriRequestBase buildRequestWithBody(String requestBody, HttpUriRequestBase Request) {
      if (requestBody.isBlank() == false) {
          try {
          	// Create a request body            	
              StringEntity stringEntity = new StringEntity(requestBody);
              Request.setEntity(stringEntity);
          } catch (Exception e) {
          	// log it?
			}    		
  	}
  	return Request;    	
  }
  
	public YFTransportResponse execute () {

		YFTransportResponse result = new YFTransportResponse();        
      try {
          // Send the request
      	CloseableHttpResponse response = httpClient.execute(httpMethod);
      	try {
      		result.setStatus(response.getCode());  
      		if (response.getCode() == HttpStatus.SC_OK) {
	                // take the response body as a json formatted string and get the token
	                String responseData = EntityUtils.toString(response.getEntity());
	                result.setData(responseData);
	            }
	                  	
      	} finally {
      		response.close();            
          }        	
      	return result;        	
      	
      } catch (Exception e) {        	
      	result.setStatus(555);
      	result.setData(e.getMessage());
      	return result;
      }    	
		
	}

}

