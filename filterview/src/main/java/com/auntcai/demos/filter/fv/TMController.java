package com.auntcai.demos.filter.fv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auntcai.demos.filter.R;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class TMController implements FilterView.ITabMenuController<Object> {
    private Object data;
    private Context mContext;

    private View mTabView, mMenuView;
    private TabHolder mTabHolder;

    private Drawable mDrawableRed, mDrawableGray;
    private int mTextColorRed, mTextColorGray;

    private boolean mTabStatus;

    public TMController(Context context) {
        mContext = context;

        mDrawableRed = mContext.getResources().getDrawable(R.drawable.arrow_up_red);
        mDrawableGray = mContext.getResources().getDrawable(R.drawable.arrow_down_gray);
        mDrawableRed.setBounds(0, 0, mDrawableRed.getMinimumWidth(), mDrawableRed.getMinimumHeight());
        mDrawableGray.setBounds(0, 0, mDrawableGray.getMinimumWidth(), mDrawableGray.getMinimumHeight());
        mTextColorRed = mContext.getResources().getColor(R.color.mz_theme_color_firebrick);
        mTextColorGray = mContext.getResources().getColor(R.color.filter_tab_text_color);
    }

    @Override
    public void onCreateMenuView(int maxHeight) {
        if (null == mMenuView) {
            mMenuView = LayoutInflater.from(mContext).inflate(R.layout.filter_container_test, null);
            //TODO check menu height
        }
    }

    @Override
    public View getMenuView() {
        TextView textView = (TextView) mMenuView.findViewById(R.id.filter_container_tv);
        textView.setText("M-" + TMController.this.hashCode());
        return mMenuView;
    }

    @Override
    public void onCreateTabView(ViewGroup parent) {
        if (null == mTabView) {
            mTabView = LayoutInflater.from(mContext).inflate(R.layout.filter_tab_view, parent, false);
            mTabHolder = new TabHolder(mTabView);
        }
    }

    @Override
    public View getTabView() {
        mTabHolder.textView.setText("T-" + TMController.this.hashCode());
        return mTabView;
    }

    @Override
    public boolean getTabStatus() {
        return mTabStatus;
    }

    @Override
    public void changeTabStatus(boolean tabStatus) {
        mTabStatus = tabStatus;
        mTabHolder.textView.setTextColor(tabStatus ? mTextColorRed : mTextColorGray);
        mTabHolder.textView.setCompoundDrawables(null, null, tabStatus ? mDrawableRed : mDrawableGray, null);
    }

    @Override
    public void setSelectedData(Object o) {

    }

    @Override
    public Object getSelectedData() {
        return data;
    }

    static class TabHolder {
        TextView textView;

        public TabHolder(View view) {
            textView = (TextView) view.findViewById(R.id.tab_view_tv);
        }
    }
}
