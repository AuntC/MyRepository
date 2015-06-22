package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.FilterConstant;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/14/15.
 */
public abstract class FilterBaseMenu extends PopupWindow {
    protected Context mContext;
    protected int mTabIndex, mMenuType;
    protected FilterMenuBean mFilterMenuBean;
    protected MenuOnItemClickListener mMenuOnItemClickListener;

    public interface MenuOnItemClickListener {
        public void onItemClick(int tabIndex, String id, String name);
    }

    public FilterBaseMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context);
        initArgs(context, tabIndex, filterMenuBean);
    }

    private void initArgs(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        mContext = context;
        mTabIndex = tabIndex;
        mMenuType = filterMenuBean.menuType;
        mFilterMenuBean = filterMenuBean;

        setPopupWindowStyle(context, initContentViewView(), isOverHeight(filterMenuBean.menuType, filterMenuBean.getMenuSize()));
    }

    protected abstract View initContentViewView();

    private void setPopupWindowStyle(Context context, View contentView, boolean isOverHeight) {
        setContentView(contentView);

        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体的高
        if (isOverHeight) {
            setHeight(context.getResources().getDimensionPixelOffset(R.dimen.filter_gridview_max_height));
        } else {
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        setFocusable(false);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.popupAnimation);

        setOutsideTouchable(false);

        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.pop_back)));

        //当PopWindow弹出后，菜单键再次按下事件被PopWindow获得，在这里判断
        contentView.setFocusableInTouchMode(false);
    }

    private boolean isOverHeight(int menuType, int size) {
        switch (menuType) {
            case FilterConstant.MenuType.GRID:
                int lines = size / FilterConstant.MenuColumns.GRID;
                if (size - lines * FilterConstant.MenuColumns.GRID > 0) {
                    lines += 1;
                }
                if (lines * FilterConstant.Height.GRIDVIEW_ITEM > FilterConstant.Height.POPUP_WINDOW) {
                    return true;
                } else {
                    return false;
                }
            case FilterConstant.MenuType.LIST:
                if (size * FilterConstant.Height.LISTVIEW_ITEM > FilterConstant.Height.POPUP_WINDOW) {
                    return true;
                } else {
                    return false;
                }
            case FilterConstant.MenuType.EXPANDABLELIST:
                if (size * FilterConstant.Height.EXPANDVIEW_GROUP > FilterConstant.Height.POPUP_WINDOW) {

                }
                return true;
        }
        return false;
    }

    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        this.mMenuOnItemClickListener = menuOnItemClickListener;
    }
}
