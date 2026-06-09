package com.yellowfinbi.api.sections.orgs;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFOrg extends IYFLoadFromJSON {
    long getIpOrg(); String getClientRefId(); String getName(); String getDefaultTimezone(); boolean isDefaultOrg();
}
