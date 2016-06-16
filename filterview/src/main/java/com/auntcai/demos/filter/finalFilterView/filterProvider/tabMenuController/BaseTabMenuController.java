package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.finalFilterView.FilterView;

/**
 * Description: 生活服务中用的统一的tab view样式，故提供一个基类；
 *
 * @author caiweixin
 * @since 6/12/16.
 */
public abstract class BaseTabMenuController<T extends FilterView.IFilterItem> extends FilterView.ITabMenuController<T> {

    private boolean mTabStatus;
    private TabHolder mTabHolder;

    private Drawable mDrawableRed, mDrawableGray;
    private int mTextColorRed, mTextColorGray;

    public BaseTabMenuController(Context context, int type) {
        super(context, type);
        mDrawableRed = mContext.getResources().getDrawable(R.drawable.arrow_up_red);
        mDrawableGray = mContext.getResources().getDrawable(R.drawable.arrow_down_gray);
        mDrawableRed.setBounds(0, 0, mDrawableRed.getMinimumWidth(), mDrawableRed.getMinimumHeight());
        mDrawableGray.setBounds(0, 0, mDrawableGray.getMinimumWidth(), mDrawableGray.getMinimumHeight());
        mTextColorRed = mContext.getResources().getColor(R.color.mz_theme_color_firebrick);
        mTextColorGray = mContext.getResources().getColor(R.color.filter_tab_text_color);
    }

    @Override
    public View getTabView(ViewGroup parent) {
        if (null == mTabView) {
            mTabView = LayoutInflater.from(mContext).inflate(R.layout.filter_tab_view, parent, false);
            mTabHolder = new TabHolder(mTabView);
        }
        changeSelectItem(getCurrentSelection());
        return mTabView;
    }

    public void changeSelectItem(T select) {
        mTabHolder.textView.setText(select.getTabShowText());
    }

    public void changeTabStatus(boolean tabStatus) {
        mTabStatus = tabStatus;
        mTabHolder.textView.setTextColor(tabStatus ? mTextColorRed : mTextColorGray);
        mTabHolder.textView.setCompoundDrawables(null, null, tabStatus ? mDrawableRed : mDrawableGray, null);
    }

    static class TabHolder {
        TextView textView;

        public TabHolder(View view) {
            textView = (TextView) view.findViewById(R.id.tab_view_tv);
        }
    }
}
