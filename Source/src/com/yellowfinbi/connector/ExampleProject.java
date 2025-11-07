package com.yellowfinbi.connector;

import java.util.List;

import com.yellowfinbi.api.classes.*;
import com.yellowfinbi.api.methods.*;
import com.yellowfinbi.api.transport.*;

public class ExampleProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Welocme to the sample for showing how to connect to Yellowfin REST API from Java!\n");
		System.out.print(" \n");
		System.out.print("For more details on the API, visit https://developers.yellowfinbi.com/dev/api-docs/current \n");
		System.out.print("This is designed to provide helper methods to get you started quickly, passing the end point to the YFTransport method and also shows how to use the different tokens");		
				
		// This sample shows different steps to make your first API Call!
		// Feel free to copy these classes and use them in your own application
		
		// These are the test settings. Please update with your instance details. 
		String hostURL = "http://localhost:8080";
		String appName = "YELLOWFIN"; /* this is the default. changing this is done with whitelabeling only */
		String adminUserName = "admin@yellowfin.com.au";
		String adminPassword = "test";
		String userName = "admin@yellowfin.com.au";
		String userPassword = "test";
		String orgID = "0";
		
		// This example shows how to get all tokens. If you only need a single sign on (login token) and you are happy to store the Admin level username and password
		// then you can use the YFTokenRequest.GetLoginTokenSSO method. 
		// NOTE: Using single sign on without the users password requires configuration settings to enable this style of login by proxy 
		
		System.out.print("Refresh Token: \n");
		YFTokenResponse AdminRefreshToken = YFTokenRequests.GetRefreshToken(hostURL, appName, adminUserName, adminPassword, orgID);
		System.out.print("Status: "+AdminRefreshToken.getStatus().toString()+"\n");
		System.out.print("Refresh Token: "+AdminRefreshToken.getToken()+"\n");
		
		System.out.print(" \n");
		
		System.out.print("Access Token: (lasts 20 minutes - 1200 seconds) \n");
		YFTokenResponse AdminAccessToken = YFTokenRequests.GetAccessToken(hostURL, appName, AdminRefreshToken.getToken());
		System.out.print("Status: "+AdminAccessToken.getStatus().toString()+"\n");
		System.out.print("Access Token: "+AdminAccessToken.getToken()+"\n");
		
		System.out.print(" \n");
		
		System.out.print("Login Token: (Single Use) \n");
		YFTokenResponse LoginToken = YFTokenRequests.GetLoginToken(hostURL, appName, AdminAccessToken.getToken(), userName, userPassword);
		System.out.print("Status: "+LoginToken.getStatus().toString()+"\n");
		System.out.print("Login Token: "+LoginToken.getToken()+"\n");
		
		System.out.print(" \n");
		
		System.out.print("SSO URL Example \n");
		System.out.print("(Learn more @ https://wiki.yellowfinbi.com/display/yfcurrent/Defining+Login+Session+Options) \n");
		
		System.out.print(hostURL+"/logon.i4?LoginWebserviceId="+LoginToken.getToken()+"&disableheader=true&entry=TIMELINE \n");
		
		// Todo... expand the example to have other methods e.g. getting a list of user reports. 
		// Show how the RPC call works instead of getting all the tokens
		
		System.out.print(" \n");
		System.out.print("Now lets get a list of reports for a specific user. \n");
		System.out.print("For this example we will assume that we are using Single Sign on and that we DON'T have the users password \n");
		
		System.out.print("Get a Refresh Token (for specific user) using the authorisation of the Admin level Access Token \n");	
		YFTokenResponse UserRefreshToken = YFTokenRequests.GetUserRefreshTokenNoPassword(hostURL, appName, AdminAccessToken.getToken(), userName, orgID);
		System.out.print("Status: "+UserRefreshToken.getStatus().toString()+"\n");
		System.out.print("User Refresh Token: "+UserRefreshToken.getToken()+"\n");		
		System.out.print(" \n");
		
		System.out.print("Access Token: (lasts 20 minutes - 1200 seconds) \n");
		YFTokenResponse UserAccessToken = YFTokenRequests.GetAccessToken(hostURL, appName, UserRefreshToken.getToken());
		System.out.print("Status: "+UserAccessToken.getStatus().toString()+"\n");
		System.out.print("User Access Token: "+UserAccessToken.getToken()+"\n");
		System.out.print(" \n");

		System.out.print("Get a list of reports accessible to the user \n");
		List<YFReport> Reports = YFReportRequests.GetUserReports(hostURL, appName, UserAccessToken.getToken());
		
		// Loop through the list of YFReport objects
		for (YFReport report : Reports) {
		    // Perform operations on the 'report' object here
		    // You can access the properties and methods of each YFReport object within this loop
		    if (report.isFavouritedByUser()) {
		    	System.out.println(" - Report Name: [*] " + report.getName());  // These are favorited by the user.
		    } else {
		    	System.out.println(" - Report Name: " + report.getName());
		    }
		    System.out.println(" - Report Description: " + report.getDescription()+" \n");		    
		}		
		
		 System.out.println(" \n");
		 System.out.println(" \n");
		 
		 System.out.println("What are the details of the current user?");
		 //YFUser user =  YFUserRequests.GetUserDetailsByUsername(hostURL, appName, AdminAccessToken.getToken(), userName);
		 YFUser user =  YFUserRequests.GetUserDetailsByUsername(hostURL, appName, AdminAccessToken.getToken(), userName);
		 System.out.println(user.toJSON(false).toString());
		
		 System.out.print("Updating User: "+user.getUserId()+" ["+Integer.toString(user.getIpId())+"]");
		 
		 String tempLastName = user.getLastName();
		 user.setLastName("Updated");
		 YFTransportResponse UpdateduserResult =  YFUserRequests.UpdateUser(hostURL, appName, UserAccessToken.getToken(), user);
		 System.out.print("Status: "+UpdateduserResult.getStatus().toString()+" \n");
		 System.out.print("Result: "+UpdateduserResult.getData()+" \n");
			 
		 user.setLastName(tempLastName);
		 UpdateduserResult =  YFUserRequests.UpdateUser(hostURL, appName, UserAccessToken.getToken(), user);
		 System.out.print("Status: "+UpdateduserResult.getStatus().toString()+" \n");
		 System.out.print("Result: "+UpdateduserResult.getData()+" \n");
		 
		 YFUser updatedUser = YFUser.createFromJSON(UpdateduserResult.getData());
		 System.out.print("Example of using the User Object, created from JSON : email = "+ updatedUser.getEmailAddress());
		 
	}

}
