package com.yellowfinbi.api.sections.datasources;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFClientDataSource extends IYFLoadFromJSON {
    long getClientOrgId(); String getClientOrgRef(); String getClientOrgName(); long getSourceId(); String getSourceName();
}
