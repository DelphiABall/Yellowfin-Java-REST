package com.yellowfinbi.api.sections.categories;
import com.yellowfinbi.api.common.IYFJSON;
public interface IYFCategory extends IYFJSON {
    String getCategoryUUID(); long getCategoryOrgId(); String getCategoryName(); String getCategoryDescription();
    String getParentCategoryUUID(); long getSortOrder(); boolean isApprovalRequired();
}
