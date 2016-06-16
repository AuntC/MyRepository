package com.auntcai.demos.filter.HorizontalTranslate;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.auntcai.demos.filter.R;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/16/16.
 */
public class HorizontalFlipperView extends ViewFlipper {

    private AdapterDataSetObserver mAdapterDataSetObserver;
    private Adapter mAdapter;

    private int mCurrentPosition;
    private ViewHolder mCurViewHolder, mNextViewHolder;
    private boolean mShowCurrent;

    public HorizontalFlipperView(Context context) {
        this(context, null);
    }

    public HorizontalFlipperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top));
        setFlipInterval(2000);
    }

    public void setAdapter(Adapter adapter) {
        if (null != mAdapter && null != mAdapterDataSetObserver) {
            mAdapter.unregisterDataSetObserver(mAdapterDataSetObserver);
        }

        this.mAdapter = adapter;
        if (null != mAdapter) {
            mAdapterDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mAdapterDataSetObserver);
        }
    }

    private void initChildViews() {
        mCurrentPosition = 0;
        removeAllViews();
        if (mAdapter.getItemCount() > 0) {
            if (mAdapter.getItemCount() > 1) {
                if (null == mCurViewHolder) {
                    mCurViewHolder = mAdapter.onCreateViewHolder(HorizontalFlipperView.this);
                    mAdapter.onBindViewHolder(mCurViewHolder, mCurrentPosition);
                }
                if (null == mNextViewHolder) {
                    mNextViewHolder = mAdapter.onCreateViewHolder(HorizontalFlipperView.this);
                }
                addView(mCurViewHolder.itemView);
                addView(mNextViewHolder.itemView);
                startFlipping();
            } else {
                ViewHolder viewHolder = mAdapter.onCreateViewHolder(HorizontalFlipperView.this);
                mAdapter.onBindViewHolder(viewHolder, 0);
                addView(viewHolder.itemView);
            }
        }
    }

    @Override
    public void showNext() {
        mCurrentPosition++;
        mCurrentPosition %= mAdapter.getItemCount();
        Log.e("show", "mCurrentPosition-" + mCurrentPosition + "  mShowCurrent-" + mShowCurrent);
        mAdapter.onBindViewHolder(mShowCurrent ? mCurViewHolder : mNextViewHolder, mCurrentPosition);
        mShowCurrent = !mShowCurrent;
        super.showNext();
    }

    @Override
    public void startFlipping() {
        if (mAdapter.getItemCount() > 0) {
            super.startFlipping();
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            initChildViews();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private final DataSetObservable mDataSetObservable = new DataSetObservable();

        public abstract VH onCreateViewHolder(ViewGroup parent);

        public abstract void onBindViewHolder(VH holder, int position);

        public abstract int getItemCount();

        public abstract Object getItemData(int position);

        public void registerDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.registerObserver(observer);
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            mDataSetObservable.unregisterObserver(observer);
        }

        public void notifyDataSetChanged() {
            mDataSetObservable.notifyChanged();
        }
    }

    public static abstract class ViewHolder {
        public final View itemView;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }
    }
}
