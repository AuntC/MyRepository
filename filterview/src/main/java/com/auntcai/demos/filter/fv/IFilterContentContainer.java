package com.auntcai.demos.filter.fv;

import android.view.View;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public interface IFilterContentContainer {
    void init(FilterView filterView);
    void showFilterContent(int filterTabId, View filterContentView);
}
