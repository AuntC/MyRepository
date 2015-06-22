package com.meizu.caiweixin.mydemo.filter.view;

import android.content.Context;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.meizu.caiweixin.mydemo.filter.FilterConstant;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;
import com.meizu.caiweixin.mydemo.filter.menu.FilterBaseMenu;
import com.meizu.caiweixin.mydemo.filter.menu.FilterExpandMenu;
import com.meizu.caiweixin.mydemo.filter.menu.FilterGridMenu;
import com.meizu.caiweixin.mydemo.filter.menu.FilterListMenu;

/**
 * Created by caiweixin on 5/11/15.
 */
public class FilterView extends LinearLayout {

    private Context mContext;

    private int mTabNumber, mCurrentTabIndex;

    private SparseArray mMenuList, mMenuViews;

    private MenuSelectedListener mMenuSelectedListener;

    public interface MenuSelectedListener {
        public void MenuSelected(int tabIndex, String id, String name);
    }

    public FilterView(Context context, SparseArray menuList) {
        super(context);
        initViews(context, menuList);
    }

    private void initViews(Context context, SparseArray menuList) {
        mContext = context;
        mMenuList = menuList;
        mCurrentTabIndex = -1;
        mTabNumber = menuList.size();
        mMenuViews = new SparseArray();

        FilterTabView filterTabView = null;
        for (int i = 0; i < mTabNumber; i++) {
            filterTabView = new FilterTabView(context, i);
            filterTabView.setTabOnClickListener(new FilterTabView.TabOnClickListener() {
                @Override
                public void onClick(int tabIndex, boolean isExpand) {
//                    LogHelper.logE(FilterConstant.TAG, "tabIndex:" + tabIndex + " isExpand:" + isExpand);
                    if (isExpand) {
                        showMenuPopupWindow(tabIndex);
                    } else {
                        hideMenuPopupWindow(tabIndex);
                    }
                }
            });
            addView(filterTabView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            mMenuViews.append(i, getPopupWindow(i, (FilterMenuBean) menuList.get(i)));
        }

    }

    public boolean onBackPressed() {
//        LogHelper.logE(FilterConstant.TAG, "onBackPressed:" + mCurrentTabIndex);
        if (mCurrentTabIndex != -1) {
            hideMenuPopupWindow(mCurrentTabIndex);
            return false;
        } else {
            return true;
        }
    }

    public void onDestroy() {
        mMenuList = null;
        mMenuViews = null;
    }

    private void setTabTextByIndex(int tabIndex, String text) {
        FilterTabView filterTabView = (FilterTabView) this.getChildAt(tabIndex);
        filterTabView.setTabText(text);
        hideMenuPopupWindow(tabIndex);
    }

    private void setTabImage(int tabIndex) {
//        LogHelper.logE(FilterConstant.TAG, "setTabImage -- tabIndex:" + tabIndex);
        FilterTabView filterTabView = (FilterTabView) this.getChildAt(tabIndex);
        if (filterTabView.isExpand()) {
            filterTabView.setTabExpandImg(false);
        }
    }

    /**
     * 显示菜单
     *
     * @param tabIndex
     */
    private void showMenuPopupWindow(int tabIndex) {
//        LogHelper.logE(FilterConstant.TAG, "showMenuPopupWindow -- tabIndex:" + tabIndex);
        if (null != mMenuViews && mMenuViews.size() > tabIndex && tabIndex > -1) {
            FilterBaseMenu filterBaseMenu = (FilterBaseMenu) mMenuViews.get(tabIndex);
            filterBaseMenu.showAsDropDown(this);
            changeCurrentTabIndex(tabIndex);
        } else {

        }
    }

    /**
     * 隐藏菜单
     *
     * @param tabIndex
     */
    private void hideMenuPopupWindow(int tabIndex) {
//        LogHelper.logE(FilterConstant.TAG, "hideMenuPopupWindow -- tabIndex:" + tabIndex);
        if (null != mMenuViews && mMenuViews.size() > tabIndex && tabIndex > -1) {
            ((FilterBaseMenu) mMenuViews.get(tabIndex)).dismiss();
            setTabImage(tabIndex);
            resetCurrentTabIndex();
        } else {

        }
    }

    private void resetCurrentTabIndex() {
//        LogHelper.logE(FilterConstant.TAG, "resetCurrentTabIndex -- mCurrentTabIndex:" + mCurrentTabIndex);
        if (mCurrentTabIndex != -1) {
            mCurrentTabIndex = -1;
        } else {

        }
    }

    private void changeCurrentTabIndex(int tabIndex) {
//        LogHelper.logE(FilterConstant.TAG, "changeCurrentTabIndex -- mCurrentTabIndex:" + mCurrentTabIndex + " tabIndex:" + tabIndex);
        if (mCurrentTabIndex != tabIndex) {
            if (mCurrentTabIndex != -1) {
                ((FilterBaseMenu) mMenuViews.get(mCurrentTabIndex)).dismiss();
                setTabImage(mCurrentTabIndex);
            }
            mCurrentTabIndex = tabIndex;
        } else {
//            hideMenuPopupWindow(tabIndex);
        }
    }

    private FilterBaseMenu getPopupWindow(int tabPosition, FilterMenuBean filterMenuBean) {
        FilterBaseMenu p = null;
        switch (filterMenuBean.menuType) {
            case FilterConstant.MenuType.GRID:
                p = new FilterGridMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.LIST:
                p = new FilterListMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.EXPANDABLELIST:
                p = new FilterExpandMenu(mContext, tabPosition, filterMenuBean);
                break;
            default:
                break;
        }
        if (null != p) {
            p.setMenuOnItemClickListener(new FilterBaseMenu.MenuOnItemClickListener() {
                @Override
                public void onItemClick(int tabIndex, String id, String name) {
                    if (null != mMenuSelectedListener) {
                        mMenuSelectedListener.MenuSelected(tabIndex, id, name);
                    }
                    setTabTextByIndex(tabIndex, name);
                }
            });
        }
        return p;
    }

    public void setMenuSelectedListener(MenuSelectedListener menuSelectedListener) {
        this.mMenuSelectedListener = menuSelectedListener;
    }
}
