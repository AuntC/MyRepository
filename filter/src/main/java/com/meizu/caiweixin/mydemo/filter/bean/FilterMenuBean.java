package com.meizu.caiweixin.mydemo.filter.bean;

import java.util.List;

/**
 * Created by caiweixin on 5/14/15.
 */
public class FilterMenuBean<T> {
    public FilterMenuBean(int type, List<T> list) {
        this.menuType = type;
        this.menuList = list;
    }

    public int menuType;

    public List<T> menuList;

    public int getMenuSize() {
        return menuList == null ? 0 : menuList.size();
    }
}
