package com.yellowfinbi.connector;

import com.yellowfinbi.api.common.*;
import com.yellowfinbi.api.transport.*;
import com.yellowfinbi.api.security.*;
import com.yellowfinbi.api.sections.reports.*;
import com.yellowfinbi.api.sections.users.*;

import com.yellowfinbi.api.common.YFFactoryRegistration;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Yellowfin REST API — Java Example Application.
 *
 * Mirrors the logic and flow of the C# and TypeScript examples:
 *   1. Load/prompt credentials (INI file + interactive prompts)
 *   2. Admin Refresh Token → Access Token
 *   3. List users, select one
 *   4. User-specific tokens
 *   5. SSO login URLs (with and without admin tokens)
 *   6. List reports
 *   7. Favourite the first report
 *   8. Cleanup — delete refresh tokens
 *
 * Usage:
 *   mvn exec:java
 *   — or pass overrides via system properties: —
 *   mvn exec:java -Dyf.url=https://sandbox.yellowfinbi.com -Dyf.user=admin@example.com ...
 */
public class ExampleProject {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // ─── Initialise the factory registry ─────────────────────
        YFFactoryRegistration.registerAll();

        // ─── INI Configuration ───────────────────────────────────
        YFSettingsINI ini = new YFSettingsINI("YFSandbox.ini");

        String yfUrl       = param("yf.url",      ini.readString("SANDBOX", "URL",           "https://sandbox.yellowfinbi.com"));
        String yfInstance  = param("yf.instance",  ini.readString("SANDBOX", "INSTANCE_NAME", "YELLOWFIN"));
        String yfUser      = param("yf.user",      ini.readString("SANDBOX", "USERNAME",      ""));
        String yfPassword  = param("yf.password",  decodeBase64(ini.readString("SANDBOX", "PASSWORD", "")));
        String yfOrg       = param("yf.org",       ini.readString("SANDBOX", "TENANT",        ""));
        int    yfTimeout   = Integer.parseInt(param("yf.timeout", String.valueOf(ini.readInteger("SANDBOX", "TIMEOUT", 6))));

        // If credentials exist, offer to change them
        if (!yfUser.isEmpty() || !yfPassword.isEmpty() || !yfOrg.isEmpty()) {
            System.out.println("You are about to connect to " + yfOrg + " on " + yfUrl);
            System.out.println("With user " + yfUser);
            System.out.println("Press C to change details, or Enter to continue");
            String response = scanner.nextLine().trim().toUpperCase();
            if ("C".equals(response)) {
                yfUser = "";
                yfPassword = "";
                yfOrg = "";
            }
        }

        // Prompt for missing credentials
        while (yfUser.isEmpty()) {
            System.out.println("What is your SANDBOX user name?");
            yfUser = scanner.nextLine().trim();
            ini.writeString("SANDBOX", "USERNAME", yfUser);
        }

        while (yfPassword.isEmpty()) {
            System.out.println("What is your SANDBOX password?");
            yfPassword = scanner.nextLine().trim();

            System.out.println("Would you like to save the password for subsequent runs? Y or N");
            String save = scanner.nextLine().trim().toUpperCase();
            if ("Y".equals(save))
                ini.writeString("SANDBOX", "PASSWORD", Base64.getEncoder().encodeToString(yfPassword.getBytes(StandardCharsets.UTF_8)));
            else
                ini.writeString("SANDBOX", "PASSWORD", "");
        }

        while (yfOrg.isEmpty()) {
            System.out.println("What is your SANDBOX Tenant_ID / Org_ID?");
            yfOrg = scanner.nextLine().trim();
            ini.writeString("SANDBOX", "TENANT", yfOrg);
        }

        YFServerConfig config = new YFServerConfig(yfUrl, yfInstance, yfUser, yfPassword, yfOrg, yfTimeout);

        // ─── Welcome ─────────────────────────────────────────────
        System.out.println();
        System.out.println("Welcome to the Yellowfin REST API Java Sample!");
        System.out.println();
        System.out.println("For more details on the API, visit https://developers.yellowfinbi.com/dev/api-docs/current");
        System.out.println("This is designed to provide helper methods to get you started quickly.");
        System.out.println();
        System.out.println("The Yellowfin API uses 3 different tokens: Refresh, Access and Login");
        System.out.println("> \"Refresh Token\" - is long lasting and is linked to a specific user");
        System.out.println("> \"Access Token\"  - is short lived, to provide access to the API on behalf of the Refresh Token User");
        System.out.println("> \"Login Token\"   - is single use, used for single sign on");
        System.out.println();
        System.out.println("Press Enter to continue");
        scanner.nextLine();

        // Track refresh token IDs for cleanup
        List<Long> refreshTokenIds = new ArrayList<>();

        // ─── Refresh Token ───────────────────────────────────────
        System.out.println();
        System.out.println("Refresh Token: - Required to get an Access Token");
        YFTokenResponse adminRefreshToken = YFTokenRequests.getRefreshToken(config, config);

        System.out.println("Status: " + adminRefreshToken.getStatus());
        System.out.println("Refresh Token: " + adminRefreshToken.getToken());
        System.out.println("Refresh Token ID: " + adminRefreshToken.getTokenId());
        System.out.println();

        if (!adminRefreshToken.isSuccessful()) {
            System.out.println("Unsuccessful Response. Terminating.");
            return;
        }

        refreshTokenIds.add(adminRefreshToken.getTokenId());

        // ─── Access Token ────────────────────────────────────────
        System.out.println("Access Token: (lasts 20 minutes - 1200 seconds) - Used to call most of the Yellowfin API");
        YFAccessTokenManager adminAccessMgr = new YFAccessTokenManager(config, adminRefreshToken.getToken(), true);

        System.out.println("Access Token: " + truncate(adminAccessMgr.getAccessToken(), 20));
        System.out.println("Expiry: " + adminAccessMgr.getExpiry());
        System.out.println();

        // ─── List Users ──────────────────────────────────────────
        System.out.println("List Users (Name / ID)");
        System.out.println("----------------------");

        YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleUserModel>> usersResult =
                YFUsersApi.getUsers(config, adminAccessMgr.getAccessToken(), null);
        if (!usersResult.isSuccessful()) {
            System.out.println("Failed to list users: " + usersResult.getResponse().getData());
            return;
        }

        YFModelList<IYFSimpleUserModel> usersList = usersResult.getResult();
        for (IYFSimpleUserModel u : usersList.getItems()) {
            System.out.println(u.getName() + " / " + u.getUserId() + " / " + u.getUserConnectionStatus());
        }
        System.out.println();

        long selectedUserId;
        if (usersList.getItems().size() == 1) {
            selectedUserId = usersList.getItems().get(0).getUserId();
        } else {
            System.out.println("Enter UserID to select a user...");
            try {
                selectedUserId = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid UserID");
                return;
            }
        }
        System.out.println();

        // ─── Get User Details ────────────────────────────────────
        YFGenericExecutor.ExecuteResult<IYFUser> userResult =
                YFUsersApi.getUserByID(config, adminAccessMgr.getAccessToken(), selectedUserId);
        if (!userResult.isSuccessful()) {
            System.out.println("Failed to get user: " + userResult.getResponse().getData());
            return;
        }
        IYFUser user = userResult.getResult();

        // ─── Login Token (SSO via Access Token) ──────────────────
        System.out.println("Login Token: (Single Use)");

        List<String> loginParams = Arrays.asList(
                "YFTOOLBAR=FALSE",
                "ENTRY=TIMELINE",
                "DISABLEHEADER=TRUE",
                "DISABLEFOOTER=TRUE",
                "DISABLESIDENAV=TRUE",
                "DISABLELOGOFF=TRUE"
        );

        String ssoUserName = user.getEmail();
        String ssoUserPassword = "";  // Empty for SSO (requires SIMPLE_AUTHENTICATION = TRUE)

        YFTokenResponse loginToken = YFTokenRequests.getLoginToken(
                config, adminAccessMgr.getAccessToken(),
                ssoUserName, ssoUserPassword, yfOrg, loginParams, null);

        System.out.println("Status: " + loginToken.getStatus());

        if (loginToken.isSuccessful()) {
            System.out.println("Login Token: " + loginToken.getToken());
            System.out.println();
            System.out.println("*******************************************************************");
            System.out.println("**** Open this URL in a browser to see how Single Signon Works ****");
            System.out.println("**** The URL will open using the params specified in code      ****");
            System.out.println("****                                                           ****");
            System.out.println(config.getHostURL() + "/logon.i4?LoginWebserviceId=" + loginToken.getToken());
            System.out.println("****                                                           ****");
            System.out.println("*******************************************************************");
            System.out.println();
        } else {
            System.out.println("LoginToken Failed");
            System.out.println("Data: " + loginToken.getData());
            System.out.println();
        }

        // ─── Alternative SSO method (no admin tokens needed) ─────
        System.out.println("Alternative Method to get a SSO token - Use this approach if you don't have other admin calls to make on the API");
        YFTokenResponse ssoToken = YFTokenRequests.getLoginTokenSSO(
                config, config, ssoUserName, ssoUserPassword, yfOrg, loginParams, null);

        System.out.println("SSO Login Status: " + ssoToken.getStatus());
        if (ssoToken.isSuccessful()) {
            System.out.println("SSO Login Token: " + ssoToken.getToken());
            System.out.println(config.getHostURL() + "/logon.i4?LoginWebserviceId=" + ssoToken.getToken());
        } else {
            System.out.println("GetLoginTokenSSO Failed");
            System.out.println("Data: " + ssoToken.getData());
        }
        System.out.println();

        // ─── User-specific Refresh Token ─────────────────────────
        System.out.println("Getting user-specific refresh token...");
        YFTokenResponse userRefreshToken = YFTokenRequests.getUserRefreshTokenNoPassword(
                config, adminAccessMgr.getAccessToken(), user.getEmail());

        if (!userRefreshToken.isSuccessful()) {
            System.out.println("Failed to get User Specific Refresh Token");
            System.out.println("Status: " + userRefreshToken.getStatus());
            System.out.println("Data: " + userRefreshToken.getData());
        } else {
            System.out.println("User Refresh Token: " + truncate(userRefreshToken.getToken(), 20));
            refreshTokenIds.add(userRefreshToken.getTokenId());

            YFTokenResponse userAccessToken = YFTokenRequests.getAccessToken(config, userRefreshToken.getToken());
            String userToken = userAccessToken.getToken();

            // Re-fetch user with the USER's own access token — this returns the full model
            // (firstName, lastName, status, title, timezone, preferences, etc.)
            // The earlier getUserByID with admin token returns a simpler model.
            YFGenericExecutor.ExecuteResult<IYFUser> fullUserResult =
                    YFUsersApi.getUserByID(config, userToken, selectedUserId);
            if (fullUserResult.isSuccessful()) {
                IYFUser fullUser = fullUserResult.getResult();
                System.out.println("User Details:");
                System.out.println("  Name:     " + fullUser.getFirstName() + " " + fullUser.getLastName());
                System.out.println("  UserName: " + fullUser.getUserName());
                System.out.println("  Email:    " + fullUser.getEmail());
                System.out.println("  Role:     " + fullUser.getRoleCode());
                System.out.println("  Language: " + fullUser.getPreferredLanguageCode());
                if (!fullUser.getStatus().isEmpty())
                    System.out.println("  Status:   " + fullUser.getStatus());
                if (!fullUser.getTitle().isEmpty())
                    System.out.println("  Title:    " + fullUser.getTitle());
                if (!fullUser.getTimeZoneCode().isEmpty())
                    System.out.println("  Zone:     " + fullUser.getTimeZoneCode());
                if (fullUser.getUserPreferences() != null) {
                    System.out.println("  Entry Page: " + fullUser.getUserPreferences().getEntryPage());
                    System.out.println("  AllowUsersToConnect: " + fullUser.getUserPreferences().getAllowUsersToConnect());
                    System.out.println("  AllowUsersToPostOnTimeline: " + fullUser.getUserPreferences().getAllowUsersToPostOnTimeline());
                }
                System.out.println();

                // [USERS] There are three ways to get a User, depending on what ID you are using:
                // YFUsersApi.getUserByID(config, token, userId)
                // YFUsersApi.getUserByEmailAddress(config, token, email)        — RPC endpoint
                // YFUsersApi.getUserByUserName(config, token, userName)          — RPC endpoint
                YFGenericExecutor.ExecuteResult<IYFUser> namedResult =
                        YFUsersApi.getUserByUserName(config, userToken, fullUser.getUserName());
                if (namedResult.isSuccessful())
                    System.out.println("GetUserByUserName: " + namedResult.getResult().getFirstName() + " " + namedResult.getResult().getLastName());
                else
                    System.out.println("GetUserByUserName failed: " + namedResult.getResponse().getStatus());
            } else {
                System.out.println("getUserByID failed: " + fullUserResult.getResponse().getStatus());
                System.out.println("Data: " + fullUserResult.getResponse().getData());
            }

            // ─── List Reports ────────────────────────────────────
            System.out.println();
            System.out.println("Reports:");
            YFGenericExecutor.ExecuteResult<YFModelList<IYFReportListItemModel>> reportsResult =
                    YFReportsApi.getReports(config, userToken, null, 0);
            if (reportsResult.isSuccessful()) {
                List<IYFReportListItemModel> reports = reportsResult.getResult().getItems();
                for (IYFReportListItemModel r : reports) {
                    System.out.println("  " + r.getName() + " / " + r.getReportPublishUUID());
                }

                // ─── SSO Direct to First Report ──────────────────
                if (!reports.isEmpty()) {
                    IYFReportListItemModel firstReport = reports.get(0);
                    System.out.println();
                    System.out.println("SSO Direct to Report: " + firstReport.getName());
                    YFTokenResponse reportToken = YFTokenRequests.getLoginTokenSSO(
                            config, config, ssoUserName, "", yfOrg,
                            Arrays.asList(
                                    "ENTRY=VIEWREPORT",
                                    "REPORTUUID=" + firstReport.getReportPublishUUID(),
                                    "DISABLEHEADER=TRUE",
                                    "DISABLESIDENAV=TRUE"
                            ), null);

                    if (reportToken.isSuccessful()) {
                        System.out.println("URL: " + config.getHostURL() + "/logon.i4?LoginWebserviceId=" + reportToken.getToken());
                        System.out.println("(Open this URL in a browser to view the report directly)");
                    } else {
                        System.out.println("Failed: " + reportToken.getStatus());
                    }

                    // ─── Favourite / Unfavourite First Report ────
                    System.out.println();

                    // Check if the report is already favourited
                    boolean alreadyFavourited = false;
                    YFGenericExecutor.ExecuteResult<YFModelList<IYFFavouriteModel>> favsResult =
                            YFUsersApi.getUserFavourites(config, userToken, selectedUserId);
                    if (favsResult.isSuccessful()) {
                        for (IYFFavouriteModel f : favsResult.getResult().getItems()) {
                            if (f.getContentId() == firstReport.getReportId()) {
                                alreadyFavourited = true;
                                break;
                            }
                        }
                    }

                    if (alreadyFavourited) {
                        System.out.println("Report '" + firstReport.getName() + "' is already favourited — unfavouriting...");
                        YFTransportResponse unfavResp =
                                YFUsersApi.unfavouriteAContentItem(config, userToken, selectedUserId, "report", firstReport.getReportId());
                        if (unfavResp.isSuccessful()) {
                            System.out.println("  Unfavourited successfully.");
                        } else {
                            System.out.println("  Failed to unfavourite: " + unfavResp.getStatus());
                        }
                    } else {
                        System.out.println("Report '" + firstReport.getName() + "' is not favourited — favouriting...");
                        YFGenericExecutor.ExecuteResult<IYFFavouriteModel> favResult =
                                YFUsersApi.favouriteAContentItem(config, userToken, selectedUserId, "report", firstReport.getReportId());
                        if (favResult.isSuccessful()) {
                            IYFFavouriteModel fav = favResult.getResult();
                            System.out.println("  Favourited: " + fav.getTitle());
                            System.out.println("  Content Type: " + fav.getContentType());
                            System.out.println("  Content UUID: " + fav.getContentUuid());
                        } else {
                            System.out.println("  Failed to favourite: " + favResult.getResponse().getStatus());
                        }
                    }
                }
            }
        }

        // ─── Cleanup: Delete Refresh Tokens ──────────────────────
        System.out.println();
        System.out.println("Cleanup — Deleting Refresh Tokens:");
        // Delete in reverse order: user tokens first, admin token last
        Collections.reverse(refreshTokenIds);
        for (long tokenId : refreshTokenIds) {
            try {
                boolean deleted = YFTokenRequests.deleteRefreshToken(config, adminAccessMgr.getAccessToken(), tokenId);
                System.out.println("  Token ID " + tokenId + ": " + (deleted ? "deleted" : "failed"));
            } catch (Exception e) {
                System.out.println("  Token ID " + tokenId + ": cleanup error (ignored)");
            }
        }

        System.out.println();
        System.out.println("Done.");
    }

    // ─── Helpers ─────────────────────────────────────────────────

    /** Read a system property override, falling back to the INI/default value. */
    private static String param(String key, String defaultValue) {
        String val = System.getProperty(key);
        return (val != null && !val.isEmpty()) ? val : defaultValue;
    }

    /** Truncate a string for display. */
    private static String truncate(String s, int len) {
        if (s == null) return "(null)";
        return s.length() > len ? s.substring(0, len) + "..." : s;
    }

    /** Decode a Base64 encoded string (for stored passwords). */
    private static String decodeBase64(String encoded) {
        if (encoded == null || encoded.isEmpty()) return "";
        try {
            return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
