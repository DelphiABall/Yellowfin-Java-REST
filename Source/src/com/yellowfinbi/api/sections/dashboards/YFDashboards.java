package com.yellowfinbi.api.sections.dashboards;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFDashboardListItemModelImpl implements IYFDashboardListItemModel {
    private long dashboardId, creatorId, lastModifierId, favSeqNum;
    private String dashPubUUID, name, desc, catCode, catName, subCatCode, subCatName, statusCode;
    private String fmtLastMod, fmtPub, creatorName, lastModName; private boolean isFav;
    static { YFFactoryRegistry.registerFactory(IYFDashboardListItemModel.class, YFDashboardListItemModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        dashboardId = YFJsonHelper.getLong(j,"dashboardId",0); dashPubUUID = YFJsonHelper.getString(j,"dashboardPublishUUID","");
        name = YFJsonHelper.getString(j,"name",""); desc = YFJsonHelper.getString(j,"description","");
        catCode = YFJsonHelper.getString(j,"categoryCode",""); catName = YFJsonHelper.getString(j,"categoryName","");
        subCatCode = YFJsonHelper.getString(j,"subCategoryCode",""); subCatName = YFJsonHelper.getString(j,"subCategoryName","");
        statusCode = YFJsonHelper.getString(j,"statusCode","");
        fmtLastMod = YFJsonHelper.getString(j,"formattedLastModifiedDateTime",""); fmtPub = YFJsonHelper.getString(j,"formattedPublishedDateTime","");
        creatorId = YFJsonHelper.getLong(j,"creatorId",0); creatorName = YFJsonHelper.getString(j,"creatorName","");
        lastModifierId = YFJsonHelper.getLong(j,"lastModifierId",0); lastModName = YFJsonHelper.getString(j,"lastModifierName","");
        isFav = YFJsonHelper.getBool(j,"isFavouritedByUser",false); favSeqNum = YFJsonHelper.getLong(j,"favouriteSequenceNumber",0);
    }
    @Override public long getDashboardId(){return dashboardId;} @Override public String getDashboardPublishUUID(){return dashPubUUID;}
    @Override public String getName(){return name;} @Override public String getDescription(){return desc;}
    @Override public String getCategoryCode(){return catCode;} @Override public String getCategoryName(){return catName;}
    @Override public String getSubCategoryCode(){return subCatCode;} @Override public String getSubCategoryName(){return subCatName;}
    @Override public String getStatusCode(){return statusCode;}
    @Override public String getFormattedLastModifiedDateTime(){return fmtLastMod;} @Override public String getFormattedPublishedDateTime(){return fmtPub;}
    @Override public long getCreatorId(){return creatorId;} @Override public String getCreatorName(){return creatorName;}
    @Override public long getLastModifierId(){return lastModifierId;} @Override public String getLastModifierName(){return lastModName;}
    @Override public boolean isFavouritedByUser(){return isFav;} @Override public long getFavouriteSequenceNumber(){return favSeqNum;}
}
class YFDashboardReportModelImpl implements IYFDashboardReportModel {
    private long tabId, reportId, commentCount; private String entityUUID, defaultDisplay, name, desc; private boolean isFav;
    static { YFFactoryRegistry.registerFactory(IYFDashboardReportModel.class, YFDashboardReportModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        tabId = YFJsonHelper.getLong(j,"tabId",0); entityUUID = YFJsonHelper.getString(j,"entityUUID","");
        defaultDisplay = YFJsonHelper.getString(j,"defaultDisplay",""); reportId = YFJsonHelper.getLong(j,"reportId",0);
        name = YFJsonHelper.getString(j,"name",""); desc = YFJsonHelper.getString(j,"description","");
        isFav = YFJsonHelper.getBool(j,"isFavouritedByUser",false); commentCount = YFJsonHelper.getLong(j,"commentCount",0);
    }
    @Override public long getTabId(){return tabId;} @Override public String getEntityUUID(){return entityUUID;}
    @Override public String getDefaultDisplay(){return defaultDisplay;} @Override public long getReportId(){return reportId;}
    @Override public String getName(){return name;} @Override public String getDescription(){return desc;}
    @Override public boolean isFavouritedByUser(){return isFav;} @Override public long getCommentCount(){return commentCount;}
}
