package com.meizu.caiweixin.mydemo.filter.bean;

/**
 * Created by caiweixin on 5/11/15.
 */
public class FilterBaseBean implements FilterInterface{
    public String id;

    public String text;

    public boolean isSelected;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }
}