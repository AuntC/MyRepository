package com.meizu.caiweixin.mydemo.filter.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meizu.media.life.R;

public class FilterTabView extends LinearLayout {
    private Context mContext;
    private TextView mTabTitle, mDivider;
    private ImageView mImageView;
    private boolean mIsExpand;
    private int mTabIndex;
    private TabOnClickListener mTabOnClickListener;

    public interface TabOnClickListener {
        public void onClick(int tabIndex, boolean isExpand);
    }

    public FilterTabView(Context context, int tabIndex) {
        super(context);
        initViews(context, tabIndex);
    }

    private void initViews(Context context, int tabIndex) {
        mContext = context;
        mTabIndex = tabIndex;
        mIsExpand = false;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.filter_tabview, this);
        this.setGravity(Gravity.CENTER);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabExpandImg(!mIsExpand);
                if (null != mTabOnClickListener) {
                    mTabOnClickListener.onClick(mTabIndex, mIsExpand);
                }
            }
        });
        mTabTitle = (TextView) view.findViewById(R.id.tabview_tv);
        mDivider = (TextView) view.findViewById(R.id.filter_divide_line);
        mDivider.setVisibility(tabIndex == 0 ? GONE : VISIBLE);
        mImageView = (ImageView) view.findViewById(R.id.tabview_img);
    }

    public boolean isExpand() {
        return mIsExpand;
    }

    public void setTabText(String text) {
        if (null != mTabTitle) {
            mTabTitle.setText("" + text);
        }
    }

    public String getTabText() {
        return null == mTabTitle ? "" : mTabTitle.getText().toString();
    }

    public void setTabExpandImg(boolean isExpand) {
        mIsExpand = isExpand;
        if (null != mImageView) {
            mTabTitle.setTextColor(isExpand ? mContext.getResources().getColor(R.color.mz_theme_color_firebrick) : mContext.getResources().getColor(R.color.filter_tab_text_color));
            mImageView.setImageResource(isExpand ? R.drawable.arrow_up_red : R.drawable.arrow_down_gray);
        }
    }

    public void setTabOnClickListener(TabOnClickListener tabOnClickListener) {
        this.mTabOnClickListener = tabOnClickListener;
    }
}
