package com.yellowfinbi.api.methods;

import org.json.JSONObject;

import com.yellowfinbi.api.transport.YFTokenResponse;
import com.yellowfinbi.api.transport.YFTransport;
import com.yellowfinbi.api.transport.YFTransportToken;

public class YFTokenRequests {
	
	public static YFTokenResponse GetRefreshToken(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName, 			
			/*@NotBlank*/ String yf_loginID, 
			/*@NotBlank*/ String yf_loginPassword,
			/*@NotBlank*/ String orgID) {	    

	    // https://developers.yellowfinbi.com/dev/api-docs/current/#tag/refresh-tokens
		
		// Build the request and send it
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", yf_loginID);
		jsonObject.put("password", yf_loginPassword);
		jsonObject.put("clientOrgRef", orgID);
		     
	      // 2 - Create the transport with the Body to request the login
		YFTransportToken transport = new YFTransportToken(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/refresh-tokens",
	    			"", // don't use an existing security token...
	    			jsonObject.toString(),
	    			YFTransport.defaultTimeout()
	    			);
	    
	     // 3 - Call the API end point     	    	
	     YFTokenResponse result = transport.executeToken();
	     
	     // Debug code
//	     if (result.token.isBlank()) {
//	    	 result.token = jsonObject.toString();
//	     }		
	    			     
	     return(result); 	

	}	
	
	// Gets an Access token based on the user authorised from the Refresh Token. 
	public static YFTokenResponse GetAccessToken(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName, 	
			/*@NotBlank*/ String yf_RefreshToken) {

		// 1 - No body required. 		
	    // 2 - Create an Access Token based on the user for the Refresh Token.  
		YFTransportToken transport = new YFTransportToken(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName, 		    			
	    			"api/access-tokens",
	    			yf_RefreshToken,
	    			"", // no body required
	    			YFTransport.defaultTimeout()
	    			);
	    
	     // 3 - Call the API end point     	    	
	    YFTokenResponse result = transport.executeToken();
		
		return(result);        

	}

	// Gets a Refresh Token for a specific user. This is used in integration where you just have the Application level refresh token
	public static YFTokenResponse GetUserRefreshTokenNoPassword(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName, 			
			/*@NotBlank*/ String yf_AdminAccessToken,			
			/*@NotBlank*/ String yf_currUserID, /* User ID is case sensitive - email isn't */
			/*@NotBlank*/ String orgID) {	    
		String entryPoint = "api/refresh-tokens?singleSignOn=true&noPassword=true";
		
		// 1 Build the request and send it
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", yf_currUserID);
		     
	    // 2 - Create the transport with the Body to request the login
		YFTransportToken transport = new YFTransportToken(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName,
	    			entryPoint,
	    			yf_AdminAccessToken, 
	    			jsonObject.toString(),
	    			YFTransport.defaultTimeout()
	    			);
	    
	     // 3 - Call the API end point     	    	
	     YFTokenResponse UserRefreshToken = transport.executeToken();
	     return(UserRefreshToken);				 	
	}		
	
	// Using a Access Token (created from a specific User) creates a login Token
	public static YFTokenResponse GetLoginToken(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName,			
			/*@NotBlank*/ String yf_AccessToken,
			/*@NotBlank*/ String yf_UserID, 
			              String yf_UserPassword
			) {

	    // 1.a - Make the "SignOnUser" JSON part of the data packet	    	
		JSONObject signOnUser = new JSONObject();
	    signOnUser.put("userName", yf_UserID);
	    if (yf_UserPassword.isBlank() == false) {
	      signOnUser.put("password", yf_UserPassword);
	    }

	     // 1.b - Bring the JSON parts together into the Body Object
	    JSONObject jsonObject = new JSONObject();        
	    jsonObject.put("signOnUser", signOnUser);
	    jsonObject.put("noPassword", (yf_UserPassword.isBlank()));                   
			
	      // 2 - Create the transport with the Body to request the login
		YFTransportToken transport = new YFTransportToken(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/login-tokens",
	    			yf_AccessToken,
	    			jsonObject.toString(), 
	    			YFTransport.defaultTimeout()
	    			);
	    
	     // 3 - Call the API end point     	    	
	    YFTokenResponse result = transport.executeToken();
	    
		return(result);
	}		

	public static YFTokenResponse GetLoginTokenSSO(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName,			
			/*@NotBlank*/ String yf_loginID, 
			/*@NotBlank*/ String yf_loginPassword,
			/*@NotBlank*/ String orgID,
			/*@NotBlank*/ String yf_UserID, 
			              String yf_UserPassword) {

		 // 1) Build Request Body 
	     // 2) Adds to the transport
	     // 3) Gets the token    	
	    	
	     // 1.a - Make the "SignOnUser" JSON part of the data packet	    	
		 JSONObject signOnUser = new JSONObject();
	     signOnUser.put("userName", yf_UserID); 
	     if (yf_UserPassword.isBlank() == false) {
	        signOnUser.put("password", yf_UserPassword); 
	     }
	     
	     signOnUser.put("clientOrgRef", orgID);
	     
	     // 1.b - Make the "adminUser" JSON part of the data packet
	     JSONObject adminUser = new JSONObject();
	     adminUser.put("userName", yf_loginID);
	     adminUser.put("password", yf_loginPassword);

	     // 1.c - Bring the JSON parts together into the Body Object
	     JSONObject jsonObject = new JSONObject();        
	     jsonObject.put("signOnUser", signOnUser);
	     jsonObject.put("noPassword", (yf_UserPassword.isBlank()));                   
	     jsonObject.put("adminUser", adminUser);
	     
	      // 2 - Create the transport with the Body to request the login
	     YFTransportToken transport = new YFTransportToken(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/rpc/login-tokens/create-sso-token",
	    			"", // don't use an existing security token...
	    			jsonObject.toString(),
	    			YFTransport.defaultTimeout()
	    			);
	    
	     // 3 - Call the API end point     	    	
	     YFTokenResponse result = transport.executeToken();
	         			     
	     return(result); 
	}  
	
}
