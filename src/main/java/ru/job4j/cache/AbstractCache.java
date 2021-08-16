package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public static void printConsole(String txt) {
        System.out.println("-------------------------------------------------");
        System.out.println(txt);
        System.out.println("-------------------------------------------------");
    }

    public void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            SoftReference<V> softReference = cache.get(key);
            if (softReference != null) {
                printConsole("          *** Data from the cache ***");
                return softReference.get();
            } else {
                printConsole("          *** Loading data into a cache ***");
                V txt = load(key);
                put(key, txt);
                return txt;
            }
        }
        printConsole("          *** Loading data into a cache ***");
        V txt = load(key);
        put(key, txt);
        return txt;
    }

    protected abstract V load(K key);

}