package com.auntcai.demos.filter.finalFilterView.filterProvider.menuStyle.pop;

import android.content.Context;
import android.view.View;

import com.auntcai.demos.filter.finalFilterView.FilterView;

/**
 * Description: pop menu view container‘s controller
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class PopController implements FilterView.IFilterController {

    private View anchor;
    private PopMenuContainer menuContainer;

    public PopController(Context context) {
        menuContainer = new PopMenuContainer(context);
    }

    public void setAnchor(View anchor) {
        this.anchor = anchor;
    }

    public void setMenuDismissListener(PopMenuContainer.MenuDismissListener menuDismissListener) {
        menuContainer.setMenuDismissListener(menuDismissListener);
    }

    @Override
    public int getMaxContentViewHeight() {
        return menuContainer.getMaxContentViewHeight();
    }

    @Override
    public void show(int tabIndex, View menuView) {
        if (null == anchor) {
            throw new IllegalArgumentException("The anchor is null!");
        }
        menuContainer.setShowMenuView(anchor, tabIndex, menuView, !menuContainer.isShowing());
    }

    @Override
    public void hide(boolean doAnimation) {
        menuContainer.dismissWithAnimation(doAnimation);
    }

    @Override
    public boolean onBackPressed() {
        return menuContainer.isShowing() && menuContainer.onBackPressed() == PopMenuContainer.WindowStatus.NormalBack;
    }
}
