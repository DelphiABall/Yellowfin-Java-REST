package com.yellowfinbi.api.sections.roles;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.transport.*;
public final class YFRolesApi {
    private YFRolesApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFRole>> getRoles(IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFRole.class), s,t,YFTransport.HttpVerb.GET,"api/roles",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFRole> createRole(IYFServerDetails s, String t, IYFRole role) {
        return YFGenericExecutor.executeTyped(IYFRole.class,s,t,YFTransport.HttpVerb.POST,"api/roles",role.toJSON());
    }
    public static YFGenericExecutor.ExecuteResult<IYFRole> updateRole(IYFServerDetails s, String t, String roleCode, IYFRole role) {
        return YFGenericExecutor.executeTyped(IYFRole.class,s,t,YFTransport.HttpVerb.PUT,"api/roles/"+roleCode,role.toJSON());
    }
    public static YFTransportResponse deleteRole(IYFServerDetails s, String t, String roleCode) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.DELETE,"api/roles/"+roleCode);
    }
}
