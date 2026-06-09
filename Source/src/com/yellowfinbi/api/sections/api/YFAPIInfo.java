package com.yellowfinbi.api.sections.api;
import com.yellowfinbi.api.common.YFFactoryRegistry;
import com.yellowfinbi.api.common.YFJsonHelper;
import org.json.JSONObject;

public class YFAPIInfo implements IYFAPIInfo {
    private String apiMajorVersion, apiMinorVersion, apiVersion;
    private String applicationMajorVersion, applicationMinorVersion, applicationVersion;
    private String versionPattern;

    static { YFFactoryRegistry.registerFactory(IYFAPIInfo.class, YFAPIInfo::new); }

    @Override public void loadFromJSON(JSONObject json) {
        apiMajorVersion = YFJsonHelper.getString(json, "apiMajorVersion", "");
        apiMinorVersion = YFJsonHelper.getString(json, "apiMinorVersion", "");
        apiVersion = YFJsonHelper.getString(json, "apiVersion", "");
        applicationMajorVersion = YFJsonHelper.getString(json, "applicationMajorVersion", "");
        applicationMinorVersion = YFJsonHelper.getString(json, "applicationMinorVersion", "");
        applicationVersion = YFJsonHelper.getString(json, "applicationVersion", "");
        versionPattern = YFJsonHelper.getString(json, "versionPattern", "");
    }
    @Override public String getApiMajorVersion() { return apiMajorVersion; }
    @Override public String getApiMinorVersion() { return apiMinorVersion; }
    @Override public String getApiVersion() { return apiVersion; }
    @Override public String getApplicationMajorVersion() { return applicationMajorVersion; }
    @Override public String getApplicationMinorVersion() { return applicationMinorVersion; }
    @Override public String getApplicationVersion() { return applicationVersion; }
    @Override public String getVersionPattern() { return versionPattern; }
}
