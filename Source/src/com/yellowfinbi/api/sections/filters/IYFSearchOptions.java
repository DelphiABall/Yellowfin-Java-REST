package com.yellowfinbi.api.sections.filters;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import java.util.List;
public interface IYFSearchOptions extends IYFLoadFromJSON {
    List<? extends IYFPossibleFilter> getPossibleFilters();
    List<? extends IYFSortOption> getPossibleSortOptions();
}
