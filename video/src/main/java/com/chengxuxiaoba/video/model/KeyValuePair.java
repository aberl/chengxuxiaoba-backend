package com.chengxuxiaoba.video.model;

import java.util.HashMap;
import java.util.Map;

public class KeyValuePair<K, V> {
    private Map<K, V> mapContainer = new HashMap<K, V>();

    public KeyValuePair(){}

    public KeyValuePair(K key, V value)
    {
        mapContainer.put(key, value);
    }

    public void put(K key, V value) {
        mapContainer = new HashMap<K, V>();

        mapContainer.put(key, value);
    }

    public void clear()
    {
        mapContainer.remove(getKey());
    }

    public K getKey() {
        for (K key : mapContainer.keySet()) {
            return key;
        }
        return null;
    }

    public V getValue() {
        K key = getKey();

        return mapContainer.get(key);
    }
}
