package com.yellowfinbi.api.sections.filters;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFFilterValue extends IYFLoadFromJSON {
    String getDisplayText(); String getFilterValueCode(); boolean isDefault();
}
