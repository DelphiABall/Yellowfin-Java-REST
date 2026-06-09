package com.yellowfinbi.api.transport;

import com.yellowfinbi.api.common.IYFServerDetails;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;

/**
 * Yellowfin-specific HTTP transport using the built-in java.net.http.HttpClient.
 * Constructs the Authorization header per the Yellowfin REST API v4 spec.
 */
public class YFTransport {

    public enum HttpVerb { GET, POST, PUT, PATCH, DELETE }

    private static final String YF_ACCEPT = "application/vnd.yellowfin.api-v2+json";
    private static final int DEFAULT_TIMEOUT = 6;

    protected final HttpClient httpClient;
    protected final HttpRequest httpRequest;
    private String accepts = YF_ACCEPT;

    public YFTransport(HttpVerb verb,
                       IYFServerDetails serverDetails,
                       String endpoint,
                       String securityToken,
                       String requestBody) {
        this(verb, serverDetails, endpoint, securityToken, requestBody, YF_ACCEPT);
    }

    public YFTransport(HttpVerb verb,
                       IYFServerDetails serverDetails,
                       String endpoint,
                       String securityToken,
                       String requestBody,
                       String acceptHeader) {
        this.accepts = acceptHeader;
        int timeout = serverDetails.getTimeout() > 0 ? serverDetails.getTimeout() : DEFAULT_TIMEOUT;

        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(timeout))
                .build();

        String baseUrl = serverDetails.getHostURL();
        if (!baseUrl.endsWith("/")) baseUrl += "/";
        String url = baseUrl + endpoint;

        String authHeader = buildAuthHeader(serverDetails.getAppName(), securityToken);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeout))
                .header("Authorization", authHeader)
                .header("Accept", this.accepts)
                .header("Content-Type", "application/json");

        HttpRequest.BodyPublisher bodyPublisher =
                (requestBody != null && !requestBody.isBlank())
                        ? HttpRequest.BodyPublishers.ofString(requestBody)
                        : HttpRequest.BodyPublishers.noBody();

        switch (verb) {
            case GET:    builder.GET(); break;
            case POST:   builder.POST(bodyPublisher); break;
            case PUT:    builder.PUT(bodyPublisher); break;
            case PATCH:  builder.method("PATCH", bodyPublisher); break;
            case DELETE: builder.DELETE(); break;
        }

        this.httpRequest = builder.build();
    }

    /** Legacy constructor for backward compatibility (raw strings). */
    public YFTransport(HttpVerb verb, String hostURL, String appName,
                       String endpoint, String securityToken,
                       String requestBody, int timeout) {
        this(verb, new SimpleServerDetails(hostURL, appName, timeout),
             endpoint, securityToken, requestBody);
    }

    public YFTransportResponse execute() {
        YFTransportResponse result = new YFTransportResponse();
        try {
            HttpResponse<String> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());
            result.setStatus(response.statusCode());
            result.setData(response.body());
        } catch (Exception e) {
            result.setStatus(555);
            result.setData(e.getMessage());
        }
        return result;
    }

    public byte[] executeForBytes() {
        try {
            HttpResponse<byte[]> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            }
        } catch (Exception e) { /* fall through */ }
        return new byte[0];
    }

    public String getAccepts() { return accepts; }
    public void setAccepts(String accepts) { this.accepts = accepts; }

    public static String epoch() {
        return Long.toString(System.currentTimeMillis());
    }

    public static int defaultTimeout() { return DEFAULT_TIMEOUT; }

    private static String buildAuthHeader(String appName, String securityToken) {
        String header = appName + " ts=" + epoch() + ", nonce=" + UUID.randomUUID();
        if (securityToken != null && !securityToken.isBlank()) {
            header += ", token=" + securityToken;
        }
        return header;
    }

    /** Minimal IYFServerDetails for the legacy constructor. */
    private static class SimpleServerDetails implements IYFServerDetails {
        private final String hostURL, appName;
        private final int timeout;
        SimpleServerDetails(String hostURL, String appName, int timeout) {
            this.hostURL = hostURL; this.appName = appName; this.timeout = timeout;
        }
        @Override public String getHostURL() { return hostURL; }
        @Override public String getAppName() { return appName; }
        @Override public int getTimeout() { return timeout; }
    }
}
