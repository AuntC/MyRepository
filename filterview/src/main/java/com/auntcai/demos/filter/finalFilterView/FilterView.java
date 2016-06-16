package com.auntcai.demos.filter.finalFilterView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.auntcai.demos.filter.R;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/9/16.
 */
public class FilterView extends LinearLayout {
    public static final String TAG = "FilterView";

    private Context mContext;

    private int mCurrentTabIndex;

    private IFilterController mFilterController;

    private List<ITabMenuController> mTabMenuControllers;

    private boolean closeAfterSelected;

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
        mCurrentTabIndex = -1;
        closeAfterSelected = true;
    }

    public void setCloseAfterSelected(boolean closeAfterSelected) {
        this.closeAfterSelected = closeAfterSelected;
    }

    public interface MenuSelectedListener {
        void onMenuSelected(int tabIndex, IFilterItem iFilterItem);
    }

    private MenuSelectedListener menuSelectedListener;

    public void setMenuSelectedListener(MenuSelectedListener menuSelectedListener) {
        this.menuSelectedListener = menuSelectedListener;
    }

    public void setFilterController(IFilterController filterController) {
        if (null == filterController) {
            throw new IllegalArgumentException("The filterController is null!");
        }
        this.mFilterController = filterController;
    }

    public void setTabMenuControllers(List<ITabMenuController> tabMenuControllers) {
        this.mTabMenuControllers = tabMenuControllers;
        invalidateTabViews();
    }

    private void invalidateTabViews() {
        if (null == mFilterController) {
            throw new IllegalArgumentException("The filterController is null!");
        }
        removeAllViews();
        for (int position = 0; position < mTabMenuControllers.size(); position++) {
            if (position > 0) {
                //add divider view
                addView(LayoutInflater.from(mContext).inflate(R.layout.filter_tab_divider, FilterView.this, false));
            }

            final ITabMenuController tabMenuController = mTabMenuControllers.get(position);
            final View tabView = tabMenuController.getTabView(FilterView.this);
            final int finalPosition = position;
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (-1 == mCurrentTabIndex) {
                        mFilterController.show(finalPosition, tabMenuController.getMenuView(mFilterController.getMaxContentViewHeight()));
                        changeCurrentTabStatus(finalPosition, true);
                    } else {
                        if (mCurrentTabIndex == finalPosition) {
                            mFilterController.hide(true);
                            changeCurrentTabStatus(-1, false);
                        } else {
                            mFilterController.show(finalPosition, tabMenuController.getMenuView(mFilterController.getMaxContentViewHeight()));
                            changeCurrentTabStatus(finalPosition, true);
                        }
                    }
                }
            });
            tabMenuController.setChooseItemListener(new ITabMenuController.ChooseItemListener() {
                @Override
                public void doNothingMaybeClose() {
                    if (closeAfterSelected) {
                        mFilterController.hide(true);
                        changeCurrentTabStatus(-1, false);
                    }
                }

                @Override
                public void onItemChosen(IFilterItem iFilterItem) {
                    if (null != menuSelectedListener) {
                        menuSelectedListener.onMenuSelected(finalPosition, iFilterItem);
                    }
                    if (closeAfterSelected) {
                        mFilterController.hide(true);
                        changeCurrentTabStatus(-1, false);
                    }
                }
            });
            //add tab view
            addView(tabView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    /**
     * 控制弹出menu样式
     */
    public interface IFilterController {

        int getMaxContentViewHeight();

        void show(int tabIndex, View menuView);

        void hide(boolean doAnimation);

        boolean onBackPressed();
    }

    /**
     * 控制tab与menu间的交互
     *
     * @param <T>
     */
    public static abstract class ITabMenuController<T extends IFilterItem> {
        protected Context mContext;
        protected View mTabView, mMenuView;
        private List<T> mDataSet;
        private int mCurrentItemPosition, mCurrentChildPosition;
        private T mSelectedITem;
        private int mType;

        public ITabMenuController(Context context, int type) {
            this.mContext = context;
            mType = type;
        }

        public int getType() {
            return mType;
        }

        public void setDataSet(List<T> dataSet) {
            this.mDataSet = dataSet;
        }

        public List<T> getDataSet() {
            return mDataSet;
        }

        public int getItemCount() {
            if (null == mDataSet) {
                throw new IllegalArgumentException("The mDataSet is null!");
            }
            return mDataSet.size();
        }

        public T getItem(int position) {
            if (null == mDataSet) {
                throw new IllegalArgumentException("The mDataSet is null!");
            }
            return mDataSet.get(position);
        }

        public void setDefaultSelection(int itemPosition, int childPosition) {
            if (null == mDataSet) {
                throw new IllegalArgumentException("The mDataSet is null!");
            }
            if (mDataSet.get(mCurrentItemPosition).hasChildList()) {
                mCurrentItemPosition = itemPosition;
                mCurrentChildPosition = childPosition;

                getItem(itemPosition).setSelected(true);
                List<T> children = getItem(itemPosition).getChildren();
                children.get(childPosition).setSelected(true);

                mSelectedITem = children.get(childPosition);
//                mSelectedITem = mSelectedITem.isAll() ? (T) mSelectedITem.getParentItem() : mSelectedITem;
            } else {
                mCurrentItemPosition = itemPosition;

                mDataSet.get(itemPosition).setSelected(true);

                mSelectedITem = mDataSet.get(itemPosition);
            }

        }

        public void setCurrentSelection(int itemPosition, int childPosition) {
            if (null == mDataSet) {
                throw new IllegalArgumentException("The mDataSet is null!");
            }
            if (mDataSet.get(mCurrentItemPosition).hasChildList()) {
                if (mCurrentItemPosition == itemPosition && mCurrentChildPosition == childPosition) {
                    doNothingMaybeClose();
                } else {
                    List<T> child = getItem(mCurrentItemPosition).getChildren();
                    child.get(mCurrentChildPosition).setSelected(false);

                    List<T> child2 = getItem(itemPosition).getChildren();
                    child2.get(childPosition).setSelected(true);

                    mCurrentItemPosition = itemPosition;
                    mCurrentChildPosition = childPosition;

                    mSelectedITem = child2.get(childPosition);
//                    mSelectedITem = mSelectedITem.isAll() ? (T) mSelectedITem.getParentItem() : mSelectedITem;

                    changeCurrentSelection(mSelectedITem);
                }
            } else {
                if (mCurrentItemPosition == itemPosition) {
                    doNothingMaybeClose();
                } else {
                    mDataSet.get(mCurrentItemPosition).setSelected(false);
                    mDataSet.get(itemPosition).setSelected(true);

                    mCurrentItemPosition = itemPosition;
                    mSelectedITem = mDataSet.get(itemPosition);

                    changeCurrentSelection(mSelectedITem);
                }
            }
        }

        public T getCurrentSelection() {
            return null == mSelectedITem ? (mDataSet.get(0).hasChildList() ? (T) mDataSet.get(0).getChildren().get(0) : mDataSet.get(0)) : mSelectedITem;
        }

        public abstract void doNothingMaybeClose();

        public abstract void changeCurrentSelection(T selectedItem);

        public interface ChooseItemListener {
            void doNothingMaybeClose();

            void onItemChosen(IFilterItem iFilterItem);
        }

        protected ChooseItemListener mChooseItemListener;

        public void setChooseItemListener(ChooseItemListener chooseItemListener) {
            this.mChooseItemListener = chooseItemListener;
        }

        public abstract View getTabView(ViewGroup parent);

        public abstract void changeTabStatus(boolean tabStatus);

        public abstract View getMenuView(int maxHeight);

        public abstract void measureMenuHeight(int maxHeight);
    }

    public interface IFilterItem<T extends IFilterItem> {
        String getTabShowText();

        String getMenuShowText();

        void setIsAll(boolean isAll);

        boolean isAll();

        void setSelected(boolean isSelected);

        boolean isSelected();

        boolean hasChildList();

        void setLoaded(boolean isLoaded);

        boolean isLoaded();

        List<T> getChildren();

        void setChildren(List<T> children);

        void setParentItem(T parent);

        T getParentItem();
    }

    public boolean onBackPressed() {
        return mFilterController.onBackPressed();
    }

    public void changeCurrentTabStatus(int tabIndex, boolean status) {
        mCurrentTabIndex = tabIndex;
        for (int index = 0; index < mTabMenuControllers.size(); index++) {
            mTabMenuControllers.get(index).changeTabStatus(status && index == tabIndex);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus && mFilterController != null) {
            mFilterController.hide(false);
            changeCurrentTabStatus(-1, false);
        }
    }
}
