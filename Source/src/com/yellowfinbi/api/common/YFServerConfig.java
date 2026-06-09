package com.yellowfinbi.api.common;

/**
 * Convenience container combining server details and admin credentials.
 */
public class YFServerConfig implements IYFServerDetails, IYFAdminCredentials {

    private final String hostURL;
    private final String appName;
    private final int timeout;
    private final String adminUserName;
    private final String adminPassword;
    private final String clientOrg;

    public YFServerConfig(String hostURL, String appName,
                          String adminUserName, String adminPassword,
                          String clientOrg, int timeout) {
        this.hostURL = hostURL;
        this.appName = appName;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
        this.clientOrg = clientOrg;
        this.timeout = timeout;
    }

    public YFServerConfig(String hostURL, String adminUserName, String adminPassword) {
        this(hostURL, "YELLOWFIN", adminUserName, adminPassword, "", 6);
    }

    @Override public String getHostURL() { return hostURL; }
    @Override public String getAppName() { return appName; }
    @Override public int getTimeout() { return timeout; }
    @Override public String getAdminUserName() { return adminUserName; }
    @Override public String getAdminPassword() { return adminPassword; }
    @Override public String getClientOrg() { return clientOrg; }

    public IYFServerDetails getServerDetails() { return this; }
    public IYFAdminCredentials getAdminCredentials() { return this; }
}
