package com.yellowfinbi.api.security;

import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.YFTokenResponse;

import java.time.Instant;

/**
 * Manages a single access token with automatic refresh on expiry.
 * Mirrors YFAccessTokenManager from C# and Delphi.
 */
public class YFAccessTokenManager {

    private final IYFServerDetails serverDetails;
    private final String refreshToken;
    private final boolean autoRefresh;
    private YFTokenResponse response;

    public YFAccessTokenManager(IYFServerDetails serverDetails,
                                String refreshToken,
                                boolean autoRefresh) {
        this.serverDetails = serverDetails;
        this.refreshToken = refreshToken;
        this.autoRefresh = autoRefresh;
        if (autoRefresh) refresh();
    }

    /** Returns the current access token, auto-refreshing if expired. */
    public String getAccessToken() {
        if (isTokenExpired() && autoRefresh) {
            refresh();
        }
        return response != null ? response.getToken() : null;
    }

    public Instant getExpiry() {
        return response != null ? response.getExpiry() : Instant.MIN;
    }

    public boolean isTokenExpired() {
        return response == null || getExpiry().isBefore(Instant.now());
    }

    public boolean isAutoRefresh() { return autoRefresh; }
    public IYFServerDetails getServerDetails() { return serverDetails; }
    public YFTokenResponse getResponse() { return response; }

    /** Force a new access token request. */
    public void refresh() {
        this.response = YFTokenRequests.getAccessToken(serverDetails, refreshToken);
    }
}
