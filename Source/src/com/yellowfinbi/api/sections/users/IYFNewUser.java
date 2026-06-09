package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFNewUser extends IYFJSON {
    long getUserId(); void setUserId(long v);
    String getUserName(); void setUserName(String v);
    String getEmail(); void setEmail(String v);
    String getRoleCode(); void setRoleCode(String v);
    String getStatus(); void setStatus(String v);
    String getPassword(); void setPassword(String v);
    String getFirstName(); void setFirstName(String v);
    String getLastName(); void setLastName(String v);
    String getTitle(); void setTitle(String v);
    String getDescription(); void setDescription(String v);
    String getTimeZoneCode(); void setTimeZoneCode(String v);
    String getPreferredLanguageCode(); void setPreferredLanguageCode(String v);
    IYFUserPreferences getUserPreferences();
}
