package com.yellowfinbi.api.sections.filters;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
public interface IYFSortOption extends IYFLoadFromJSON {
    String getDisplayName(); SortDirection getDefaultSortDirection(); boolean isDefault(); String getPropertyName();
}
