package com.yellowfinbi.api.sections.health;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.YFGenericExecutor;
import com.yellowfinbi.api.transport.YFTransport;

public final class YFHealthApi {
    private YFHealthApi() { }
    public static YFGenericExecutor.ExecuteResult<IYFHealth> getHealth(
            IYFServerDetails serverDetails, String accessToken) {
        return YFGenericExecutor.executeTyped(IYFHealth.class, serverDetails, accessToken,
                YFTransport.HttpVerb.GET, "api/health", null);
    }
}
