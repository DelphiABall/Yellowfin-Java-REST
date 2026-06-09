package com.yellowfinbi.api.sections.cache;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.*;
import java.util.stream.Collectors;
import java.util.Arrays;
public final class YFCacheApi {
    private YFCacheApi(){}
    public static YFTransportResponse clearAllContentsOfACache(IYFServerDetails s, String t, CacheType cache) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.DELETE,"api/admin/caches/"+cache.name());
    }
    public static YFTransportResponse reloadCodesInOrgCaches(IYFServerDetails s, String t, OrgCacheType... types) {
        String params = Arrays.stream(types).map(c->"code="+c.name()).collect(Collectors.joining("&"));
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.POST,"api/rpc/admin/caches/reload-codes?"+params,null);
    }
    public static YFTransportResponse refreshCachedFiltersForView(IYFServerDetails s, String t, String viewId) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.POST,"api/rpc/admin/caches/refresh-view-filters/"+viewId,null);
    }
    public static YFTransportResponse refreshCachedFiltersForDashboard(IYFServerDetails s, String t, String dashboardId) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.POST,"api/rpc/admin/caches/refresh-dashboard-filters/"+dashboardId,null);
    }
}
