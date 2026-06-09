package com.yellowfinbi.api.sections.datasources;
import com.yellowfinbi.api.common.*;
import com.yellowfinbi.api.transport.*;
public final class YFDataSourcesApi {
    private YFDataSourcesApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFDataSource>> getDataSources(IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFDataSource.class), s,t,YFTransport.HttpVerb.GET,"api/data-sources",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFDataSource> getDataSource(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeTyped(IYFDataSource.class,s,t,YFTransport.HttpVerb.GET,"api/data-sources/"+id,null);
    }
    public static YFTransportResponse closeConnectionPool(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.DELETE,"api/data-sources/"+id+"/connection-pool");
    }
    public static YFTransportResponse refreshAutomaticSourceFilters(IYFServerDetails s, String t, long id) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.POST,"api/rpc/data-sources/"+id+"/refresh-source-filters",null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFClientDataSource>> getClientDataSources(IYFServerDetails s, String t, long orgId) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFClientDataSource.class), s,t,YFTransport.HttpVerb.GET,"api/data-sources/"+orgId+"/client-data-sources",null);
    }
}
