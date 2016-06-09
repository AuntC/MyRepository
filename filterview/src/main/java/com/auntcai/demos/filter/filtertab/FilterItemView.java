package com.auntcai.demos.filter.filtertab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class FilterItemView extends TextView implements FilterView.IFilterItemView {

    private int mTabId;
    private FilterView mParent;
    private IFilterContentView mFilterContentView;

    public FilterItemView(Context context) {
        super(context);
    }

    public FilterItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTabId(int id) {
        mTabId = id;
    }

    @Override
    public int getTabId() {
        return mTabId;
    }

    @Override
    public View getItemContentView() {
        return mFilterContentView.get();
    }

    @Override
    public void selectTab() {
        mParent.selectTab(this);
    }

    @Override
    public void unSelectTab() {
    }

    public FilterItemView setFilterContentView(IFilterContentView filterContentView) {
        mFilterContentView = filterContentView;
        return this;
    }

    public interface IFilterContentView {
        View get();
        void setSelectFilterController(IFilterController controller);
    }

    public interface IFilterItem {
        String getId();
        String getText();
    }
}
