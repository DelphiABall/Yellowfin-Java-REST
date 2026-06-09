package com.yellowfinbi.api.sections.orgs;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.transport.*;
public final class YFOrgsApi {
    private YFOrgsApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFOrg>> getOrgs(IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFOrg.class), s,t,YFTransport.HttpVerb.GET,"api/orgs",null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFOrg>> getUserOrgs(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFOrg.class), s,t,YFTransport.HttpVerb.GET,"api/admin/users/"+userId+"/org-access",null);
    }
}
