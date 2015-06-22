package com.meizu.caiweixin.lru;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by caiweixin on 6/1/15.
 */
public class SearchManager {
    private static SearchManager mInstance;

    private StringLruCache mLruCache = new StringLruCache(5);

    public static SearchManager getInstance() {
        if (mInstance == null) {
            mInstance = new SearchManager();
        }
        return mInstance;
    }

    public void addSearchHistory(Context context, String keyWord) {
        SharedPreferences preferences = context.getSharedPreferences("search_history", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", keyWord);
        editor.apply();
    }

    public void clearSearchHistory() {

    }

    public ArrayList<String> getHotSearchKeys() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("西餐");
        result.add("KTV");
        result.add("湘菜");
        result.add("马德里");
        result.add("火锅");
        result.add("韩国料理");
        result.add("自助餐");
        result.add("汉堡王");
        return result;
    }

    public void addHistory(String s) {
        mLruCache.put(s);
    }

    public ArrayList<String> getHistory() {
        return mLruCache.getStringList();
    }
}
