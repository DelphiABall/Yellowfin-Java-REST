package com.yellowfinbi.api.sections.api;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.YFGenericExecutor;
import com.yellowfinbi.api.transport.YFTransport;

public final class YFAPI {
    private YFAPI() { }
    public static YFGenericExecutor.ExecuteResult<IYFAPIInfo> getAPIInformation(
            IYFServerDetails serverDetails, String accessToken) {
        return YFGenericExecutor.executeTyped(IYFAPIInfo.class, serverDetails, accessToken,
                YFTransport.HttpVerb.GET, "api", null);
    }
}
