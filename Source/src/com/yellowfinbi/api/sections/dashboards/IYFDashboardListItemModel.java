package com.yellowfinbi.api.sections.dashboards;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFDashboardListItemModel extends IYFLoadFromJSON {
    long getDashboardId(); String getDashboardPublishUUID(); String getName(); String getDescription();
    String getCategoryCode(); String getCategoryName(); String getSubCategoryCode(); String getSubCategoryName();
    String getStatusCode(); String getFormattedLastModifiedDateTime(); String getFormattedPublishedDateTime();
    long getCreatorId(); String getCreatorName(); long getLastModifierId(); String getLastModifierName();
    boolean isFavouritedByUser(); long getFavouriteSequenceNumber();
}
