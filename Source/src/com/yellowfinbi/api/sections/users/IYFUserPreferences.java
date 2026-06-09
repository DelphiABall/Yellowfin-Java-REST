package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFUserPreferences extends IYFLoadFromJSON {
    String getEntryPage(); int getDraftContentItemCount(); int getRecentContentItemCount();
    boolean isRetainContentTypeFilterOnBrowsePage();
    String getAllowUsersToConnect(); String getAllowUsersToViewTimeline(); String getAllowUsersToPostOnTimeline();
}
