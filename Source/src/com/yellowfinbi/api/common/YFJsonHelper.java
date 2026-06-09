package com.yellowfinbi.api.common;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Safe JSON accessor utilities with default values.
 */
public final class YFJsonHelper {

    private YFJsonHelper() { }

    public static String getString(JSONObject json, String key, String defaultValue) {
        return json.has(key) && !json.isNull(key) ? json.optString(key, defaultValue) : defaultValue;
    }

    public static long getLong(JSONObject json, String key, long defaultValue) {
        return json.has(key) && !json.isNull(key) ? json.optLong(key, defaultValue) : defaultValue;
    }

    public static int getInt(JSONObject json, String key, int defaultValue) {
        return json.has(key) && !json.isNull(key) ? json.optInt(key, defaultValue) : defaultValue;
    }

    public static boolean getBool(JSONObject json, String key, boolean defaultValue) {
        return json.has(key) && !json.isNull(key) ? json.optBoolean(key, defaultValue) : defaultValue;
    }

    public static JSONObject getObject(JSONObject json, String key) {
        return json.has(key) && !json.isNull(key) ? json.optJSONObject(key) : null;
    }

    public static JSONArray getArray(JSONObject json, String key) {
        return json.has(key) && !json.isNull(key) ? json.optJSONArray(key) : null;
    }

    /** Convert a string value to an enum constant, returning defaultValue if not found. */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getEnum(JSONObject json, String key,
                                                 Class<E> enumClass, E defaultValue) {
        String val = getString(json, key, "");
        if (val.isBlank()) return defaultValue;
        try {
            return Enum.valueOf(enumClass, val);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
