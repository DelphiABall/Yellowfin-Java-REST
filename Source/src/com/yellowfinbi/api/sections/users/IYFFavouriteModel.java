package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFFavouriteModel extends IYFLoadFromJSON {
    long getUserId(); String getName(); String getContentType(); String getTitle(); String getDescription();
    long getContentId(); String getContentUuid(); String getPublishedDate();
}
