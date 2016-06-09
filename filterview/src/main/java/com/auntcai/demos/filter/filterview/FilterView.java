package com.auntcai.demos.filter.filterview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.filterview.menuContainer.FilterMenuContainer;
import com.auntcai.demos.filter.filterview.tab.ITab;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/8/16.
 */
public class FilterView extends LinearLayout {
    public static final String TAG = "FilterView";

    private static final int MIN_ITEM_COUNT = 1;
    private static final int MAX_ITEM_COUNT = 4;

    private Context mContext;
    private FilterMenuContainer mMenuContainer;
    private int mCurrentIndex = -1;

    private FilterTabAdapter mAdapter;

    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mMenuContainer = new FilterMenuContainer(context);
    }

    public void setAdapter(FilterTabAdapter adapter) {
        if (null == adapter) {
            throw new IllegalArgumentException("The adapter is null!");
        }
        this.mAdapter = adapter;
        if (adapter.getItemCount() < MIN_ITEM_COUNT || adapter.getItemCount() > MAX_ITEM_COUNT) {
            throw new IllegalArgumentException("Total itemCount must in " + MIN_ITEM_COUNT + "~" + MAX_ITEM_COUNT);
        }
        for (int position = 0; position < adapter.getItemCount(); position++) {
            if (position > 0) {
                addView(LayoutInflater.from(mContext).inflate(R.layout.filter_tab_divider, FilterView.this, false));
            }
            TabHolder holder = adapter.onCreateTabHolder(FilterView.this, position);
            adapter.onBindTabHolder(holder, position);
            final int finalPosition = position;
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealOnClickEvent(finalPosition);
                }
            });
            addView(holder.itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    private void dealOnClickEvent(int tabIndex) {
        if (mCurrentIndex == -1) {
            mMenuContainer.setMenuView(tabIndex, mAdapter.getContentView(tabIndex));

            mMenuContainer.showAsDropDown(FilterView.this, true);
            mCurrentIndex = tabIndex;
        } else {
            if (mCurrentIndex == tabIndex) {
                mMenuContainer.dismissWithAnimation(true);
                mCurrentIndex = -1;
            } else {
                mMenuContainer.setMenuView(tabIndex, mAdapter.getContentView(tabIndex));
                mMenuContainer.showAsDropDown(FilterView.this, false);
                mCurrentIndex = tabIndex;
            }
        }
    }

    public static abstract class FilterTabAdapter<TH extends TabHolder, IT extends ITab> {
        protected List<IT> dataSet;

        public abstract TH onCreateTabHolder(ViewGroup parent, int position);

        public abstract void onBindTabHolder(TH holder, int position);

        public abstract int getItemCount();

        public abstract View getContentView(int position);

        public abstract IT getItem(int position);
    }

    public static abstract class TabHolder {
        public final View itemView;

        protected TabHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }
    }
}
