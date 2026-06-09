package com.yellowfinbi.api.sections.api;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFAPIInfo extends IYFLoadFromJSON {
    String getApiMajorVersion();
    String getApiMinorVersion();
    String getApiVersion();
    String getApplicationMajorVersion();
    String getApplicationMinorVersion();
    String getApplicationVersion();
    String getVersionPattern();
}
