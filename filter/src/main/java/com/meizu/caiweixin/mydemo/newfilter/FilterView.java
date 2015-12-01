package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.util.SparseArray;
import android.widget.LinearLayout;

import com.meizu.media.life.R;
import com.meizu.media.life.ui.widget.filter.FilterConstant;
import com.meizu.media.life.ui.widget.filter.bean.FilterBaseBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterDistrictBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterDistrictChildBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterExpandBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterInterface;
import com.meizu.media.life.ui.widget.filter.bean.FilterMenuBean;
import com.meizu.media.life.ui.widget.filter.view.FilterTabView;

import java.util.List;

/**
 * Created by caiweixin on 5/11/15.
 */
public class FilterView extends LinearLayout {

    private Context mContext;

    private int mTabNumber, mCurrentTabIndex;

    private SparseArray mMenuList;
    private SparseArray<BaseMenuView> mMenuViews;

    private MenuSelectedListener mMenuSelectedListener;

    //标记筛选框是否选择之后就关闭，默认关闭；
    private boolean mCloseAfter = true;

    private MenuContainer mMenuContainer;

    public interface MenuSelectedListener {
        public void MenuSelected(int tabIndex, Object object);
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
        resetCurrentTabIndex();
        mTabNumber = menuList.size();
        mMenuViews = new SparseArray();
        mMenuContainer = new MenuContainer(context);

        mMenuContainer.setMenuDismissListener(new MenuContainer.MenuDismissListener() {
            @Override
            public void menuDismiss(int tabIndex) {
                setTabImage(tabIndex);
                resetCurrentTabIndex();
            }
        });

        FilterTabView filterTabView = null;
        int key;
        for (int i = 0; i < mTabNumber; i++) {
            key = menuList.keyAt(i);
            FilterMenuBean filterMenuBean = (FilterMenuBean) menuList.get(key);
            filterTabView = new FilterTabView(context, key);
            filterTabView.setTabOnClickListener(new FilterTabView.TabOnClickListener() {
                @Override
                public void onClick(int tabIndex, boolean isExpand) {
                    if (isExpand) {
                        showMenuPopupWindow(tabIndex);
                    } else {
                        hideMenuPopupWindow(tabIndex, true);
                    }
                }
            });
            addView(filterTabView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
            mMenuViews.append(key, getMenuView(key, filterMenuBean));

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
                case FilterConstant.MenuType.EXPANDABLELIST:
                    filterTabView.setTabText(((FilterExpandBean) filterMenuBean.menuList.get(0)).child.get(0).getText());
                    break;
                case FilterConstant.MenuType.DISTRICT:
                    filterTabView.setTabText(((FilterDistrictBean) filterMenuBean.menuList.get(0)).getFilterDistrictChildBean().get(0).getBusinessCircleName());
                    break;
            }
        }

    }

    public boolean onBackPressed() {
        if (mCurrentTabIndex != -1) {
            if (mMenuContainer.onBackPressed() == MenuContainer.WindowStatus.NormalBack) {
                if (null != mMenuStatusListener) {
                    mMenuStatusListener.hide(mCurrentTabIndex);
                }
            }
            return false;
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
    }

    public String getTabTextByIndex(int tabIndex) {
        FilterTabView filterTabView = (FilterTabView) this.getChildAt(tabIndex);
        return filterTabView.getTabText();
    }

    private void setTabImage(int tabIndex) {
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
        if (mCurrentTabIndex != -1 && mCurrentTabIndex != tabIndex) {
            setTabImage(mCurrentTabIndex);
        }
        mMenuContainer.setShowMenuView(this, tabIndex, mMenuViews.get(tabIndex).getView(), mCurrentTabIndex == -1 ? true : false);
        mCurrentTabIndex = tabIndex;

        if (mMenuStatusListener != null) {
            mMenuStatusListener.show(tabIndex);
        }
    }

    private void hideMenuPopupWindow(int tabIndex, boolean isHideByUser) {
        resetCurrentTabIndex();
        mMenuContainer.dismissWithAnimation(true);

        if (null != mMenuStatusListener && isHideByUser) {
            mMenuStatusListener.hide(tabIndex);
        }
    }

    private void hideMenuPopupWindow(int tabIndex) {
        hideMenuPopupWindow(tabIndex, false);
    }


    private void resetCurrentTabIndex() {
        if (mCurrentTabIndex != -1) {
            mCurrentTabIndex = -1;
        } else {

        }
    }

    private BaseMenuView getMenuView(int tabPosition, FilterMenuBean filterMenuBean) {
        BaseMenuView p = null;
        switch (filterMenuBean.menuType) {
            case FilterConstant.MenuType.GRID:
                p = new GridMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.EXPANDABLELIST:
                p = new ExpandNoPinnedMenu(mContext, tabPosition, filterMenuBean);
                break;
            case FilterConstant.MenuType.DISTRICT:
                p = new DistrictMenu(mContext, tabPosition, filterMenuBean);
                break;
            default:
                break;
        }
        if (null != p) {
            p.setMenuOnItemClickListener(new BaseMenuView.MenuOnItemClickListener() {
                @Override
                public void onFilterMenuItemClick(final int tabIndex, Object object) {
                    if (object instanceof FilterInterface) {
                        setTabTextByIndex(tabIndex, ((FilterInterface) object).getText());
                    } else if (object instanceof FilterDistrictChildBean) {
                        FilterDistrictChildBean childBean = (FilterDistrictChildBean) object;
                        if (mContext.getString(R.string.filter_region_title_child).equals(childBean.getBusinessCircleName())) {
                            setTabTextByIndex(tabIndex, mContext.getString(R.string.filter_region_title_child));
                        } else {
                            setTabTextByIndex(tabIndex, childBean.isAll() ? childBean.getRegionName() : childBean.getBusinessCircleName());
                        }
                    }
                    if (null != mMenuSelectedListener) {
                        mMenuSelectedListener.MenuSelected(tabIndex, object);
                    }
                    if (mCloseAfter) {
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                hideMenuPopupWindow(tabIndex);
                            }
                        });
                    }
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

    public void setTabDefaultPosition(int tabIndex, int tabPosition) {
        if (mMenuViews != null) {
            BaseMenuView menu = mMenuViews.get(tabIndex);
            if (menu != null) {
                menu.setDefaultPosition(tabPosition);
            }
        }
    }

    public boolean isAnimating() {
        return mMenuContainer == null ? false : mMenuContainer.isAnimating();
    }

    /**
     * 待优化，此方法只是重新生成一个新view，不是对原view更新属性；
     *
     * @param key
     * @param filterMenuBean
     */
    public void updateTabView(int key, FilterMenuBean filterMenuBean) {
        mMenuList.put(key, filterMenuBean);
        mMenuViews.put(key, getMenuView(key, filterMenuBean));
    }
}
