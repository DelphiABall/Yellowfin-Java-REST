package com.yellowfinbi.api.sections.dashboards;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFDashboardReportModel extends IYFLoadFromJSON {
    long getTabId(); String getEntityUUID(); String getDefaultDisplay();
    long getReportId(); String getName(); String getDescription();
    boolean isFavouritedByUser(); long getCommentCount();
}
