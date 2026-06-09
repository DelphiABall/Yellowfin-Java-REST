package com.yellowfinbi.api.common;

/**
 * Connection configuration for a Yellowfin instance.
 */
public interface IYFServerDetails {
    String getHostURL();
    String getAppName();
    int getTimeout();
}
