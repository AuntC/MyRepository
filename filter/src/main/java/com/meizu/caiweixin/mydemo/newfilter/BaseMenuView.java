package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.view.View;

import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;


/**
 * Created by caiweixin on 5/14/15.
 */
public abstract class BaseMenuView {
    protected Context mContext;
    protected int mTabIndex;
    protected FilterMenuBean mFilterMenuBean;
    protected MenuOnItemClickListener mMenuOnItemClickListener;

    public int mCurrentPosition;

    protected View mView;

    public interface MenuOnItemClickListener {
        public void onFilterMenuItemClick(int tabIndex, Object object);
    }

    public BaseMenuView(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        initArgs(context, tabIndex, filterMenuBean);
        initContentView();
    }

    private void initArgs(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        mContext = context;
        mTabIndex = tabIndex;
        mFilterMenuBean = filterMenuBean;
    }

    protected abstract void initContentView();

    public View getView() {
        return mView;
    }

    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        this.mMenuOnItemClickListener = menuOnItemClickListener;
    }

    public void setDefaultPosition(int position) {
        mCurrentPosition = position;
    }
}
