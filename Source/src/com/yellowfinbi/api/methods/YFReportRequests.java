package com.yellowfinbi.api.methods;

import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import com.yellowfinbi.api.classes.*;
import com.yellowfinbi.api.transport.*;

public class YFReportRequests {
	
	static public List<YFReport> GetUserReports(
			/*@NotBlank*/ String yf_baseURL,
			/*@NotBlank*/ String yf_appName,			
			/*@NotBlank*/ String yf_accessToken) {

		
		YFTransport transport = new YFTransport(YFTransport.httpVerbType.Get, 
				                                yf_baseURL, 
				                                yf_appName,
				                                "api/reports/", 
				                                yf_accessToken,
				                                "", // No Body
				                                YFTransport.defaultTimeout());

		YFTransportResponse data = transport.execute();

		if (data.getStatus() == HttpStatus.SC_OK) {
			// Parse the JSON string
			JSONObject json = new JSONObject(data.getData());
			JSONArray items = json.getJSONArray("items");

			// Convert JSON array to array of YFTransportResponseUserRoles
			List<YFReport> reports = new ArrayList<YFReport>();
			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);

				YFReport report = new YFReport();
				
	            report.setReportId(item.getInt("reportId"));
	            report.setName(item.getString("name"));
	            report.setDescription(item.getString("description"));
	            report.setCategoryCode(item.getString("categoryCode"));
	            report.setCategoryName(item.getString("categoryName"));
	            report.setSubCategoryCode(item.getString("subCategoryCode"));
	            report.setSubCategoryName(item.getString("subCategoryName"));
	            report.setStatusCode(item.getString("statusCode"));
	            report.setViewId(item.getInt("viewId"));
	            report.setViewName(item.getString("viewName"));
	            report.setSourceId(item.getInt("sourceId"));
	            report.setSourceName(item.getString("sourceName"));
	            report.setAccessTypeCode(item.getString("accessTypeCode"));
	            report.setPublishDate(item.getString("formattedPublishDate"));
	            report.setUpdatedDate(item.getString("formattedUpdatedDate"));
	            report.setCreatorName(item.getString("creatorName"));
	            report.setLastUpdaterName(item.getString("lastUpdaterName"));
	            report.setFavouritedByUser(item.getBoolean("isFavouritedByUser"));

				reports.add(report);
			}
			return reports;					
		} else {
			return null;
		}
		
	}	

}
