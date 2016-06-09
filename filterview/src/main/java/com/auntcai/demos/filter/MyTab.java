package com.auntcai.demos.filter;

import android.content.Context;
import android.view.View;

import com.auntcai.demos.filter.filterview.menuViews.IMenu;
import com.auntcai.demos.filter.filterview.tab.ITab;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class MyTab implements ITab, IMenu {
    private Context mContext;
    private int mIndex;
    private int mStatus;
    private IMenu iMenu;

    public MyTab(Context context, int index) {
        this.mContext = context;
        this.mIndex = index;
    }

    @Override
    public int getIndex() {
        return mIndex;
    }

    @Override
    public CharSequence getShowText() {
        return String.valueOf(mIndex);
    }

    @Override
    public void setStatus(int status) {
        this.mStatus = status;
    }

    @Override
    public int getStatus() {
        return mStatus;
    }

    @Override
    public View getMenuView() {
        return iMenu.getMenuView();
    }

    public void setMenuView(IMenu iMenu) {
        this.iMenu = iMenu;
    }
}
