package com.yellowfinbi.api.sections.usergroups;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFUserGroupMemberListItem extends IYFLoadFromJSON {
    long getGroupId(); String getMemberId(); YFMemberEntityType getEntityType();
    String getEntityCode(); String getMemberName(); YFMemberMembershipType getMembershipType();
}
