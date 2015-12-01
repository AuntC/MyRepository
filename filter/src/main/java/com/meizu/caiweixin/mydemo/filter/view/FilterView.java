package com.meizu.caiweixin.mydemo.filter.view;

import android.content.Context;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.FilterConstant;
import com.meizu.caiweixin.mydemo.filter.bean.FilterBaseBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterCountBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterExpandBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterInterface;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;
import com.meizu.caiweixin.mydemo.filter.menu.BaseMenu;
import com.meizu.caiweixin.mydemo.filter.menu.ExpandMenu;
import com.meizu.caiweixin.mydemo.filter.menu.GridMenu;
import com.meizu.caiweixin.mydemo.filter.menu.ListMenu;

import java.util.List;

/**
 * Created by caiweixin on 5/11/15.
 */
public class FilterView extends LinearLayout {
    //Warring!!! Please don't use this class,it's not working properly.

    private Context mContext;

    private int mTabNumber, mCurrentTabIndex;

    private SparseArray mMenuList;
    private SparseArray<BaseMenu> mMenuViews;

    private MenuSelectedListener mMenuSelectedListener;

    private boolean mCloseAfter = false;

    public interface MenuSelectedListener {
        public void MenuSelected(int tabIndex, FilterInterface filterInterface);
    }

    public FilterView(Context context, SparseArray menuList) {
        super(context);
        initViews(context, menuList);
        mCloseAfter = true;
    }

    public FilterView(Context context, SparseArray menuList, boolean closeAfter) {
        this(context, menuList);
        mCloseAfter = closeAfter;
    }

    private void initViews(Context context, SparseArray menuList) {
        setBackgroundColor(context.getResources().getColor(R.color.filter_background));
        mContext = context;
        mMenuList = menuList;
        mCurrentTabIndex = -1;
        mTabNumber = menuList.size();
        mMenuViews = new SparseArray();

        FilterTabView filterTabView = null;
        int key;
        for (int i = 0; i < mTabNumber; i++) {
            key = menuList.keyAt(i);
            FilterMenuBean filterMenuBean = (FilterMenuBean) menuList.get(key);
            filterTabView = new FilterTabView(context, key);
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
            addView(filterTabView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
            mMenuViews.append(key, getPopupWindow(key, filterMenuBean));

            switch (filterMenuBean.menuType) {
                case FilterConstant.MenuType.GRID:
                    List<FilterBaseBean> filterMenuBeanList = filterMenuBean.menuList;
                    FilterBaseBean bean;
                    for (int j = 0; j < filterMenuBeanList.size(); j++) {
                        bean = filterMenuBeanList.get(j);
                        if (bean != null && bean.isSelected) {
                            filterTabView.setTabText(bean.getText());
                            setTabDefaultPosition(key, j);
                        }
                    }
                    break;
                case FilterConstant.MenuType.LIST:
                    filterTabView.setTabText(((FilterCountBean) filterMenuBean.menuList.get(0)).getText());
                    break;
                case FilterConstant.MenuType.EXPANDABLELIST:
                    filterTabView.setTabText(((FilterExpandBean) filterMenuBean.menuList.get(0)).title.getText());
                    break;
            }
        }

    }

    public boolean onBackPressed() {
//        LogHelper.logE(FilterConstant.TAG, "onBackPressed:" + mCurrentTabIndex);
        if (mCurrentTabIndex != -1) {
//            hideMenuPopupWindow(mCurrentTabIndex);
            if (null != mMenuViews) {
                BaseMenu baseMenu = mMenuViews.get(mCurrentTabIndex);
                if (baseMenu != null) {
                    switch (baseMenu.onBackPressed()) {
                        case CancelDismiss:
                            return true;
                        case NormalBack:
                            return false;
                        case CancelShow:
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void onPause() {
        onBackPressed();
    }

    public void onDestroy() {
        mMenuList = null;
        mMenuViews = null;
    }

    public void setTabTextByIndex(int tabIndex, String text) {
        FilterTabView filterTabView = (FilterTabView) this.getChildAt(tabIndex);
        if (filterTabView != null) {
            filterTabView.setTabText(text);
        }
//        hideMenuPopupWindow(tabIndex);
    }

    private void setTabImage(int tabIndex) {
//        LogHelper.logE(FilterConstant.TAG, "setTabImage -- tabIndex:" + tabIndex);

        FilterTabView filterTabView = (FilterTabView) this.getChildAt(tabIndex);
        if (null != filterTabView && filterTabView.isExpand()) {
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
        if (null != mMenuViews) {
            BaseMenu baseMenu = (BaseMenu) mMenuViews.get(tabIndex);
            if (baseMenu != null) {
                changeCurrentTabIndex(tabIndex);
                baseMenu.showAsDropDown(this);
                if (mMenuStatusListener != null) {
                    mMenuStatusListener.show(tabIndex);
                }
            } else {

            }
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
        if (null != mMenuViews) {
            BaseMenu baseMenu = (BaseMenu) mMenuViews.get(tabIndex);
            if (baseMenu != null) {
                baseMenu.dismissWithAnimation(true);
//                setTabImage(tabIndex);
                if (null != mMenuStatusListener) {
                    mMenuStatusListener.hide(tabIndex);
                }
            } else {

            }
//            resetCurrentTabIndex();
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
            BaseMenu menu = mMenuViews.get(mCurrentTabIndex);
            if (menu != null) {
                if (menu.getCurrentWindowStatus() == BaseMenu.WindowStatus.CancelDismiss) {
                    menu.stopDismissAnimation();
                }
                menu.dismiss();
                setTabImage(mCurrentTabIndex);
            }
            mCurrentTabIndex = tabIndex;
        } else {
//            hideMenuPopupWindow(tabIndex);
        }
    }

    private BaseMenu getPopupWindow(int tabPosition, FilterMenuBean filterMenuBean) {
        BaseMenu p = null;
        switch (filterMenuBean.menuType) {
            case FilterConstant.MenuType.GRID:
                p = new GridMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.LIST:
                p = new ListMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.EXPANDABLELIST:
                p = new ExpandMenu(mContext, tabPosition, filterMenuBean);
                break;
            default:
                break;
        }
        if (null != p) {
            p.setMenuOnItemClickListener(new BaseMenu.MenuOnItemClickListener() {
                @Override
                public void onFilterMenuItemClick(int tabIndex, FilterInterface filter) {
                    if (null != mMenuSelectedListener) {
                        mMenuSelectedListener.MenuSelected(tabIndex, filter);
                    }
                    setTabTextByIndex(tabIndex, filter.getText());
                    if (mCloseAfter) {
                        hideMenuPopupWindow(tabIndex);
                    }
                }
            });
            p.setMenuDismissListener(new BaseMenu.MenuDismissListener() {
                @Override
                public void menuDismiss(int tabIndex) {
                    setTabImage(tabIndex);
                    resetCurrentTabIndex();
                }
            });
        }
        return p;
    }

    public interface MenuStatusListener {
        public void show(int tabIndex);

        public void hide(int tabIndex);
    }

    private MenuStatusListener mMenuStatusListener;

    public void setMenuStatusListener(MenuStatusListener menuStatusListener) {
        this.mMenuStatusListener = menuStatusListener;
    }

    public void setMenuSelectedListener(MenuSelectedListener menuSelectedListener) {
        this.mMenuSelectedListener = menuSelectedListener;
    }


    public void setMenuOnItemClickListener(int tabIndex, BaseMenu.MenuOnItemClickListener listener) {
        if (mMenuViews != null) {
            BaseMenu menu = mMenuViews.get(tabIndex);
            if (menu != null) {
                menu.setMenuOnItemClickListener(listener);
            }
        }
    }

    public void updateMenuPopupWindow(int tabIndex, FilterMenuBean<FilterBaseBean> filterMenuBean) {
        if (mMenuViews != null) {
            BaseMenu menu = mMenuViews.get(tabIndex);
            if (menu != null) {
                menu.updateMenuAdapter(filterMenuBean);
            }
        }
    }


    public void setTabDefaultPosition(int tabIndex, int tabPosition) {
        if (mMenuViews != null) {
            BaseMenu menu = mMenuViews.get(tabIndex);
            if (menu != null) {
                menu.setDefaultPosition(tabPosition);
            }
        }
    }
}
