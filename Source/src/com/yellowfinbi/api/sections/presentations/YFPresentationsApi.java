package com.yellowfinbi.api.sections.presentations;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.sections.filters.*;
import com.yellowfinbi.api.transport.*;
public final class YFPresentationsApi {
    private YFPresentationsApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFPresentationListItemModel>> getPresentationsList(
            IYFServerDetails s, String t, String filterJson, int maxItems) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFPresentationListItemModel.class),
                s,t,YFTransport.HttpVerb.GET,YFFilterHelper.buildURL("api/stories",filterJson,maxItems),null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFPresentationListItemModel> getPresentation(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeTyped(IYFPresentationListItemModel.class,s,t,YFTransport.HttpVerb.GET,"api/stories/"+id,null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFPresentationReportModel>> getPresentationReports(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFPresentationReportModel.class,"reports"),
                s,t,YFTransport.HttpVerb.GET,"api/stories/"+id+"/reports",null);
    }
}
