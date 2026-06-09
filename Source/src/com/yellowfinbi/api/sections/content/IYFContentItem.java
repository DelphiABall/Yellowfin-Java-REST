package com.yellowfinbi.api.sections.content;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import com.yellowfinbi.api.common.YFStatusCode;
public interface IYFContentItem extends IYFLoadFromJSON {
    long getContentId(); String getContentUuid(); String getName(); String getDescription();
    String getContentType(); String getCategoryCode(); String getCategoryName();
    String getSubCategoryCode(); String getSubCategoryName(); YFStatusCode getStatusCode();
    long getViewId(); String getViewName(); long getSourceId(); String getSourceName();
    String getAccessTypeCode(); String getFormattedUpdatedDate(); String getFormattedPublishedDate();
    long getCreatorId(); String getCreatorName(); long getLastUpdaterId(); String getLastUpdaterName();
    boolean isFavouritedByUser(); long getCommentCount();
}
