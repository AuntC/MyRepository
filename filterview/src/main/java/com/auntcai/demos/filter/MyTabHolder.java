package com.auntcai.demos.filter;

import android.view.View;
import android.widget.TextView;

import com.auntcai.demos.filter.filterview.FilterView;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class MyTabHolder extends FilterView.TabHolder {
    public TextView textView;

    public MyTabHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tab_view_tv);
    }
}
