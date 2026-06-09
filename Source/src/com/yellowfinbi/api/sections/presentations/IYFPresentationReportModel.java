package com.yellowfinbi.api.sections.presentations;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFPresentationReportModel extends IYFLoadFromJSON {
    long getSlideId(); String getDefaultDisplay(); String getEntityUUID();
    long getReportId(); String getName(); String getDescription(); boolean isFavouritedByUser(); long getCommentCount();
}
