package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public static void printConsole(String txt) {
        System.out.println("-------------------------------------------------");
        System.out.println(txt);
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            V data = cache.get(key).get();
            if (data != null) {
                printConsole("                (<-- Data from the cache)");
                return data;
            } else {
                data = load(key);
                put(key, data);
                printConsole("                (--> Loading data into a cache)");
                return data;
            }
        }
        V data = load(key);
        if (data == null) {
            printConsole("***** File not found, verify file name is correct *****");
            return data;
        }
        printConsole("                (--> Loading data into a cache)");
        put(key, data);
        return data;
    }

    protected abstract V load(K key);

}