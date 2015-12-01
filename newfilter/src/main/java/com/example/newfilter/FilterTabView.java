package com.example.newfilter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by caiweixin on 5/14/15.
 */
public class FilterTabView extends LinearLayout {
    private Context mContext;
    private TextView mTextView;
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
        mTextView = (TextView) view.findViewById(R.id.tabview_tv);
        mImageView = (ImageView) view.findViewById(R.id.tabview_img);
    }

    public boolean isExpand() {
        return mIsExpand;
    }

    public void setTabText(String text) {
        if (null != mTextView) {
            mTextView.setText("" + text);
        }
    }

    public void setTabExpandImg(boolean isExpand) {
        mIsExpand = isExpand;
        if (null != mImageView) {
            mImageView.setImageResource(isExpand ? R.drawable.map_expand_up : R.drawable.map_expand_down);
        }
    }

    public void setTabOnClickListener(TabOnClickListener tabOnClickListener) {
        this.mTabOnClickListener = tabOnClickListener;
    }
}
