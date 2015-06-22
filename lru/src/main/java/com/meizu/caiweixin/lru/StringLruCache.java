package com.meizu.caiweixin.lru;

import android.util.LruCache;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by caiweixin on 6/13/15.
 */
public class StringLruCache {
    private int size;

    private android.util.LruCache<String, String> cache;

    public StringLruCache(int size) {
        this.size = size;
        this.cache = new LruCache<String, String>(size);
    }

    public void put(String value) {
        if (cache.size() == size && cache.get(value) == null) {
            Iterator iterator = cache.snapshot().keySet().iterator();
            cache.remove((String) iterator.next());
        }
        cache.put(value, value);
    }

    public ArrayList<String> getStringList() {
        ArrayList<String> result = new ArrayList<>();
        Iterator iterator = cache.snapshot().keySet().iterator();
        while (iterator.hasNext()) {
            result.add((String) iterator.next());
        }
        return result;
    }

    public void removeAll(){
        cache.evictAll();
    }
}
