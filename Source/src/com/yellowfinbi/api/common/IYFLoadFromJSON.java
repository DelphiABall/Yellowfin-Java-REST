package com.yellowfinbi.api.common;

import org.json.JSONObject;

/**
 * Interface for objects that can be populated from parsed JSON.
 * Every model in the library implements this interface.
 */
public interface IYFLoadFromJSON {
    void loadFromJSON(JSONObject json);
}
