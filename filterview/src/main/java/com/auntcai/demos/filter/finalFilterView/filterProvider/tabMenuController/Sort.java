package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import com.auntcai.demos.filter.finalFilterView.FilterView;

import java.util.List;

/**
 * Description:
 *
 * @author chengsiyu
 * @since 16/6/6.
 */
public class Sort implements FilterView.IFilterItem<Sort> {
    private int type;
    private String name;
    private boolean isSelected;

    public interface SortType {
        //展示顺序为：1离我最近、4评分最高、2人均最低、3人均最高
        int LEAST_DISTANCE = 1;
        int LOWEST_PRICE = 2;
        int HIGHEST_PRICE = 3;
        int HIGHEST_SCORE = 4;
    }

    public Sort(int id, String name) {
        this.type = id;
        this.name = name;
    }

    @Override
    public String getTabShowText() {
        return name;
    }

    @Override
    public String getMenuShowText() {
        return name;
    }

    @Override
    public void setIsAll(boolean isAll) {

    }

    @Override
    public boolean isAll() {
        return false;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean hasChildList() {
        return false;
    }

    @Override
    public void setLoaded(boolean isLoaded) {

    }

    @Override
    public boolean isLoaded() {
        return false;
    }

    @Override
    public List<Sort> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<Sort> children) {

    }

    @Override
    public void setParentItem(Sort parent) {

    }

    @Override
    public Sort getParentItem() {
        return null;
    }

    public int getType() {
        return type;
    }
}
