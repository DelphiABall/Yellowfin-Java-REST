package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFUserPatch extends IYFJSON {
    String getFirstName(); void setFirstName(String v);
    String getLastName(); void setLastName(String v);
    String getTitle(); void setTitle(String v);
    String getDescription(); void setDescription(String v);
    String getPreferredLanguageCode(); void setPreferredLanguageCode(String v);
    String getTimeZoneCode(); void setTimeZoneCode(String v);
    IYFUserPreferences getUserPreferences();
}
