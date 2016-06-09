package com.auntcai.demos.filter.filtertab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class FilterView extends LinearLayout {
    private ArrayList<IFilterItemView> mChilds;
    private IFilterContentContainer mFilterContentContainer;
    //popup

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterView(Context context) {
        super(context);

        for (int i = 0; i < getChildCount(); ++i) {
            ((IFilterItemView) getChildAt(i)).setTabId(i);
        }
    }

    public void selectTab(IFilterItemView selectedChild) {
        for (IFilterItemView child : mChilds) {
            if (child.getTabId() != selectedChild.getTabId()) {
                child.unSelectTab();
            }
        }

        mFilterContentContainer.show(selectedChild.getItemContentView());
    }

    public interface IFilterItemView {
        public void setTabId(int id);

        public int getTabId();

        View getItemContentView();

        public void selectTab();

        public void unSelectTab();
    }

    public interface IFilterContentContainer {
        void show(View view);
    }
}
