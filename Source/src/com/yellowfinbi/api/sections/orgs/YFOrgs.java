package com.yellowfinbi.api.sections.orgs;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFOrgImpl implements IYFOrg {
    private long ipOrg; private String clientRefId, name, defaultTimezone; private boolean isDefaultOrg;
    static { YFFactoryRegistry.registerFactory(IYFOrg.class, YFOrgImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        ipOrg=YFJsonHelper.getLong(j,"ipOrg",0); clientRefId=YFJsonHelper.getString(j,"clientRefId","");
        name=YFJsonHelper.getString(j,"name",""); defaultTimezone=YFJsonHelper.getString(j,"defaultTimezone","");
        isDefaultOrg=YFJsonHelper.getBool(j,"isDefaultOrg",false);
    }
    @Override public long getIpOrg(){return ipOrg;} @Override public String getClientRefId(){return clientRefId;}
    @Override public String getName(){return name;} @Override public String getDefaultTimezone(){return defaultTimezone;}
    @Override public boolean isDefaultOrg(){return isDefaultOrg;}
}
