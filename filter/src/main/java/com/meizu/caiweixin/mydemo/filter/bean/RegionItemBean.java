package com.meizu.caiweixin.mydemo.filter.bean;

public class RegionItemBean extends FilterBaseBean {

    public RegionItemBean() {
    }

    public RegionItemBean(String title) {
        text = title;
    }

    private boolean mAll;

    private FilterExpandBean parent;

    public boolean isAll() {
        return mAll;
    }

    public void setAll(boolean mAll) {
        this.mAll = mAll;
    }

    public void setParent(FilterExpandBean parent) {
        this.parent = parent;
    }

    public FilterExpandBean getParent() {
        return parent;
    }
}
