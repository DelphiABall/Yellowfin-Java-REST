package com.yellowfinbi.api.security;

import com.yellowfinbi.api.common.IYFAdminCredentials;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.YFTokenResponse;
import com.yellowfinbi.api.transport.YFTransport;
import com.yellowfinbi.api.transport.YFTransportToken;
import org.json.JSONObject;

import java.util.List;

/**
 * Static methods for all token CRUD operations against the Yellowfin REST API.
 * Mirrors YFTokenRequests from C# and TYF_TokenRequests from Delphi.
 */
public final class YFTokenRequests {

    private YFTokenRequests() { }

    /** Creates a Refresh Token — long-lived, like an application key. */
    public static YFTokenResponse getRefreshToken(
            IYFServerDetails serverDetails,
            IYFAdminCredentials adminCredentials,
            String accessToken) {

        JSONObject body = new JSONObject();
        body.put("userName", adminCredentials.getAdminUserName());
        body.put("clientOrgRef", adminCredentials.getClientOrg());
        String password = adminCredentials.getAdminPassword();
        if (password != null && !password.isBlank()) {
            body.put("password", password);
        }

        String endpoint;
        if (accessToken == null || accessToken.isBlank()) {
            endpoint = "api/refresh-tokens";
        } else if (password == null || password.isBlank()) {
            endpoint = "api/refresh-tokens?singleSignOn=true&noPassword=true";
        } else {
            endpoint = "api/refresh-tokens?singleSignOn=true";
        }

        YFTransportToken transport = new YFTransportToken(
                YFTransport.HttpVerb.POST, serverDetails, endpoint, accessToken, body.toString());
        return transport.executeRefreshToken();
    }

    /** Convenience overload — no existing access token. */
    public static YFTokenResponse getRefreshToken(
            IYFServerDetails serverDetails,
            IYFAdminCredentials adminCredentials) {
        return getRefreshToken(serverDetails, adminCredentials, "");
    }

    /** Deletes a refresh token by its server-assigned ID. */
    public static boolean deleteRefreshToken(
            IYFServerDetails serverDetails,
            String accessToken,
            long tokenId) {
        YFTransport transport = new YFTransport(
                YFTransport.HttpVerb.DELETE, serverDetails,
                "api/refresh-tokens/" + tokenId, accessToken, null);
        return transport.execute().isSuccessful();
    }

    /** Creates an Access Token from a Refresh Token. */
    public static YFTokenResponse getAccessToken(
            IYFServerDetails serverDetails,
            String refreshToken) {
        YFTransportToken transport = new YFTransportToken(
                YFTransport.HttpVerb.POST, serverDetails,
                "api/access-tokens", refreshToken, null);
        return transport.executeAccessToken();
    }

    /** Creates a Login Token for a specific user — requires an Access Token. */
    public static YFTokenResponse getLoginToken(
            IYFServerDetails serverDetails,
            String accessToken,
            String userId,
            String userPassword,
            String clientOrg,
            List<String> loginParameters,
            String customerParameters) {

        JSONObject signOnUser = new JSONObject();
        signOnUser.put("userName", userId);
        if (userPassword != null && !userPassword.isBlank()) {
            signOnUser.put("password", userPassword);
        }
        if (clientOrg != null && !clientOrg.isBlank()) {
            signOnUser.put("clientOrgRef", clientOrg);
        }

        JSONObject body = new JSONObject();
        body.put("signOnUser", signOnUser);
        body.put("noPassword", (userPassword == null || userPassword.isBlank()) ? "True" : "False");
        if (loginParameters != null && !loginParameters.isEmpty()) {
            body.put("loginParameters", loginParameters);
        }
        if (customerParameters != null && !customerParameters.isBlank()) {
            body.put("customParameters", customerParameters);
        }

        YFTransportToken transport = new YFTransportToken(
                YFTransport.HttpVerb.POST, serverDetails,
                "api/login-tokens", accessToken, body.toString());
        return transport.executeToken();
    }

    /** Creates an SSO Login Token — admin credentials in the body, no existing access token. */
    public static YFTokenResponse getLoginTokenSSO(
            IYFServerDetails serverDetails,
            IYFAdminCredentials adminCredentials,
            String userId,
            String userPassword,
            String clientOrg,
            List<String> loginParameters,
            String customerParameters) {

        JSONObject signOnUser = new JSONObject();
        signOnUser.put("userName", userId);
        if (userPassword != null && !userPassword.isBlank()) signOnUser.put("password", userPassword);
        if (clientOrg != null && !clientOrg.isBlank()) signOnUser.put("clientOrgRef", clientOrg);

        JSONObject adminUser = new JSONObject();
        adminUser.put("userName", adminCredentials.getAdminUserName());
        adminUser.put("password", adminCredentials.getAdminPassword());

        JSONObject body = new JSONObject();
        body.put("signOnUser", signOnUser);
        body.put("adminUser", adminUser);
        body.put("noPassword", (userPassword == null || userPassword.isBlank()) ? "True" : "False");
        if (loginParameters != null) body.put("loginParameters", loginParameters);
        if (customerParameters != null && !customerParameters.isBlank()) body.put("customParameters", customerParameters);

        YFTransportToken transport = new YFTransportToken(
                YFTransport.HttpVerb.POST, serverDetails,
                "api/rpc/login-tokens/create-sso-token", "", body.toString());
        return transport.executeToken();
    }

    /** Creates a user-specific refresh token without requiring their password. */
    public static YFTokenResponse getUserRefreshTokenNoPassword(
            IYFServerDetails serverDetails,
            String adminAccessToken,
            String userId) {
        JSONObject body = new JSONObject();
        body.put("userName", userId);
        YFTransportToken transport = new YFTransportToken(
                YFTransport.HttpVerb.POST, serverDetails,
                "api/refresh-tokens?singleSignOn=true&noPassword=true",
                adminAccessToken, body.toString());
        return transport.executeRefreshToken();
    }
}
