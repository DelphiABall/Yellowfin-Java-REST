package com.yellowfinbi.api.common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Generic model list that deserialises a JSON object containing an items array.
 * Mirrors YFModelList&lt;T&gt; from C# and TYFModelList&lt;I&gt; from Delphi.
 *
 * @param <T> The model type — must implement IYFLoadFromJSON.
 */
public class YFModelList<T extends IYFLoadFromJSON> implements IYFLoadFromJSON {

    private final List<T> items = new ArrayList<>();
    private final String itemsArrayName;
    private final Supplier<T> factory;

    public YFModelList(Class<T> type) {
        this(type, "items");
    }

    public YFModelList(Class<T> type, String itemsArrayName) {
        this(() -> YFFactoryRegistry.createNew(type), itemsArrayName);
    }

    public YFModelList(Supplier<T> factory, String itemsArrayName) {
        this.factory = factory;
        this.itemsArrayName = itemsArrayName;
    }

    public List<T> getItems() { return Collections.unmodifiableList(items); }
    public T get(int index) { return items.get(index); }
    public int getCount() { return items.size(); }

    @Override
    public void loadFromJSON(JSONObject json) {
        items.clear();
        JSONArray arr = YFJsonHelper.getArray(json, itemsArrayName);
        if (arr != null) {
            loadFromJSONArray(arr);
        }
    }

    public void loadFromJSONArray(JSONArray jsonArray) {
        items.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.optJSONObject(i);
            if (element != null) {
                T item = factory.get();
                item.loadFromJSON(element);
                items.add(item);
            }
        }
    }
}
