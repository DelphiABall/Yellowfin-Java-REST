package com.yellowfinbi.api.sections.categories;
import com.yellowfinbi.api.common.*;
import org.json.JSONObject;
class YFSimpleCategoryModelImpl implements IYFSimpleCategoryModel {
    private String name, desc, uuid, parentUUID;
    static { YFFactoryRegistry.registerFactory(IYFSimpleCategoryModel.class, YFSimpleCategoryModelImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        name=YFJsonHelper.getString(j,"categoryName",""); desc=YFJsonHelper.getString(j,"categoryDescription","");
        uuid=YFJsonHelper.getString(j,"categoryUUID",""); parentUUID=YFJsonHelper.getString(j,"parentCategoryUUID","");
    }
    @Override public String getCategoryName(){return name;} @Override public String getCategoryDescription(){return desc;}
    @Override public String getCategoryUUID(){return uuid;} @Override public String getParentCategoryUUID(){return parentUUID;}
}
class YFCategoryImpl implements IYFCategory {
    private String uuid, name, desc, parentUUID; private long orgId, sortOrder; private boolean approvalRequired;
    static { YFFactoryRegistry.registerFactory(IYFCategory.class, YFCategoryImpl::new); }
    @Override public void loadFromJSON(JSONObject j) {
        uuid=YFJsonHelper.getString(j,"categoryUUID",""); orgId=YFJsonHelper.getLong(j,"categoryOrgId",0);
        name=YFJsonHelper.getString(j,"categoryName",""); desc=YFJsonHelper.getString(j,"categoryDescription","");
        parentUUID=YFJsonHelper.getString(j,"parentCategoryUUID",""); sortOrder=YFJsonHelper.getLong(j,"sortOrder",0);
        approvalRequired=YFJsonHelper.getBool(j,"approvalRequired",false);
    }
    @Override public String toJSON() { return asJSON().toString(); }
    @Override public JSONObject asJSON() {
        JSONObject j = new JSONObject();
        j.put("categoryName",name); j.put("categoryDescription",desc);
        if(!parentUUID.isBlank()) j.put("parentCategoryUUID",parentUUID);
        j.put("sortOrder",sortOrder); j.put("approvalRequired",approvalRequired);
        return j;
    }
    @Override public String getCategoryUUID(){return uuid;} @Override public long getCategoryOrgId(){return orgId;}
    @Override public String getCategoryName(){return name;} @Override public String getCategoryDescription(){return desc;}
    @Override public String getParentCategoryUUID(){return parentUUID;} @Override public long getSortOrder(){return sortOrder;}
    @Override public boolean isApprovalRequired(){return approvalRequired;}
}
