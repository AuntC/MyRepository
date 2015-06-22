package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.FilterConstant;
import com.meizu.caiweixin.mydemo.filter.bean.FilterBaseBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/12/15.
 */
public class FilterGridMenu extends FilterBaseMenu {
    private GridView mGridView;
    private GridAdapter mAdapter;

    private int mCurrentPosition;

    public FilterGridMenu(Context context, int tabPosition, FilterMenuBean filterMenuBean) {
        super(context, tabPosition, filterMenuBean);
    }

    private void setCurrentPosition(int position) {
        if (mCurrentPosition != position && null != mFilterMenuBean.menuList) {
            ((FilterBaseBean) mFilterMenuBean.menuList.get(mCurrentPosition)).isSelected = false;
            mCurrentPosition = position;

            FilterBaseBean bean = (FilterBaseBean) mFilterMenuBean.menuList.get(mCurrentPosition);
            bean.isSelected = true;
            if (null != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }
            if (null != mMenuOnItemClickListener) {
                mMenuOnItemClickListener.onItemClick(mTabIndex, bean.id, bean.text);
            }
        }
    }

    @Override
    protected View initContentViewView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_gridview, null);
        mGridView = (GridView) view.findViewById(R.id.filter_menu_gridview);
        mAdapter = new GridAdapter(mContext, mFilterMenuBean.menuList);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(FilterConstant.MenuColumns.GRID);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCurrentPosition(position);
            }
        });
        return view;
    }
}
