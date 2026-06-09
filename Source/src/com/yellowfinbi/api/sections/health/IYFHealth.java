package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import com.yellowfinbi.api.common.YFModelList;
public interface IYFHealth extends IYFLoadFromJSON {
    String getStatus();
    YFModelList<? extends IYFNodeInfo> getNodes();
}
