package com.yellowfinbi.api.sections.datasources;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFDataSourceImpl implements IYFDataSource {
    private long sourceId, connTimeout, minConn, maxConn, maxRows, maxAnalysisRows;
    private String sourceName, sourceDesc, sourceType, connType, connTypeCode, connStr, userName;
    private boolean inheritChildFilters;
    static { YFFactoryRegistry.registerFactory(IYFDataSource.class, YFDataSourceImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        sourceId=YFJsonHelper.getLong(j,"sourceId",0); sourceName=YFJsonHelper.getString(j,"sourceName","");
        sourceDesc=YFJsonHelper.getString(j,"sourceDescription",""); sourceType=YFJsonHelper.getString(j,"sourceType","");
        connType=YFJsonHelper.getString(j,"connectionType",""); connTypeCode=YFJsonHelper.getString(j,"connectionTypeCode","");
        connStr=YFJsonHelper.getString(j,"connectionString",""); connTimeout=YFJsonHelper.getLong(j,"connectionTimeout",0);
        userName=YFJsonHelper.getString(j,"userName","");
        minConn=YFJsonHelper.getLong(j,"minimumConnections",0); maxConn=YFJsonHelper.getLong(j,"maximumConnections",0);
        maxRows=YFJsonHelper.getLong(j,"maxRows",0); maxAnalysisRows=YFJsonHelper.getLong(j,"maxAnalysisRows",0);
        inheritChildFilters=YFJsonHelper.getBool(j,"inheritChildSourceFilters",false);
    }
    @Override public long getSourceId(){return sourceId;} @Override public String getSourceName(){return sourceName;}
    @Override public String getSourceDescription(){return sourceDesc;} @Override public String getSourceType(){return sourceType;}
    @Override public String getConnectionType(){return connType;} @Override public String getConnectionTypeCode(){return connTypeCode;}
    @Override public String getConnectionString(){return connStr;} @Override public long getConnectionTimeout(){return connTimeout;}
    @Override public String getUserName(){return userName;}
    @Override public long getMinimumConnections(){return minConn;} @Override public long getMaximumConnections(){return maxConn;}
    @Override public long getMaxRows(){return maxRows;} @Override public long getMaxAnalysisRows(){return maxAnalysisRows;}
    @Override public boolean isInheritChildSourceFilters(){return inheritChildFilters;}
}
class YFClientDataSourceImpl implements IYFClientDataSource {
    private long clientOrgId, sourceId; private String clientOrgRef, clientOrgName, sourceName;
    static { YFFactoryRegistry.registerFactory(IYFClientDataSource.class, YFClientDataSourceImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        clientOrgId=YFJsonHelper.getLong(j,"clientOrgId",0); clientOrgRef=YFJsonHelper.getString(j,"clientOrgRef","");
        clientOrgName=YFJsonHelper.getString(j,"clientOrgName",""); sourceId=YFJsonHelper.getLong(j,"sourceId",0);
        sourceName=YFJsonHelper.getString(j,"sourceName","");
    }
    @Override public long getClientOrgId(){return clientOrgId;} @Override public String getClientOrgRef(){return clientOrgRef;}
    @Override public String getClientOrgName(){return clientOrgName;} @Override public long getSourceId(){return sourceId;}
    @Override public String getSourceName(){return sourceName;}
}
