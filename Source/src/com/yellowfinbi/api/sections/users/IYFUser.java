package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFUser extends IYFJSON {
    long getUserId(); String getUserName(); String getEmail();
    String getFirstName(); String getLastName(); String getTitle(); String getDescription();
    String getStatus(); String getRoleCode(); String getTimeZoneCode(); String getPreferredLanguageCode();
    boolean isCurrentUser(); int getNumStories(); int getNumFollowers(); int getNumFollowing(); int getNumDiscussionStreams();
    IYFUserPreferences getUserPreferences();
}
