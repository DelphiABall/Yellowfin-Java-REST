package com.yellowfinbi.api.sections.users;
import com.yellowfinbi.api.common.*;
import com.yellowfinbi.api.transport.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

public final class YFUsersApi {
    private YFUsersApi(){}

    /** Encode a path segment preserving @, -, _, . (matches Delphi TNetEncoding.URL.EncodePath). */
    private static String encodePathSegment(String value) {
        // URLEncoder encodes for query strings (@ → %40, space → +). For path segments,
        // we need to preserve characters that are valid in URI paths.
        try {
            // Use URI constructor which correctly encodes path segments
            return new URI(null, null, "/" + value, null).getRawPath().substring(1);
        } catch (Exception e) {
            return URLEncoder.encode(value, StandardCharsets.UTF_8);
        }
    }

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    // ── User(s) Management ──────────────────────────────────────

    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleUserModel>> getUsers(
            IYFServerDetails s, String t, String filterJson) {
        String url = "api/users";
        if (filterJson != null && !filterJson.isBlank())
            url += "?filters=" + URLEncoder.encode(filterJson, StandardCharsets.UTF_8);
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFSimpleUserModel.class), s, t, YFTransport.HttpVerb.GET, url, null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUser> getUserByID(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeTyped(IYFUser.class, s, t, YFTransport.HttpVerb.GET, "api/users/" + userId, null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUser> getUserByUserName(IYFServerDetails s, String t, String userName) {
        return YFGenericExecutor.executeTyped(IYFUser.class, s, t, YFTransport.HttpVerb.GET,
                "api/rpc/users/user-details-by-username/" + encodePathSegment(userName), null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUser> getUserByEmailAddress(IYFServerDetails s, String t, String email) {
        if (email == null || !EMAIL_REGEX.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email address");
        return YFGenericExecutor.executeTyped(IYFUser.class, s, t, YFTransport.HttpVerb.GET,
                "api/rpc/users/user-details-by-email/" + encodePathSegment(email), null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUserPatch> updateUser(IYFServerDetails s, String t, long userId, IYFUserPatch patch) {
        return YFGenericExecutor.executeTyped(IYFUserPatch.class, s, t, YFTransport.HttpVerb.PATCH,
                "api/users/" + userId + "/", patch.toJSON());
    }

    public static YFTransportResponse updateUserName(IYFServerDetails s, String t, long userId, String newUserName) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.PUT,
                "api/admin/users/" + userId + "/username", new JSONObject().put("userName", newUserName).toString());
    }

    public static YFTransportResponse updateUserPassword(IYFServerDetails s, String t, long userId, String newPassword) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.PUT,
                "api/admin/users/" + userId + "/password", new JSONObject().put("password", newPassword).toString());
    }

    // ── User Access ─────────────────────────────────────────────

    public static YFTransportResponse createUsers(IYFServerDetails s, String t, List<IYFNewUser> newUsers) {
        JSONArray arr = new JSONArray();
        for (IYFNewUser u : newUsers) arr.put(new JSONObject(u.toJSON()));
        YFTransportResponse resp = YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.POST, "api/admin/users", arr.toString());
        if (resp.isSuccessful()) {
            try {
                JSONObject data = new JSONObject(resp.getData());
                JSONArray items = data.optJSONArray("items");
                if (items != null) {
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String email = item.optString("email", "");
                        if (email.isEmpty()) continue;
                        for (IYFNewUser nu : newUsers) {
                            if (email.equals(nu.getEmail())) {
                                nu.loadFromJSON(item);
                                nu.setUserId(item.optLong("userId", 0));
                                break;
                            }
                        }
                    }
                }
            } catch (Exception ignored) { /* best-effort update */ }
        }
        return resp;
    }

    public static YFTransportResponse requestNewPasswordForUser(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.POST, "api/rpc/admin/users/" + userId + "/reset-password", null);
    }

    public static YFTransportResponse deleteUserFromAllOrgs(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.DELETE, "api/admin/users/" + userId);
    }

    public static YFTransportResponse addUserLoginToSpecificOrg(IYFServerDetails s, String t, long userId, long orgId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.POST,
                "api/admin/users/" + userId + "/org-access", new JSONObject().put("orgId", orgId).toString());
    }

    public static YFTransportResponse deleteUserFromSpecificOrg(IYFServerDetails s, String t, long userId, long orgId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.DELETE, "api/admin/users/" + userId + "/org-access/" + orgId);
    }

    public static YFTransportResponse terminateHTTPSessions(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.DELETE, "api/admin/users/" + userId + "/sessions");
    }

    // ── Following ───────────────────────────────────────────────

    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleUserModel>> getFollowers(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFSimpleUserModel.class),
                s, t, YFTransport.HttpVerb.GET, "api/users/" + userId + "/followers", null);
    }

    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFSimpleUserModel>> getFollowees(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFSimpleUserModel.class),
                s, t, YFTransport.HttpVerb.GET, "api/users/" + userId + "/followees", null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUserFollowRequest> submitFollowRequest(IYFServerDetails s, String t, long userId, long targetUserId) {
        return YFGenericExecutor.executeTyped(IYFUserFollowRequest.class, s, t, YFTransport.HttpVerb.GET,
                "api/users/" + userId + "/followees/" + targetUserId, null);
    }

    public static YFTransportResponse unfollowUser(IYFServerDetails s, String t, long userId, long targetUserId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.DELETE,
                "api/users/" + userId + "/followees/" + targetUserId);
    }

    public static YFGenericExecutor.ExecuteResult<IYFUserFollowRequest> confirmFollowRequest(IYFServerDetails s, String t, long userId, long followerId) {
        return YFGenericExecutor.executeTyped(IYFUserFollowRequest.class, s, t, YFTransport.HttpVerb.PUT,
                "api/users/" + userId + "/followees/" + followerId, null);
    }

    // ── Favourites ──────────────────────────────────────────────

    public static YFGenericExecutor.ExecuteResult<YFModelList<IYFFavouriteModel>> getUserFavourites(IYFServerDetails s, String t, long userId) {
        return YFGenericExecutor.executeTyped(() -> new YFModelList<>(IYFFavouriteModel.class),
                s, t, YFTransport.HttpVerb.GET, "api/users/" + userId + "/favourites", null);
    }

    public static YFGenericExecutor.ExecuteResult<IYFFavouriteModel> favouriteAContentItem(
            IYFServerDetails s, String t, long userId, String contentTypePath, long contentId) {
        return YFGenericExecutor.executeTyped(IYFFavouriteModel.class, s, t, YFTransport.HttpVerb.POST,
                "api/users/" + userId + "/favourites/" + contentTypePath + "/" + contentId, null);
    }

    public static YFTransportResponse unfavouriteAContentItem(
            IYFServerDetails s, String t, long userId, String contentTypePath, long contentId) {
        return YFGenericExecutor.executeRaw(s, t, YFTransport.HttpVerb.DELETE,
                "api/users/" + userId + "/favourites/" + contentTypePath + "/" + contentId);
    }
}
