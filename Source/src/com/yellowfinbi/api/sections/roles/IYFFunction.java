package com.yellowfinbi.api.sections.roles;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFFunction extends IYFJSON {
    String getFunctionCode(); String getFunctionName(); String getFunctionTypeCode();
    String getAccessLevelCode(); String getFunctionDescription();
}
