package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import com.yellowfinbi.api.common.YFModelList;
public interface IYFNodeInfo extends IYFLoadFromJSON {
    String getHostName();
    String getApplicationVersion();
    String getStatusCode();
    int getAvailableProcessors();
    long getJvmMemoryInUse();
    long getJvmTotalMemory();
    long getJvmFreeMemory();
    long getJvmMaxMemory();
    long getSystemMemory();
    int getWebSessions();
    YFModelList<? extends IYFNodeCacheInfo> getCaches();
    IYFConfigDBInfo getConfigDBInfo();
}
