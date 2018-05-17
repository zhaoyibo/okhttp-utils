package com.windmt.util;

import java.util.LinkedHashMap;

/**
 * @author: yibo
 **/
public class FluentMap<K, V> extends LinkedHashMap<K, V> {

    public FluentMap() {
    }

    public FluentMap(K key, V value) {
        this.put(key, value);
    }

    public FluentMap<K, V> add(K key, V value) {
        this.put(key, value);
        return this;
    }

    public FluentMap<K, V> delete(K key) {
        this.remove(key);
        return this;
    }

}
