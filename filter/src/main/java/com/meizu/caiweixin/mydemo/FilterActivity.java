package com.meizu.caiweixin.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.meizu.caiweixin.mydemo.filter.FilterConstant;
import com.meizu.caiweixin.mydemo.filter.bean.FilterBaseBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterCountBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterExpandBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;
import com.meizu.caiweixin.mydemo.filter.view.FilterView;

import java.util.ArrayList;


public class FilterActivity extends Activity {

    private FrameLayout mFrameLayout;

    private FilterView mFilterView;

    private SparseArray mMenuList;

    private int[] random = {6, 8, 7, 9, 3, 7, 5, 1, 4, 3, 5, 2, 8,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.container);

        mMenuList = new SparseArray();
        mMenuList.append(0, new FilterMenuBean<FilterCountBean>(FilterConstant.MenuType.LIST, createListData()));
        mMenuList.append(1, new FilterMenuBean<FilterBaseBean>(FilterConstant.MenuType.GRID, createGridData(30)));
        mMenuList.append(2, new FilterMenuBean<FilterExpandBean>(FilterConstant.MenuType.EXPANDABLELIST, createExpandBean(10)));

        mFilterView = new FilterView(FilterActivity.this, mMenuList);

        mFilterView.setMenuSelectedListener(new FilterView.MenuSelectedListener() {
            @Override
            public void MenuSelected(int tabIndex, String id, String name) {
                clickEvent(tabIndex, id, name);
            }
        });

        mFrameLayout.addView(mFilterView);
    }

    public ArrayList<FilterExpandBean> createExpandBean(int size) {
        ArrayList<FilterExpandBean> beans = new ArrayList<FilterExpandBean>();
        FilterExpandBean bean;
        FilterBaseBean title;
        for (int i = 0; i < size; i++) {
            bean = new FilterExpandBean();
            title = new FilterBaseBean();
            title.text = "title" + i;
            bean.title = title;
            bean.child = createGridData(random[i]);
            beans.add(bean);
        }
        return beans;
    }

    public ArrayList<FilterBaseBean> createGridData(int size) {
        ArrayList<FilterBaseBean> beans = new ArrayList<FilterBaseBean>();

        FilterBaseBean bean;
        for (int i = 0; i < size; i++) {
            bean = new FilterBaseBean();
            bean.text = "选择" + i;
            bean.isSelected = false;
            beans.add(bean);
        }

        beans.get(0).isSelected = true;

        return beans;
    }

    public ArrayList<FilterCountBean> createListData() {
        ArrayList<FilterCountBean> beans = new ArrayList<FilterCountBean>();

        FilterCountBean bean;
        for (int i = 0; i < 10; i++) {
            bean = new FilterCountBean();
            bean.text = "选择" + i;
            bean.count = 10 + i;
            bean.isSelected = false;
            beans.add(bean);
        }

        beans.get(0).isSelected = true;

        return beans;
    }

    @Override
    public void onBackPressed() {
        if (mFilterView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void clickEvent(int tabIndex, String id, String name) {
//        LogHelper.logE(FilterConstant.TAG, "tabIndex:" + tabIndex + " id:" + id + " text:" + text);
    }

    Handler handler = new Handler();

    public Handler getHandler() {
        return handler;
    }
}
