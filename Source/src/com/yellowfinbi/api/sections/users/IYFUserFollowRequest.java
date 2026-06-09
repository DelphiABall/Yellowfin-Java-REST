package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFUserFollowRequest extends IYFLoadFromJSON {
    long getSubjectUserId(); long getObjectUserId(); String getUserConnectionStatus();
}
