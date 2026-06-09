package com.yellowfinbi.api.common;

/**
 * Authentication credentials for an admin user.
 */
public interface IYFAdminCredentials {
    String getAdminUserName();
    String getAdminPassword();
    String getClientOrg();
}
