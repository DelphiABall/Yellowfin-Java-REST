package com.yellowfinbi.api.sections.roles;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFRole extends IYFJSON {
    String getRoleName(); String getRoleDescription(); String getRoleCode();
    boolean isMandatoryFlag(); boolean isDefaultFlag(); boolean isGuestFlag();
}
