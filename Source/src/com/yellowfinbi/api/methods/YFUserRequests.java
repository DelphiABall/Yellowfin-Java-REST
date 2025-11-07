package com.yellowfinbi.api.methods;

import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yellowfinbi.api.classes.YFUser;
import com.yellowfinbi.api.transport.YFTransport;
import com.yellowfinbi.api.transport.YFTransportResponse;
import java.util.List;


public class YFUserRequests {
	
	// Add User into Yellowfin
	static public YFTransportResponse CreateUsers(
			/*@NotBlank*/ String yf_baseURL, 			
			/*@NotBlank*/ String yf_appName, 						
			/*@NotBlank*/ String yf_accessToken,
			/*@NotBlank*/ String yf_userRole,						  
		                  List<YFUser> yfUsers
			) {
	
	    JSONArray userArray = new JSONArray();
	    for (YFUser user : yfUsers) {
	      userArray.put(user.toJSON(false));
	    }
	     	    	
		YFTransport transport = new YFTransport(
	    			YFTransport.httpVerbType.Post,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/admin/users/",
	    			yf_accessToken, 
	    			userArray.toString(),
	    			YFTransport.defaultTimeout()
	    			);
		
		YFTransportResponse result = transport.execute();
		
		return (result);				
	 }
	
	static public YFUser GetUserDetailsByUsername(
			/*@NotBlank*/ String yf_baseURL, 			
			/*@NotBlank*/ String yf_appName, 						
			/*@NotBlank*/ String yf_accessToken,									
		                  String UserID
			) {
		       
		YFTransport transport = new YFTransport(
	    			YFTransport.httpVerbType.Get,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/rpc/users/user-details-by-username/"+UserID, // this gets the Yellowfin internal ID
	    			yf_accessToken, 
	    			"",
	    			YFTransport.defaultTimeout()
	    			);
		
		YFTransportResponse data = transport.execute();
		
		if (data.getStatus() == HttpStatus.SC_OK) {
			
			JSONObject jsonObject = new JSONObject(data.getData());	        
	        String yfID = jsonObject.optString("userId", "");
			
			YFTransport userTransport = new YFTransport(
	    			YFTransport.httpVerbType.Get,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/admin/users/"+yfID,  // This provides a more detailed response. 
	    			yf_accessToken, 
	    			"",
	    			YFTransport.defaultTimeout()
	    			);
			
			YFTransportResponse userData = userTransport.execute();			
			YFUser user = YFUser.createFromJSON(userData.getData());
			
			return(user);
			
		} else {
			return(null);
		}
							
	 }

	static public YFTransportResponse UpdateUser(
			/*@NotBlank*/ String yf_baseURL, 			
			/*@NotBlank*/ String yf_appName, 						
			/*@NotBlank*/ String yf_accessToken,
			              YFUser yfUser
			) {
		
		
		     	    
		YFTransport transport = new YFTransport(
	    			YFTransport.httpVerbType.Patch,
	    			yf_baseURL,
	    			yf_appName,
	    			"api/admin/users/"+Integer.toString(yfUser.getIpId()),
	    			yf_accessToken, 
	    			yfUser.toJSON(true).toString(),
	    			YFTransport.defaultTimeout()
	    			);
		
		YFTransportResponse result = transport.execute();
		
		return (result);				
	 }


}
