package com.yellowfinbi.api.sections.content;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;

class YFContentItemImpl implements IYFContentItem {
    private long contentId, viewId, sourceId, creatorId, lastUpdaterId, commentCount;
    private String contentUuid, name, description, contentType, categoryCode, categoryName;
    private String subCategoryCode, subCategoryName, viewName, sourceName, accessTypeCode;
    private String formattedUpdatedDate, formattedPublishedDate, creatorName, lastUpdaterName;
    private YFStatusCode statusCode; private boolean isFavouritedByUser;

    static { YFFactoryRegistry.registerFactory(IYFContentItem.class, YFContentItemImpl::new); }

    @Override public void loadFromJSON(JSONObject j) {
        contentId = YFJsonHelper.getLong(j, "contentId", 0);
        contentUuid = YFJsonHelper.getString(j, "contentUUID", "");
        name = YFJsonHelper.getString(j, "name", "");
        description = YFJsonHelper.getString(j, "description", "");
        contentType = YFJsonHelper.getString(j, "contentType", "");
        categoryCode = YFJsonHelper.getString(j, "categoryCode", "");
        categoryName = YFJsonHelper.getString(j, "categoryName", "");
        subCategoryCode = YFJsonHelper.getString(j, "subCategoryCode", "");
        subCategoryName = YFJsonHelper.getString(j, "subCategoryName", "");
        statusCode = YFJsonHelper.getEnum(j, "statusCode", YFStatusCode.class, YFStatusCode.OPEN);
        viewId = YFJsonHelper.getLong(j, "viewId", 0);
        viewName = YFJsonHelper.getString(j, "viewName", "");
        sourceId = YFJsonHelper.getLong(j, "sourceId", 0);
        sourceName = YFJsonHelper.getString(j, "sourceName", "");
        accessTypeCode = YFJsonHelper.getString(j, "accessTypeCode", "");
        formattedUpdatedDate = YFJsonHelper.getString(j, "formattedUpdatedDate", "");
        formattedPublishedDate = YFJsonHelper.getString(j, "formattedPublishedDate", "");
        creatorId = YFJsonHelper.getLong(j, "creatorId", 0);
        creatorName = YFJsonHelper.getString(j, "creatorName", "");
        lastUpdaterId = YFJsonHelper.getLong(j, "lastUpdaterId", 0);
        lastUpdaterName = YFJsonHelper.getString(j, "lastUpdaterName", "");
        isFavouritedByUser = YFJsonHelper.getBool(j, "isFavouritedByUser", false);
        commentCount = YFJsonHelper.getLong(j, "commentCount", 0);
    }
    @Override public long getContentId() { return contentId; }
    @Override public String getContentUuid() { return contentUuid; }
    @Override public String getName() { return name; }
    @Override public String getDescription() { return description; }
    @Override public String getContentType() { return contentType; }
    @Override public String getCategoryCode() { return categoryCode; }
    @Override public String getCategoryName() { return categoryName; }
    @Override public String getSubCategoryCode() { return subCategoryCode; }
    @Override public String getSubCategoryName() { return subCategoryName; }
    @Override public YFStatusCode getStatusCode() { return statusCode; }
    @Override public long getViewId() { return viewId; }
    @Override public String getViewName() { return viewName; }
    @Override public long getSourceId() { return sourceId; }
    @Override public String getSourceName() { return sourceName; }
    @Override public String getAccessTypeCode() { return accessTypeCode; }
    @Override public String getFormattedUpdatedDate() { return formattedUpdatedDate; }
    @Override public String getFormattedPublishedDate() { return formattedPublishedDate; }
    @Override public long getCreatorId() { return creatorId; }
    @Override public String getCreatorName() { return creatorName; }
    @Override public long getLastUpdaterId() { return lastUpdaterId; }
    @Override public String getLastUpdaterName() { return lastUpdaterName; }
    @Override public boolean isFavouritedByUser() { return isFavouritedByUser; }
    @Override public long getCommentCount() { return commentCount; }
}
