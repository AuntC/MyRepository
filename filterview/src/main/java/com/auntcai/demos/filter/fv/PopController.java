package com.auntcai.demos.filter.fv;

import android.content.Context;
import android.view.View;

import com.auntcai.demos.filter.filterview.menuContainer.FilterMenuContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class PopController implements FilterView.IFilterController {

    private View anchor;
    private FilterMenuContainer menuContainer;

    private List<TabChangeListener> listeners;

    private static final int INDEX_ORIGINAL = -1;

    private int currentIndex;

    public PopController(Context context) {
        currentIndex = INDEX_ORIGINAL;
        menuContainer = new FilterMenuContainer(context);
        menuContainer.setMenuDismissListener(new FilterMenuContainer.MenuDismissListener() {
            @Override
            public void menuDismiss(int tabIndex) {
                currentIndex = -1;
                notifyTabs(tabIndex, false);
            }
        });

        listeners = new ArrayList<>();
    }

    @Override
    public void setAnchor(View anchor) {
        this.anchor = anchor;
    }

    @Override
    public int getMaxContentViewHeight() {
        return menuContainer.getContentViewHeight();
    }

    @Override
    public void onChangeStatus(View view, int tabIndex) {
        if (menuContainer.isAnimating()) {
            menuContainer.stopAllAnimating();
        }
        if (INDEX_ORIGINAL == currentIndex) {
            menuContainer.setMenuView(tabIndex, view);
            menuContainer.showAsDropDown(anchor);
            currentIndex = tabIndex;
            notifyTabs(currentIndex, true);
        } else {
            if (currentIndex == tabIndex) {
                menuContainer.dismissWithAnimation(true);
                currentIndex = INDEX_ORIGINAL;
                notifyTabs(currentIndex, false);
            } else {
                menuContainer.setMenuView(tabIndex, view);
                menuContainer.showAsDropDown(anchor, false);
                currentIndex = tabIndex;
                notifyTabs(currentIndex, true);
            }
        }
    }

    @Override
    public void addTabChangeListener(TabChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyTabs(int tabIndex, boolean tabStatus) {
        for (TabChangeListener listener : listeners) {
            listener.tabStatusChanged(tabIndex, tabStatus);
        }
    }

    @Override
    public boolean onBackPressed() {
        return menuContainer.isShowing() && menuContainer.onBackPressed() == FilterMenuContainer.WindowStatus.NormalBack;
    }
}
