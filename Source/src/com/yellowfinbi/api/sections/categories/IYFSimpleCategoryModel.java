package com.yellowfinbi.api.sections.categories;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFSimpleCategoryModel extends IYFLoadFromJSON {
    String getCategoryName(); String getCategoryDescription(); String getCategoryUUID(); String getParentCategoryUUID();
}
