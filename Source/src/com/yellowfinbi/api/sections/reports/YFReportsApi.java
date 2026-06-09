package com.yellowfinbi.api.sections.reports;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.sections.filters.*;
import com.yellowfinbi.api.transport.*;
public final class YFReportsApi {
    private YFReportsApi() { }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFReportListItemModel>> getReports(
            IYFServerDetails server, String token, String filterJson, int maxItems) {
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFReportListItemModel.class),
                server, token, YFTransport.HttpVerb.GET, YFFilterHelper.buildURL("api/reports", filterJson, maxItems), null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFSearchOptions> getAvailableMetadataForReportsList(
            IYFServerDetails server, String token) {
        return YFGenericExecutor.executeTyped(IYFSearchOptions.class, server, token, YFTransport.HttpVerb.GET, "api/reports/metadata", null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFReportListItemModel> getSpecificReport(
            IYFServerDetails server, String token, long reportId) {
        return YFGenericExecutor.executeTyped(IYFReportListItemModel.class, server, token, YFTransport.HttpVerb.GET, "api/reports/" + reportId, null);
    }
}
