package com.yellowfinbi.api.common;

import org.json.JSONObject;

/**
 * Bidirectional JSON interface — supports both loading and serialising.
 */
public interface IYFJSON extends IYFLoadFromJSON {
    String toJSON();
    JSONObject asJSON();
}
