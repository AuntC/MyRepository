package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

/**
 * Created by caiweixin on 5/22/15.
 */
public class PinnedHeaderExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {
    private static final String TAG = "PinnedHeaderExpandableListView";

    private OnScrollListener mOnScrollListener;
    private OnHeaderUpdateListener mHeaderUpdateListener;

    private View mHeaderView;
    private int mHeaderWidth, mHeaderHeight;
    private boolean mHeaderViewVisibility;

    private boolean mActionDownHappened = false;
    protected boolean mIsHeaderGroupClickable = true;

    public interface OnHeaderUpdateListener {
        public View getPinnedHeader();

        public void updatePinnedHeader(View headerView, int firstVisibleGroupPos);
    }

    public PinnedHeaderExpandableListView(Context context) {
        super(context);
        initView();
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setFadingEdgeLength(0);
        setOnScrollListener(this);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (this != l) {
            mOnScrollListener = l;
        } else {
            mOnScrollListener = null;
        }
        super.setOnScrollListener(l);
    }

    public void setOnHeaderUpdateListener(OnHeaderUpdateListener listener) {
        this.mHeaderUpdateListener = listener;
        if (listener == null) {
            mHeaderView = null;
            mHeaderWidth = mHeaderHeight = 0;
            return;
        }
        mHeaderView = listener.getPinnedHeader();
        int firstVisiblePos = getFirstVisiblePosition();
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        listener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
        requestLayout();
        postInvalidate();
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener, boolean isHeaderGroupClickable) {
        mIsHeaderGroupClickable = isHeaderGroupClickable;
        super.setOnGroupClickListener(onGroupClickListener);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int position = getFirstVisiblePosition();
        View v = getChildAt(0);
        int childTop = (v == null) ? 0 : v.getTop();
//        if (position == 0 && childTop > 0) {
//            mHeaderViewVisibility = false;
//        } else {
//            mHeaderViewVisibility = true;
//        }

        mHeaderViewVisibility = !(position == 0 && childTop > 0);

        if (totalItemCount > 0) {
            refreshHeader();
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView == null) {
            return;
        }
        measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderView != null) {
            int delta = mHeaderView.getTop();
            mHeaderView.layout(0, delta, mHeaderWidth, mHeaderHeight + delta);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderView != null) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        if (mHeaderView != null && y >= mHeaderView.getTop() && y <= mHeaderView.getBottom()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                mActionDownHappened = true;
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mIsHeaderGroupClickable && mActionDownHappened) {
                    int firstVisiblePosition = getFirstVisiblePosition();
                    int groupPosition = getPackedPositionGroup(getExpandableListPosition(firstVisiblePosition));
                    if (groupPosition == 0) {
                        //first group position set cannot close
                    } else {
                        if (groupPosition != INVALID_POSITION) {
                            if (isGroupExpanded(groupPosition)) {
                                collapseGroup(groupPosition);
                            } else {
                                expandGroup(groupPosition);
                            }
                        }
                        setSelection(getFlatListPosition(getPackedPositionForGroup(groupPosition)));
                    }
                }
                mActionDownHappened = false;
            }
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    protected void refreshHeader() {
        if (mHeaderView == null) {
            return;
        }
        int firstVisiblePos = getFirstVisiblePosition();
        int pos = firstVisiblePos + 1;
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        int group = getPackedPositionGroup(getExpandableListPosition(pos));

        if (group == firstVisibleGroupPos + 1) {
            View view = getChildAt(1);
            if (view == null) {
                return;
            }
            if (view.getTop() <= mHeaderHeight) {
                int delta = mHeaderHeight - view.getTop();
                mHeaderView.layout(0, -delta, mHeaderWidth, mHeaderHeight - delta);
            } else {
                mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
            }
        } else {
            mHeaderView.layout(0, 0, mHeaderViewVisibility ? mHeaderWidth : 0,
                    mHeaderViewVisibility ? mHeaderHeight : 0);
        }

        if (mHeaderUpdateListener != null) {
            mHeaderUpdateListener.updatePinnedHeader(mHeaderView,
                    firstVisibleGroupPos);
        }
    }
}
