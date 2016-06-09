package com.auntcai.demos.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auntcai.demos.filter.filterview.FilterView;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/8/16.
 */
public class FTabAdapter extends FilterView.FilterTabAdapter<MyTabHolder, MyTab> {
    private Context mContext;
    private Drawable mDrawableRed, mDrawableGray;
    private int mTextColorRed, mTextColorGray;

    public FTabAdapter(Context context) {
        mContext = context;

        mDrawableRed = mContext.getResources().getDrawable(R.drawable.arrow_up_red);
        mDrawableGray = mContext.getResources().getDrawable(R.drawable.arrow_down_gray);
        mDrawableRed.setBounds(0, 0, mDrawableRed.getMinimumWidth(), mDrawableRed.getMinimumHeight());
        mDrawableGray.setBounds(0, 0, mDrawableGray.getMinimumWidth(), mDrawableGray.getMinimumHeight());
    }

    @Override
    public MyTabHolder onCreateTabHolder(ViewGroup parent, int position) {
        return new MyTabHolder(LayoutInflater.from(mContext).inflate(R.layout.filter_tab_view, parent, false));
    }

    @Override
    public void onBindTabHolder(MyTabHolder holder, int position) {
        holder.textView.setText(getItem(position).getShowText());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public View getContentView(int position) {
        return getItem(position).getMenuView();
    }

    public MyTab getItem(int position) {
        return dataSet.get(position);
    }

    public void setDataSet(List<MyTab> dataSet) {
        this.dataSet = dataSet;
    }
}
