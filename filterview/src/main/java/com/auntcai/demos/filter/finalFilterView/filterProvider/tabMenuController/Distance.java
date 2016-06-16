package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import com.auntcai.demos.filter.finalFilterView.FilterView;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/15/16.
 */
public class Distance implements FilterView.IFilterItem<Distance> {
    private String distance;
    private String showText;

    private boolean isSelected;

    public Distance(String distance, String showText) {
        this.distance = distance;
        this.showText = showText;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public String getTabShowText() {
        return showText;
    }

    @Override
    public String getMenuShowText() {
        return showText;
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
    public List<Distance> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<Distance> children) {

    }

    @Override
    public void setParentItem(Distance parent) {

    }

    @Override
    public Distance getParentItem() {
        return null;
    }
}
