package com.yellowfinbi.api.sections.dashboards;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.sections.filters.*;
import com.yellowfinbi.api.transport.*;
public final class YFDashboardsApi {
    private YFDashboardsApi() {}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFDashboardListItemModel>> getDashboardsList(
            IYFServerDetails s, String t, String filterJson, int maxItems) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFDashboardListItemModel.class),
                s,t,YFTransport.HttpVerb.GET,YFFilterHelper.buildURL("api/dashboards",filterJson,maxItems),null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFSearchOptions> getAvailableMetadataForDashboardList(
            IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(IYFSearchOptions.class,s,t,YFTransport.HttpVerb.GET,"api/dashboards/metadata",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFDashboardListItemModel> getDashboard(
            IYFServerDetails s, String t, long dashboardId) {
        return YFGenericExecutor.executeTyped(IYFDashboardListItemModel.class,s,t,YFTransport.HttpVerb.GET,"api/dashboards/"+dashboardId,null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFDashboardReportModel>> getDashboardReports(
            IYFServerDetails s, String t, long dashboardId) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFDashboardReportModel.class,"reports"),
                s,t,YFTransport.HttpVerb.GET,"api/dashboards/"+dashboardId+"/reports",null);
    }
}
