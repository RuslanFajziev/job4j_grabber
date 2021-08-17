package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            V data = cache.get(key).get();
            if (data != null) {
                return (V) (data + " (<-- Data from the cache)");
            } else {
                data = load(key);
                put(key, data);
                return (V) (data + " (--> Loading data into a cache)");
            }
        }
        V data = load(key);
        if (data == null) {
            return (V) ("***** File not found, verify file name is correct *****");
        }
        put(key, data);
        return (V) (data + " (--> Loading data into a cache)");
    }

    protected abstract V load(K key);

}