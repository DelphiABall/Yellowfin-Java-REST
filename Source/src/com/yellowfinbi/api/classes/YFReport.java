package com.yellowfinbi.api.classes;

public class YFReport {
	
	private int reportId;	
	private String name;	
	private String description;	
	private String categoryCode;	
	private String categoryName;	
	private String subCategoryCode;
	private String subCategoryName;	
	private String statusCode;	
	private int viewId;	
	private String viewName;	
	private int sourceId;	
	private String sourceName;	
	private String accessTypeCode;	
	private String publishDate;		
	private String updatedDate;	
	private String creatorName;	
	private String lastUpdaterName;	
	private boolean isFavouritedByUser;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryCode() {
		return subCategoryCode;
	}

	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getAccessTypeCode() {
		return accessTypeCode;
	}

	public void setAccessTypeCode(String accessTypeCode) {
		this.accessTypeCode = accessTypeCode;
	}

	public String gepublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getLastUpdaterName() {
		return lastUpdaterName;
	}

	public void setLastUpdaterName(String lastUpdaterName) {
		this.lastUpdaterName = lastUpdaterName;
	}

	public boolean isFavouritedByUser() {
		return isFavouritedByUser;
	}

	public void setFavouritedByUser(boolean favouritedByUser) {
		isFavouritedByUser = favouritedByUser;
	}
}
