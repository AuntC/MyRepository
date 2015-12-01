package com.meizu.caiweixin.mydemo.filter.menu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterBaseBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterInterface;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/14/15.
 */
public abstract class BaseMenu extends PopupWindow {
    protected Context mContext;
    protected int mTabIndex, mMenuType;
    protected FilterMenuBean mFilterMenuBean;
    protected MenuOnItemClickListener mMenuOnItemClickListener;
    protected MenuDismissListener mMenuDismissListener;

    public int mCurrentPosition;

    private View mFootView, mContentView;

    private static final int DURATION = 272;//时间17帧 * 16ms/帧
    private Animator showTranslation, showAlpha, dismissTranslation, dismissAlpha;

    private int mMaxHeight, mContentHeight;

    private boolean mIsShowAnimation, mIsDismissAnimation;

    LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    public interface MenuOnItemClickListener {
        public void onFilterMenuItemClick(int tabIndex, FilterInterface filter);
    }

    public interface MenuDismissListener {
        public void menuDismiss(int tabIndex);
    }

    public BaseMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context);
        initArgs(context, tabIndex, filterMenuBean);
    }

    private void initArgs(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        mContext = context;
        mTabIndex = tabIndex;
        mMenuType = filterMenuBean.menuType;
        mFilterMenuBean = filterMenuBean;

        mMaxHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.filter_gridview_max_height);
        setPopupWindowStyle(context, initContentView());
    }

    protected abstract View initContentView();

    private void setPopupWindowStyle(Context context, View contentView) {
        FrameLayout frameLayout = new FrameLayout(mContext);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        LinearLayout footView = new LinearLayout(mContext);
        footView.setLayoutParams(mLayoutParams);
        footView.setBackground(new ColorDrawable(context.getResources().getColor(R.color.filter_background_color)));
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在动画时，监听仍然有效，需过滤无效点击；
                if (!mIsDismissAnimation) {
                    dismissWithAnimation(true);
                }
            }
        });

        frameLayout.addView(footView);
        frameLayout.addView(contentView);

        contentView.setBackgroundColor(mContext.getResources().getColor(R.color.filter_contentview_color));
        this.mContentHeight = getContentViewHeight(contentView);
        contentView.getLayoutParams().height = mContentHeight;
        this.mContentView = contentView;
        this.mFootView = footView;

        setContentView(frameLayout);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体无动画效果
        setAnimationStyle(0);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(null);
        footView.setFocusableInTouchMode(false);
        setFocusable(false);
        setOutsideTouchable(false);

        initAnimation(mContentHeight);
    }

    private int getContentViewHeight(View view) {
        int result = 0;
        if (view instanceof PinnedHeaderExpandableListView) {
            result = mMaxHeight;
        } else if (view instanceof GridView) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            view.measure(View.MeasureSpec.UNSPECIFIED, expandSpec);
            result = view.getMeasuredHeight();
            result = result < mMaxHeight ? result : mMaxHeight;
        } else {
            result = mMaxHeight;
        }
        return result;
    }

    private void initAnimation(int height) {
        showTranslation = ObjectAnimator.ofFloat(mContentView, "TranslationY", (float) -height, 0.0f).setDuration(DURATION);
        showTranslation.setInterpolator(PathIterpolatorCompat.create(0.01f, 0.0f, 0.1f, 1.0f));
        showAlpha = ObjectAnimator.ofFloat(mFootView, "alpha", 0.0f, 1.0f).setDuration(DURATION);

        dismissTranslation = ObjectAnimator.ofFloat(mContentView, "TranslationY", 0.0f, (float) -height).setDuration(DURATION);
        dismissTranslation.setInterpolator(PathInterpolatorCompat.create(0.33f, 0.0f, 0.2f, 1.0f));
        dismissAlpha = ObjectAnimator.ofFloat(mFootView, "alpha", 1.0f, 0.0f).setDuration(DURATION);
    }

    private void startShowAnimation() {
        mIsShowAnimation = true;
        showTranslation.start();
        showAlpha.start();
        showAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsShowAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void startDismissAnimation() {
        mIsDismissAnimation = true;
        dismissTranslation.start();
        dismissAlpha.start();

        dismissAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsDismissAnimation = false;
                dismiss();
                if (null != mMenuDismissListener) {
                    mMenuDismissListener.menuDismiss(mTabIndex);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void stopStartAnimation() {
        if (showTranslation != null && showTranslation.isRunning()) {
            showTranslation.pause();
        }
        if (showAlpha != null && showAlpha.isRunning()) {
            showAlpha.pause();
        }
        mIsShowAnimation = false;
    }

    public void stopDismissAnimation() {
        if (dismissTranslation != null && dismissTranslation.isRunning()) {
            dismissTranslation.cancel();
        }
        if (dismissAlpha != null && dismissAlpha.isRunning()) {
            dismissAlpha.cancel();
        }
        mIsDismissAnimation = false;
    }

    public enum WindowStatus {
        CancelShow, CancelDismiss, NormalBack;
    }

    public WindowStatus getCurrentWindowStatus() {
        if (mIsShowAnimation) {
            return WindowStatus.CancelShow;
        } else if (mIsDismissAnimation) {
            return WindowStatus.CancelDismiss;
        } else {
            return WindowStatus.NormalBack;
        }
    }

    public WindowStatus onBackPressed() {
        if (mIsShowAnimation) {
            stopStartAnimation();
            dismissWithAnimation(false);
            return WindowStatus.CancelShow;
        } else if (mIsDismissAnimation) {
            stopDismissAnimation();
            dismissWithAnimation(false);
            return WindowStatus.CancelDismiss;
        } else {
            dismissWithAnimation(true);
            return WindowStatus.NormalBack;
        }
    }


    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        startShowAnimation();
    }

    public void dismissWithAnimation(boolean doAnimation) {
        if (doAnimation) {
            startDismissAnimation();
        } else {
            dismiss();
            if (null != mMenuDismissListener) {
                mMenuDismissListener.menuDismiss(mTabIndex);
            }
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        this.mMenuOnItemClickListener = menuOnItemClickListener;
    }

    public void setMenuDismissListener(MenuDismissListener menuDismissListener) {
        this.mMenuDismissListener = menuDismissListener;
    }

    public void updateMenuAdapter(FilterMenuBean<FilterBaseBean> filterMenuBean) {
        mFilterMenuBean = filterMenuBean;
    }

    public void setDefaultPosition(int position) {
        mCurrentPosition = position;
    }
}
