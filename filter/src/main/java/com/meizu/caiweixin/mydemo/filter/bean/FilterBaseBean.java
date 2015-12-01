package com.meizu.caiweixin.mydemo.filter.bean;

/**
 * Created by caiweixin on 5/11/15.
 */
public class FilterBaseBean implements FilterInterface {
    public String id;

    public String text;

    public boolean isSelected;

    public boolean isAllTitle;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "FilterBaseBean[id:" + id + " text:" + text + "]";
    }
}