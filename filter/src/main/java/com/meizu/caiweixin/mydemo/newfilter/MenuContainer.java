package com.meizu.caiweixin.mydemo.newfilter;

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

/**
 * Created by caiweixin on 5/14/15.
 */
public class MenuContainer extends PopupWindow {
    protected Context mContext;
    protected int mTabIndex;
    protected MenuDismissListener mMenuDismissListener;

    public int mCurrentPosition;

    private View mBackgroundView;
    private View mMenuView;
    private FrameLayout mContainer;

    public static final int DURATION = 272;//时间17帧 * 16ms/帧
    private Animator showTranslation, showAlpha, dismissTranslation, dismissAlpha;

    private int mMaxHeight, mMenuHeight;

    private boolean mIsShowAnimation, mIsDismissAnimation;

    LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    public interface MenuDismissListener {
        public void menuDismiss(int tabIndex);
    }

    public MenuContainer(Context context) {
        super(context);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;

        initArgs();

        initContainer();

        setPopupWindowStyle();
    }

    private void initContentAnimator(int height) {
        showTranslation = ObjectAnimator.ofFloat(mMenuView, "TranslationY", (float) -height, 0.0f).setDuration(DURATION);
        //TODO v4 import
//        showTranslation.setInterpolator(PathInterpolatorCompat.create(0.01f, 0.0f, 0.1f, 1.0f));

        dismissTranslation = ObjectAnimator.ofFloat(mMenuView, "TranslationY", 0.0f, (float) -height).setDuration(DURATION);
//        dismissTranslation.setInterpolator(PathInterpolatorCompat.create(0.33f, 0.0f, 0.2f, 1.0f));

        showTranslation.removeAllListeners();
        showTranslation.addListener(new Animator.AnimatorListener() {
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

        dismissTranslation.removeAllListeners();
        dismissTranslation.addListener(new Animator.AnimatorListener() {
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

    private void initBackgroundAnimator() {
        showAlpha = ObjectAnimator.ofFloat(mBackgroundView, "alpha", 0.0f, 1.0f).setDuration(DURATION);
        dismissAlpha = ObjectAnimator.ofFloat(mBackgroundView, "alpha", 1.0f, 0.0f).setDuration(DURATION);
    }

    private void initArgs() {
        mMaxHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.filter_gridview_max_height);
    }

    private void initContainer() {
        mContainer = new FrameLayout(mContext);
        mContainer.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        initBackground();
    }

    private void initBackground() {
        mBackgroundView = new LinearLayout(mContext);
        mBackgroundView.setLayoutParams(mLayoutParams);
        mBackgroundView.setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.filter_background_color)));
        mBackgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正在动画时，监听仍然有效，需过滤无效点击；
                if (!mIsDismissAnimation) {
                    dismissWithAnimation(true);
                }
            }
        });
        mContainer.addView(mBackgroundView);

        initBackgroundAnimator();
    }

    private void setMenuView(int tabIndex, View menuView) {
        if (null != mMenuView) {
            mContainer.removeView(mMenuView);
        }
        mTabIndex = tabIndex;
        mContainer.addView(menuView);
        menuView.setBackgroundColor(mContext.getResources().getColor(R.color.filter_contentview_color));
        mMenuHeight = getContentViewHeight(menuView);
        menuView.getLayoutParams().height = mMenuHeight;
        mMenuView = menuView;
        initContentAnimator(mMenuHeight);
    }

    private void setPopupWindowStyle() {
        super.setContentView(mContainer);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体无动画效果
        setAnimationStyle(0);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(null);
        setFocusable(false);
        setOutsideTouchable(false);
    }

    private int getContentViewHeight(View view) {
        int result = 0;
        if (view instanceof GridView) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            view.measure(View.MeasureSpec.UNSPECIFIED, expandSpec);
            result = view.getMeasuredHeight();
            result = result < mMaxHeight ? result : mMaxHeight;
        } else {
            result = mMaxHeight;
        }
        return result;
    }

    public void setShowMenuView(View anchor, int tabIndex, View menuView, boolean doBackgroundAnimator) {
        if (mIsDismissAnimation) {
            stopDismissAnimation();
        }
        if (mIsShowAnimation) {
            stopStartAnimation();
        }
        setMenuView(tabIndex, menuView);
        showAsDropDown(anchor, doBackgroundAnimator);
    }

    private void startShowAnimation(boolean doBackgroundAnimator) {
        mIsShowAnimation = true;
        showTranslation.start();
        if (doBackgroundAnimator) {
            showAlpha.start();
        }
    }

    private void startDismissAnimation() {
        mIsDismissAnimation = true;
        dismissTranslation.start();
        dismissAlpha.start();
    }

    public void stopStartAnimation() {
        if (showTranslation != null && showTranslation.isRunning()) {
            showTranslation.cancel();
        }
        if (showAlpha != null && showAlpha.isRunning()) {
            showAlpha.cancel();
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

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, true);
    }

    public void showAsDropDown(View anchor, boolean doBackgroundAnimator) {
        super.showAsDropDown(anchor);
        startShowAnimation(doBackgroundAnimator);
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

    public void setMenuDismissListener(MenuDismissListener menuDismissListener) {
        this.mMenuDismissListener = menuDismissListener;
    }

    public void setDefaultPosition(int position) {
        mCurrentPosition = position;
    }

    public boolean isAnimating() {
        return mIsShowAnimation || mIsDismissAnimation;
    }
}
