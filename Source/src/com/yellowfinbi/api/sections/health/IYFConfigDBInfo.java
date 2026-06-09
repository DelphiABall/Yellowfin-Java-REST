package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFConfigDBInfo extends IYFLoadFromJSON {
    String getStatus();
    int getMaxConnections();
    int getMinConnections();
    int getOpenConnections();
    int getActiveConnections();
}
