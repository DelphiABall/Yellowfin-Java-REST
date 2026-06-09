package com.yellowfinbi.api.sections.content;
import com.yellowfinbi.api.common.*;
import com.yellowfinbi.api.sections.filters.*;
import com.yellowfinbi.api.transport.*;

public final class YFContentApi {
    private YFContentApi() { }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFContentItem>> getContent(
            IYFServerDetails server, String token, String filterJson, int maxItems) {
        String url = YFFilterHelper.buildURL("api/content", filterJson, maxItems);
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFContentItem.class),
                server, token, YFTransport.HttpVerb.GET, url, null);
    }
    public static YFGenericExecutor.ExecuteResult<IYFSearchOptions> getAvailableMetadataForContentList(
            IYFServerDetails server, String token) {
        return YFGenericExecutor.executeTyped(IYFSearchOptions.class, server, token,
                YFTransport.HttpVerb.GET, "api/content/metadata", null);
    }
    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFContentItem>> getContentForUser(
            IYFServerDetails server, String token, long userId, String filterJson, int maxItems) {
        String url = YFFilterHelper.buildURL("api/admin/users/" + userId + "/content", filterJson, maxItems);
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFContentItem.class),
                server, token, YFTransport.HttpVerb.GET, url, null);
    }
}
