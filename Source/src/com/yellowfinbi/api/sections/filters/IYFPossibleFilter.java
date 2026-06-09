package com.yellowfinbi.api.sections.filters;
import com.yellowfinbi.api.common.IYFLoadFromJSON;
import java.util.List;
public interface IYFPossibleFilter extends IYFLoadFromJSON {
    String getPropertyName(); FilterOperator getOperator(); List<? extends IYFFilterValue> getPossibleFilterValues();
}
