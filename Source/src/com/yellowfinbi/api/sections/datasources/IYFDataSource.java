package com.yellowfinbi.api.sections.datasources;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFDataSource extends IYFLoadFromJSON {
    long getSourceId(); String getSourceName(); String getSourceDescription(); String getSourceType();
    String getConnectionType(); String getConnectionTypeCode(); String getConnectionString();
    long getConnectionTimeout(); String getUserName(); long getMinimumConnections(); long getMaximumConnections();
    long getMaxRows(); long getMaxAnalysisRows(); boolean isInheritChildSourceFilters();
}
