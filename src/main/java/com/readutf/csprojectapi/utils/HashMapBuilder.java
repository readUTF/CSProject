package com.readutf.csprojectapi.utils;

import java.util.HashMap;

public class HashMapBuilder<T, E> {

    HashMap<T, E> map;

    public HashMapBuilder() {
        map = new HashMap<>();
    }

    public HashMapBuilder(T key, E value) {
        map = new HashMap<>();
        map.put(key, value);
    }



    public HashMapBuilder<T,E> add(T key, E value) {
        map.put(key, value);
        return this;
    }

    public HashMap<T, E> build() {
        return map;
    }
}
