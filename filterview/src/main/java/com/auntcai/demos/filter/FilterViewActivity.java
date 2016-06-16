package com.auntcai.demos.filter;

import android.app.Activity;
import android.os.Bundle;

import com.auntcai.demos.filter.finalFilterView.FilterView;
import com.auntcai.demos.filter.finalFilterView.filterProvider.menuStyle.pop.PopController;
import com.auntcai.demos.filter.finalFilterView.filterProvider.menuStyle.pop.PopMenuContainer;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.Car;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.CarController;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.DistanceController;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.SortController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/8/16.
 */
public class FilterViewActivity extends Activity {

    private FilterView mFilterView;
    private List<FilterView.ITabMenuController> mControllers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterview);

        mFilterView = (FilterView) findViewById(R.id.filterview);

        //控件初始化
        PopController popController = new PopController(FilterViewActivity.this);
        popController.setAnchor(mFilterView);
        popController.setMenuDismissListener(new PopMenuContainer.MenuDismissListener() {
            @Override
            public void menuDismiss(int tabIndex) {
                mFilterView.changeCurrentTabStatus(-1, false);
            }
        });
        mFilterView.setFilterController(popController);

        mControllers.add(SortController.createSortController(this, 1));
        mControllers.add(DistanceController.createDistanceController(this));
        mControllers.add(CarController.createCarController(this));

        mFilterView.setTabMenuControllers(mControllers);

        mFilterView.setMenuSelectedListener(new FilterView.MenuSelectedListener() {
            @Override
            public void onMenuSelected(int tabIndex, FilterView.IFilterItem iFilterItem) {

            }
        });
    }
}
