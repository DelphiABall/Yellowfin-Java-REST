package com.yellowfinbi.api.sections.presentations;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFPresentationListItemModelImpl implements IYFPresentationListItemModel {
    private long presId, creatorId, lastModId; private String presUUID, name, desc, catCode, catName, subCatCode, subCatName;
    private String fmtLastMod, fmtPub, creatorName, lastModName; private YFStatusCode statusCode; private boolean isFav;
    static { YFFactoryRegistry.registerFactory(IYFPresentationListItemModel.class, YFPresentationListItemModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        presId=YFJsonHelper.getLong(j,"presentationId",0); presUUID=YFJsonHelper.getString(j,"presentationPublishUUID","");
        name=YFJsonHelper.getString(j,"name",""); desc=YFJsonHelper.getString(j,"description","");
        catCode=YFJsonHelper.getString(j,"categoryCode",""); catName=YFJsonHelper.getString(j,"categoryName","");
        subCatCode=YFJsonHelper.getString(j,"subCategoryCode",""); subCatName=YFJsonHelper.getString(j,"subCategoryName","");
        statusCode=YFJsonHelper.getEnum(j,"statusCode",YFStatusCode.class,YFStatusCode.OPEN);
        fmtLastMod=YFJsonHelper.getString(j,"formattedLastModifiedDateTime",""); fmtPub=YFJsonHelper.getString(j,"formattedPublishedDateTime","");
        creatorId=YFJsonHelper.getLong(j,"creatorId",0); creatorName=YFJsonHelper.getString(j,"creatorName","");
        lastModId=YFJsonHelper.getLong(j,"lastModifierId",0); lastModName=YFJsonHelper.getString(j,"lastModifierName","");
        isFav=YFJsonHelper.getBool(j,"isFavouritedByUser",false);
    }
    @Override public long getPresentationId(){return presId;} @Override public String getPresentationPublishUUID(){return presUUID;}
    @Override public String getName(){return name;} @Override public String getDescription(){return desc;}
    @Override public String getCategoryCode(){return catCode;} @Override public String getCategoryName(){return catName;}
    @Override public String getSubCategoryCode(){return subCatCode;} @Override public String getSubCategoryName(){return subCatName;}
    @Override public YFStatusCode getStatusCode(){return statusCode;}
    @Override public String getFormattedLastModifiedDateTime(){return fmtLastMod;} @Override public String getFormattedPublishedDateTime(){return fmtPub;}
    @Override public long getCreatorId(){return creatorId;} @Override public String getCreatorName(){return creatorName;}
    @Override public long getLastModifierId(){return lastModId;} @Override public String getLastModifierName(){return lastModName;}
    @Override public boolean isFavouritedByUser(){return isFav;}
}
class YFPresentationReportModelImpl implements IYFPresentationReportModel {
    private long slideId, reportId, commentCount; private String defaultDisplay, entityUUID, name, desc; private boolean isFav;
    static { YFFactoryRegistry.registerFactory(IYFPresentationReportModel.class, YFPresentationReportModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        slideId=YFJsonHelper.getLong(j,"slideId",0); defaultDisplay=YFJsonHelper.getString(j,"defaultDisplay","");
        entityUUID=YFJsonHelper.getString(j,"entityUUID",""); reportId=YFJsonHelper.getLong(j,"reportId",0);
        name=YFJsonHelper.getString(j,"name",""); desc=YFJsonHelper.getString(j,"description","");
        isFav=YFJsonHelper.getBool(j,"isFavouritedByUser",false); commentCount=YFJsonHelper.getLong(j,"commentCount",0);
    }
    @Override public long getSlideId(){return slideId;} @Override public String getDefaultDisplay(){return defaultDisplay;}
    @Override public String getEntityUUID(){return entityUUID;} @Override public long getReportId(){return reportId;}
    @Override public String getName(){return name;} @Override public String getDescription(){return desc;}
    @Override public boolean isFavouritedByUser(){return isFav;} @Override public long getCommentCount(){return commentCount;}
}
