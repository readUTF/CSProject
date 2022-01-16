package com.readutf.csprojectapi.tokens;

import java.util.HashMap;

public class QuickMap<T, E> {

    HashMap<T, E> map;

    public QuickMap(Object... items) {
        map = new HashMap<>();
        for (int i = 0; i < items.length; i++) {
            if(i % 2 == 0) map.put((T) items[i], (E) items[i+1]);
        }
    }

    public HashMap<T, E> getMap() {
        return map;
    }
}
