package com.yellowfinbi.api.sections.usergroups;
import com.yellowfinbi.api.common.*; import com.yellowfinbi.api.transport.*;
public final class YFUserGroupsApi {
    private YFUserGroupsApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFUserGroup>> getUserGroups(IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFUserGroup.class), s,t,YFTransport.HttpVerb.GET,"api/user-groups",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFUserGroup> getUserGroup(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeTyped(IYFUserGroup.class,s,t,YFTransport.HttpVerb.GET,"api/user-groups/"+id,null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFUserGroup> createUserGroup(IYFServerDetails s, String t, IYFUserGroup g) {
        return YFGenericExecutor.executeTyped(IYFUserGroup.class,s,t,YFTransport.HttpVerb.POST,"api/user-groups",g.toJSON());
    }
    public static YFGenericExecutor.ExecuteResult<IYFUserGroup> updateUserGroup(IYFServerDetails s, String t, IYFUserGroup g) {
        return YFGenericExecutor.executeTyped(IYFUserGroup.class,s,t,YFTransport.HttpVerb.PATCH,"api/user-groups/"+g.getUserGroupId(),g.toJSON());
    }
    public static YFTransportResponse deleteUserGroup(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.DELETE,"api/user-groups/"+id);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFUserGroupMemberListItem>> getMembersListForUserGroup(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFUserGroupMemberListItem.class,"members"), s,t,YFTransport.HttpVerb.GET,"api/user-groups/"+id+"/members",null);
    }
}
