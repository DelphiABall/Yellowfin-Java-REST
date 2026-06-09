package com.yellowfinbi.api.sections.usergroups;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFUserGroupImpl implements IYFUserGroup {
    private long id; private String shortDesc, longDesc; private boolean secure; private YFGroupStatus status;
    static { YFFactoryRegistry.registerFactory(IYFUserGroup.class, YFUserGroupImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        id=YFJsonHelper.getLong(j,"userGroupId",0); shortDesc=YFJsonHelper.getString(j,"shortDescription","");
        longDesc=YFJsonHelper.getString(j,"longDescription",""); secure=YFJsonHelper.getBool(j,"isSecureGroup",false);
        status=YFJsonHelper.getEnum(j,"groupStatus",YFGroupStatus.class,YFGroupStatus.OPEN);
    }
    @Override public String toJSON(){return asJSON().toString();}
    @Override public JSONObject asJSON(){
        JSONObject j=new JSONObject(); j.put("shortDescription",shortDesc); j.put("longDescription",longDesc);
        j.put("isSecureGroup",secure); j.put("groupStatus",status.name()); return j;
    }
    @Override public long getUserGroupId(){return id;} @Override public String getShortDescription(){return shortDesc;}
    @Override public String getLongDescription(){return longDesc;} @Override public boolean isSecureGroup(){return secure;}
    @Override public YFGroupStatus getGroupStatus(){return status;}
}
class YFUserGroupMemberListItemImpl implements IYFUserGroupMemberListItem {
    private long groupId; private String memberId, entityCode, memberName;
    private YFMemberEntityType entityType; private YFMemberMembershipType membershipType;
    static { YFFactoryRegistry.registerFactory(IYFUserGroupMemberListItem.class, YFUserGroupMemberListItemImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        groupId=YFJsonHelper.getLong(j,"groupId",0); memberId=YFJsonHelper.getString(j,"memberId","");
        entityType=YFJsonHelper.getEnum(j,"entityType",YFMemberEntityType.class,YFMemberEntityType.PERSON);
        entityCode=YFJsonHelper.getString(j,"entityCode",""); memberName=YFJsonHelper.getString(j,"memberName","");
        membershipType=YFJsonHelper.getEnum(j,"membershipType",YFMemberMembershipType.class,YFMemberMembershipType.INCLUDED);
    }
    @Override public long getGroupId(){return groupId;} @Override public String getMemberId(){return memberId;}
    @Override public YFMemberEntityType getEntityType(){return entityType;} @Override public String getEntityCode(){return entityCode;}
    @Override public String getMemberName(){return memberName;} @Override public YFMemberMembershipType getMembershipType(){return membershipType;}
}
