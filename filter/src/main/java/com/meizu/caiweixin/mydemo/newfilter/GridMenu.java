package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.meizu.media.life.R;
import com.meizu.media.life.ui.widget.filter.FilterConstant;
import com.meizu.media.life.ui.widget.filter.bean.FilterBaseBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterMenuBean;
import com.meizu.media.life.ui.widget.filter.menu.GridAdapter;

/**
 * Created by caiweixin on 5/12/15.
 */
public class GridMenu extends BaseMenuView {
    public GridView mGridView;
    public GridAdapter mAdapter;

    public GridMenu(Context context, int tabPosition, FilterMenuBean filterMenuBean) {
        super(context, tabPosition, filterMenuBean);
    }

    public GridMenu(Context context, int tabPosition, FilterMenuBean filterMenuBean, int defaultPosition) {
        this(context, tabPosition, filterMenuBean);
        mCurrentPosition = defaultPosition;
    }

    public void setCurrentPosition(int position) {
        if (mCurrentPosition != position && null != mFilterMenuBean.menuList) {
            ((FilterBaseBean) mFilterMenuBean.menuList.get(mCurrentPosition)).isSelected = false;
            mCurrentPosition = position;
            FilterBaseBean beanFilter = (FilterBaseBean) mFilterMenuBean.menuList.get(mCurrentPosition);
            beanFilter.isSelected = true;
            if (null != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }
            if (null != mMenuOnItemClickListener) {
                mMenuOnItemClickListener.onFilterMenuItemClick(mTabIndex, beanFilter);
            }

        }
    }

    @Override
    protected void initContentView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_gridview, null);
        mGridView = (GridView) mView.findViewById(R.id.filter_menu_gridview);
        mAdapter = new GridAdapter(mContext, mFilterMenuBean.menuList);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(FilterConstant.MenuColumns.GRID);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCurrentPosition(position);
            }
        });
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }
}
