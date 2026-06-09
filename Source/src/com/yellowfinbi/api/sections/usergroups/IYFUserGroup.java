package com.yellowfinbi.api.sections.usergroups;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFUserGroup extends IYFJSON {
    long getUserGroupId(); String getShortDescription(); String getLongDescription();
    boolean isSecureGroup(); YFGroupStatus getGroupStatus();
}
