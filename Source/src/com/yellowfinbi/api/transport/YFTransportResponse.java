package com.yellowfinbi.api.transport;

/**
 * Wraps the HTTP response from a Yellowfin API call.
 */
public class YFTransportResponse {

    private String data;
    private int status;

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    /** True when the HTTP status is in the 2xx range. */
    public boolean isSuccessful() { return status >= 200 && status < 300; }
}
