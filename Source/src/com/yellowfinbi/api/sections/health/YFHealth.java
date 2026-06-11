package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;

class YFNodeCacheInfoImpl implements IYFNodeCacheInfo {
    private NodeCacheType cacheType; private long currentSize, maxSize;
    static { YFFactoryRegistry.registerFactory(IYFNodeCacheInfo.class, YFNodeCacheInfoImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        cacheType = YFJsonHelper.getEnum(json, "cacheType", NodeCacheType.class, NodeCacheType.viewCache);
        currentSize = YFJsonHelper.getLong(json, "cacheCurrentSize", 0);
        maxSize = YFJsonHelper.getLong(json, "cacheMaxSize", 0);
    }
    @Override public NodeCacheType getCacheType() { return cacheType; }
    @Override public long getCacheCurrentSize() { return currentSize; }
    @Override public long getCacheMaxSize() { return maxSize; }
}

class YFConfigDBInfoImpl implements IYFConfigDBInfo {
    private String status; private int maxConn, minConn, openConn, activeConn;
    static { YFFactoryRegistry.registerFactory(IYFConfigDBInfo.class, YFConfigDBInfoImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        status = YFJsonHelper.getString(json, "status", "");
        maxConn = YFJsonHelper.getInt(json, "maxConnections", 0);
        minConn = YFJsonHelper.getInt(json, "minConnections", 0);
        openConn = YFJsonHelper.getInt(json, "openConnections", 0);
        activeConn = YFJsonHelper.getInt(json, "activeConnections", 0);
    }
    @Override public String getStatus() { return status; }
    @Override public int getMaxConnections() { return maxConn; }
    @Override public int getMinConnections() { return minConn; }
    @Override public int getOpenConnections() { return openConn; }
    @Override public int getActiveConnections() { return activeConn; }
}

class YFNodeInfoImpl implements IYFNodeInfo {
    private String hostName, appVersion, statusCode;
    private int availProc, webSessions;
    private long jvmInUse, jvmTotal, jvmFree, jvmMax, sysMem;
    private final YFModelList<IYFNodeCacheInfo> caches = new YFModelList<>(IYFNodeCacheInfo.class, "caches");
    private IYFConfigDBInfo configDB;
    static { YFFactoryRegistry.registerFactory(IYFNodeInfo.class, YFNodeInfoImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        hostName = YFJsonHelper.getString(json, "hostName", "");
        appVersion = YFJsonHelper.getString(json, "applicationVersion", "");
        statusCode = YFJsonHelper.getString(json, "statusCode", "");
        availProc = YFJsonHelper.getInt(json, "availableProcessors", 0);
        jvmInUse = YFJsonHelper.getLong(json, "jvmMemoryInUse", 0);
        jvmTotal = YFJsonHelper.getLong(json, "jvmTotalMemory", 0);
        jvmFree = YFJsonHelper.getLong(json, "jvmFreeMemory", 0);
        jvmMax = YFJsonHelper.getLong(json, "jvmMaxMemory", 0);
        sysMem = YFJsonHelper.getLong(json, "systemMemory", 0);
        webSessions = YFJsonHelper.getInt(json, "webSessions", 0);
        caches.loadFromJSON(json);
        JSONObject dbJson = YFJsonHelper.getObject(json, "configDBInfo");
        if (dbJson != null) { configDB = new YFConfigDBInfoImpl(); configDB.loadFromJSON(dbJson); }
    }
    @Override public String getHostName() { return hostName; }
    @Override public String getApplicationVersion() { return appVersion; }
    @Override public String getStatusCode() { return statusCode; }
    @Override public int getAvailableProcessors() { return availProc; }
    @Override public long getJvmMemoryInUse() { return jvmInUse; }
    @Override public long getJvmTotalMemory() { return jvmTotal; }
    @Override public long getJvmFreeMemory() { return jvmFree; }
    @Override public long getJvmMaxMemory() { return jvmMax; }
    @Override public long getSystemMemory() { return sysMem; }
    @Override public int getWebSessions() { return webSessions; }
    @Override public YFModelList<IYFNodeCacheInfo> getCaches() { return caches; }
    @Override public IYFConfigDBInfo getConfigDBInfo() { return configDB; }
}

class YFHealthImpl implements IYFHealth {
    private String status;
    private final YFModelList<IYFNodeInfo> nodes = new YFModelList<>(IYFNodeInfo.class, "nodes");
    static { YFFactoryRegistry.registerFactory(IYFHealth.class, YFHealthImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        status = YFJsonHelper.getString(json, "statusCode", "");
        nodes.loadFromJSON(json);
    }
    @Override public String getStatus() { return status; }
    @Override public YFModelList<IYFNodeInfo> getNodes() { return nodes; }
}
