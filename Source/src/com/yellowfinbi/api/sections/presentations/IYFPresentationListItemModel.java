package com.yellowfinbi.api.sections.presentations;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import com.yellowfinbi.api.common.YFStatusCode;
public interface IYFPresentationListItemModel extends IYFLoadFromJSON {
    long getPresentationId(); String getPresentationPublishUUID(); String getName(); String getDescription();
    String getCategoryCode(); String getCategoryName(); String getSubCategoryCode(); String getSubCategoryName();
    YFStatusCode getStatusCode(); String getFormattedLastModifiedDateTime(); String getFormattedPublishedDateTime();
    long getCreatorId(); String getCreatorName(); long getLastModifierId(); String getLastModifierName();
    boolean isFavouritedByUser();
}
