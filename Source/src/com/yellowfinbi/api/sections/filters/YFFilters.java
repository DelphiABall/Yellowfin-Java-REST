package com.yellowfinbi.api.sections.filters;
import com.yellowfinbi.api.common.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

class YFFilterValueImpl implements IYFFilterValue {
    private String displayText, filterValueCode; private boolean isDefault;
    static { YFFactoryRegistry.registerFactory(IYFFilterValue.class, YFFilterValueImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        displayText = YFJsonHelper.getString(json, "displayText", "");
        filterValueCode = YFJsonHelper.getString(json, "filterValueCode", "");
        isDefault = YFJsonHelper.getBool(json, "isDefault", false);
    }
    @Override public String getDisplayText() { return displayText; }
    @Override public String getFilterValueCode() { return filterValueCode; }
    @Override public boolean isDefault() { return isDefault; }
}

class YFPossibleFilterImpl implements IYFPossibleFilter {
    private String propertyName; private FilterOperator operator;
    private final List<IYFFilterValue> values = new ArrayList<>();
    static { YFFactoryRegistry.registerFactory(IYFPossibleFilter.class, YFPossibleFilterImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        JSONObject meta = YFJsonHelper.getObject(json, "filterMetadata");
        if (meta != null) {
            propertyName = YFJsonHelper.getString(meta, "propertyName", "");
            operator = YFJsonHelper.getEnum(meta, "operator", FilterOperator.class, FilterOperator.EQUAL_TO);
        }
        values.clear();
        JSONArray arr = YFJsonHelper.getArray(json, "possibleFilterValues");
        if (arr != null) { for (int i = 0; i < arr.length(); i++) {
            IYFFilterValue fv = new YFFilterValueImpl(); fv.loadFromJSON(arr.getJSONObject(i)); values.add(fv);
        }}
    }
    @Override public String getPropertyName() { return propertyName; }
    @Override public FilterOperator getOperator() { return operator; }
    @Override public List<IYFFilterValue> getPossibleFilterValues() { return values; }
}

class YFSortOptionImpl implements IYFSortOption {
    private String displayName, propertyName; private SortDirection dir; private boolean isDefault;
    static { YFFactoryRegistry.registerFactory(IYFSortOption.class, YFSortOptionImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        displayName = YFJsonHelper.getString(json, "displayName", "");
        propertyName = YFJsonHelper.getString(json, "propertyName", "");
        dir = YFJsonHelper.getEnum(json, "defaultSortDirection", SortDirection.class, SortDirection.ASC);
        isDefault = YFJsonHelper.getBool(json, "isDefault", false);
    }
    @Override public String getDisplayName() { return displayName; }
    @Override public SortDirection getDefaultSortDirection() { return dir; }
    @Override public boolean isDefault() { return isDefault; }
    @Override public String getPropertyName() { return propertyName; }
}

class YFSearchOptionsImpl implements IYFSearchOptions {
    private final List<IYFPossibleFilter> filters = new ArrayList<>();
    private final List<IYFSortOption> sorts = new ArrayList<>();
    static { YFFactoryRegistry.registerFactory(IYFSearchOptions.class, YFSearchOptionsImpl::new); }
    @Override public void loadFromJSON(JSONObject json) {
        filters.clear(); sorts.clear();
        JSONArray fa = YFJsonHelper.getArray(json, "possibleFilters");
        if (fa != null) { for (int i = 0; i < fa.length(); i++) {
            IYFPossibleFilter f = new YFPossibleFilterImpl(); f.loadFromJSON(fa.getJSONObject(i)); filters.add(f);
        }}
        JSONArray sa = YFJsonHelper.getArray(json, "possibleSortOptions");
        if (sa != null) { for (int i = 0; i < sa.length(); i++) {
            IYFSortOption s = new YFSortOptionImpl(); s.loadFromJSON(sa.getJSONObject(i)); sorts.add(s);
        }}
    }
    @Override public List<IYFPossibleFilter> getPossibleFilters() { return filters; }
    @Override public List<IYFSortOption> getPossibleSortOptions() { return sorts; }
}
