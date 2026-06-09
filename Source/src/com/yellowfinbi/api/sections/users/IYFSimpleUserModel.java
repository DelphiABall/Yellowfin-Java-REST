package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFSimpleUserModel extends IYFLoadFromJSON {
    long getUserId(); String getName(); String getEmail(); String getUserConnectionStatus();
}
