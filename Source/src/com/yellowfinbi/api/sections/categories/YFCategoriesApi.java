package com.yellowfinbi.api.sections.categories;
import com.yellowfinbi.api.common.*;
import com.yellowfinbi.api.transport.*;
public final class YFCategoriesApi {
    private YFCategoriesApi(){}
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleCategoryModel>> getCategories(IYFServerDetails s, String t) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFSimpleCategoryModel.class), s,t,YFTransport.HttpVerb.GET,"api/content-categories",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFCategory> getCategory(IYFServerDetails s, String t, String uuid) {
        return YFGenericExecutor.executeTyped(IYFCategory.class,s,t,YFTransport.HttpVerb.GET,"api/content-categories/"+uuid,null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleCategoryModel>> getCategorySubCategories(IYFServerDetails s, String t, String uuid) {
        return YFGenericExecutor.executeTyped(()->new YFModelList<>(IYFSimpleCategoryModel.class), s,t,YFTransport.HttpVerb.GET,"api/content-categories/"+uuid+"/children",null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFCategory> createCategory(IYFServerDetails s, String t, IYFCategory cat) {
        return YFGenericExecutor.executeTyped(IYFCategory.class,s,t,YFTransport.HttpVerb.POST,"api/content-categories",cat.toJSON());
    }
    public static YFGenericExecutor.ExecuteResult<IYFCategory> updateCategory(IYFServerDetails s, String t, IYFCategory cat) {
        return YFGenericExecutor.executeTyped(IYFCategory.class,s,t,YFTransport.HttpVerb.PATCH,"api/content-categories/"+cat.getCategoryUUID(),cat.toJSON());
    }
    public static YFTransportResponse deleteCategory(IYFServerDetails s, String t, String uuid) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.DELETE,"api/content-categories/"+uuid);
    }
    public static YFTransportResponse refreshCategoryAccessLevels(IYFServerDetails s, String t, String uuid) {
        return YFGenericExecutor.executeRaw(s,t,YFTransport.HttpVerb.POST,"api/rpc/content-categories/"+uuid+"/refresh-access",null);
    }
}
