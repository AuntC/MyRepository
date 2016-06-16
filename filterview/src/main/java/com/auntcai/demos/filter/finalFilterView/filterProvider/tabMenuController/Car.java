package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import com.auntcai.demos.filter.finalFilterView.FilterView;

import java.util.List;

/**
 * Created by caiweixin on 16/6/16.
 */
public class Car implements FilterView.IFilterItem<Car> {
    private String carText;
    private boolean isSelected;

    public Car(String carText) {
        this.carText = carText;
    }

    @Override
    public String getTabShowText() {
        return carText;
    }

    @Override
    public String getMenuShowText() {
        return carText;
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
    public List<Car> getChildren() {
        return null;
    }

    @Override
    public void setChildren(List<Car> children) {

    }

    @Override
    public void setParentItem(Car parent) {

    }

    @Override
    public Car getParentItem() {
        return null;
    }
}
