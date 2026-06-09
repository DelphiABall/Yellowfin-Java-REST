package com.yellowfinbi.api.transport;

import com.yellowfinbi.api.common.IYFLoadFromJSON;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.common.YFFactoryRegistry;
import org.json.JSONObject;

import java.util.function.Supplier;

/**
 * Generic transport executor — the key DRY mechanism.
 * Handles: HTTP call → parse JSON → populate typed result.
 * Mirrors YFTransportGenericExecutor from C# and Delphi.
 */
public final class YFGenericExecutor {

    private YFGenericExecutor() { }

    /**
     * Execute a typed API call. Creates the result object via the factory registry.
     */
    public static <T extends IYFLoadFromJSON> ExecuteResult<T> executeTyped(
            Class<T> type,
            IYFServerDetails serverDetails,
            String accessToken,
            YFTransport.HttpVerb method,
            String endpoint,
            String requestBody) {
        return executeTyped(
                () -> YFFactoryRegistry.createNew(type),
                serverDetails, accessToken, method, endpoint, requestBody);
    }

    /**
     * Execute a typed API call with an explicit factory.
     */
    public static <T extends IYFLoadFromJSON> ExecuteResult<T> executeTyped(
            Supplier<T> factory,
            IYFServerDetails serverDetails,
            String accessToken,
            YFTransport.HttpVerb method,
            String endpoint,
            String requestBody) {

        YFTransport transport = new YFTransport(method, serverDetails, endpoint, accessToken, requestBody);
        YFTransportResponse response = transport.execute();

        T result = null;
        if (response.isSuccessful() && response.getData() != null && !response.getData().isBlank()) {
            JSONObject json = new JSONObject(response.getData());
            result = factory.get();
            result.loadFromJSON(json);
        }
        return new ExecuteResult<>(response, result);
    }

    /**
     * Execute a raw API call (no typed result — for DELETE, POST-only calls).
     */
    public static YFTransportResponse executeRaw(
            IYFServerDetails serverDetails,
            String accessToken,
            YFTransport.HttpVerb method,
            String endpoint,
            String requestBody) {

        YFTransport transport = new YFTransport(method, serverDetails, endpoint, accessToken, requestBody);
        return transport.execute();
    }

    /** Convenience overload for GET with no body. */
    public static YFTransportResponse executeRaw(
            IYFServerDetails serverDetails,
            String accessToken,
            YFTransport.HttpVerb method,
            String endpoint) {
        return executeRaw(serverDetails, accessToken, method, endpoint, null);
    }

    /**
     * Result container pairing the transport response with the typed model.
     */
    public static class ExecuteResult<T> {
        private final YFTransportResponse response;
        private final T result;

        public ExecuteResult(YFTransportResponse response, T result) {
            this.response = response;
            this.result = result;
        }

        public YFTransportResponse getResponse() { return response; }
        public T getResult() { return result; }
        public boolean isSuccessful() { return response.isSuccessful(); }
    }
}
