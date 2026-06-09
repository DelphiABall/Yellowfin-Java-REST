package com.yellowfinbi.api.sections.filters;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class YFFilterHelper {
    private YFFilterHelper() { }
    public static String buildURL(String baseEndpoint, String filterJson, int maxItems) {
        StringBuilder sb = new StringBuilder(baseEndpoint);
        String sep = "?";
        if (maxItems > 0) { sb.append(sep).append("limit=").append(maxItems); sep = "&"; }
        if (filterJson != null && !filterJson.isBlank()) {
            sb.append(sep).append("filters=").append(URLEncoder.encode(filterJson, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }
}
