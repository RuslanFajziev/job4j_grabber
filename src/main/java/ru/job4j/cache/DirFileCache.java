package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    public String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path, Charset.forName("WINDOWS-1251")))) {
            br.lines().forEach(builder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected String load(String key) {
        String data = get(key);
        if (data == null) {
            String value = readFile(cachingDir + key);
            if (!value.isEmpty()) {
                put(key, value);
                return value;
            }
            return "File not found!";
        }
        System.out.println("-------------------------------------------------");
        System.out.println("object already in the cache :)");
        System.out.println("-------------------------------------------------");
        return data;
    }
}