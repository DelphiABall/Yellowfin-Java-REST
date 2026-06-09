package com.yellowfinbi.api.transport;

import com.yellowfinbi.api.common.IYFServerDetails;
import org.json.JSONObject;

import java.time.Instant;

/**
 * Specialised transport for token endpoints.
 * Parses the security token, token ID, and expiry from the JSON response.
 */
public class YFTransportToken extends YFTransport {

    public YFTransportToken(HttpVerb verb,
                            IYFServerDetails serverDetails,
                            String endpoint,
                            String securityToken,
                            String requestBody) {
        super(verb, serverDetails, endpoint, securityToken, requestBody);
    }

    /** Legacy constructor for backward compatibility. */
    public YFTransportToken(HttpVerb verb, String hostURL, String appName,
                            String endpoint, String securityToken,
                            String requestBody, int timeout) {
        super(verb, hostURL, appName, endpoint, securityToken, requestBody, timeout);
    }

    /** Execute and parse a basic token response (securityToken field). */
    public YFTokenResponse executeToken() {
        YFTransportResponse raw = super.execute();
        YFTokenResponse token = new YFTokenResponse();
        token.setData(raw.getData());
        token.setStatus(raw.getStatus());

        if (raw.isSuccessful() && raw.getData() != null) {
            JSONObject json = new JSONObject(raw.getData());
            token.setToken(findSecurityToken(json));
        }
        return token;
    }

    /** Execute and parse an access token response (includes expiry). */
    public YFTokenResponse executeAccessToken() {
        YFTransportResponse raw = super.execute();
        YFTokenResponse token = new YFTokenResponse();
        token.setData(raw.getData());
        token.setStatus(raw.getStatus());

        if (raw.isSuccessful() && raw.getData() != null) {
            JSONObject json = new JSONObject(raw.getData());
            token.setToken(findSecurityToken(json));
            if (json.has("expiry")) {
                long expirySecs = json.getLong("expiry");
                token.setExpiry(Instant.now().plusSeconds(expirySecs));
            }
        }
        return token;
    }

    /** Execute and parse a refresh token response (includes token ID from _links). */
    public YFTokenResponse executeRefreshToken() {
        YFTransportResponse raw = super.execute();
        YFTokenResponse token = new YFTokenResponse();
        token.setData(raw.getData());
        token.setStatus(raw.getStatus());

        if (raw.isSuccessful() && raw.getData() != null) {
            JSONObject json = new JSONObject(raw.getData());
            token.setToken(findSecurityToken(json));

            // Parse token ID from _links.self.href (last path segment)
            if (json.has("_links")) {
                JSONObject links = json.getJSONObject("_links");
                if (links.has("self")) {
                    String href = links.getJSONObject("self").optString("href", "");
                    if (!href.isBlank()) {
                        String lastSegment = href.substring(href.lastIndexOf('/') + 1);
                        try { token.setTokenId(Long.parseLong(lastSegment)); }
                        catch (NumberFormatException ignored) { }
                    }
                }
            }
        }
        return token;
    }

    /** Recursively search for the "securityToken" field in the JSON. */
    private static String findSecurityToken(JSONObject json) {
        if (json.has("securityToken")) {
            return json.getString("securityToken");
        }
        for (String key : json.keySet()) {
            Object val = json.get(key);
            if (val instanceof JSONObject) {
                String found = findSecurityToken((JSONObject) val);
                if (found != null) return found;
            }
        }
        return null;
    }
}
