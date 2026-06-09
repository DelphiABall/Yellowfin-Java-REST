package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFNodeCacheInfo extends IYFLoadFromJSON {
    NodeCacheType getCacheType();
    long getCacheCurrentSize();
    long getCacheMaxSize();
}
