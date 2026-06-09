package com.yellowfinbi.api.transport;

import java.time.Instant;

/**
 * Token-specific response from the Yellowfin API.
 * Extends YFTransportResponse with token, tokenId, and expiry fields.
 */
public class YFTokenResponse extends YFTransportResponse {

    private String token;
    private long tokenId;
    private Instant expiry;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public long getTokenId() { return tokenId; }
    public void setTokenId(long tokenId) { this.tokenId = tokenId; }

    public Instant getExpiry() { return expiry; }
    public void setExpiry(Instant expiry) { this.expiry = expiry; }
}
