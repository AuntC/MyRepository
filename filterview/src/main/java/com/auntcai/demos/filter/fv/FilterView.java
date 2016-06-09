package com.auntcai.demos.filter.fv;

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

    private IFilterController mFilterController;
    //    private TabAdapter mAdapter;
    private List<ITabMenuController> mTabMenuControllers;

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
    }

    public void setFilterController(IFilterController filterController) {
        if (null == filterController) {
            throw new IllegalArgumentException("The filterController is null!");
        }
        this.mFilterController = filterController;
        this.mFilterController.setAnchor(FilterView.this);
    }

//    public void setAdapter(TabAdapter adapter) {
//        if (null == adapter) {
//            throw new IllegalArgumentException("The adapter is null!");
//        }
//        int tabCount = adapter.getTabMenuCount();
//        if (tabCount < ITab.MIN_TAB_COUNT || tabCount > ITab.MAX_TAB_COUNT) {
//            throw new IllegalArgumentException("Total itemCount must in " + ITab.MIN_TAB_COUNT + "~" + ITab.MAX_TAB_COUNT);
//        }
//        this.mAdapter = adapter;
//
//        invalidateTabViews();
//    }

    public void setmTabMenuControllers(List<ITabMenuController> mTabMenuControllers) {
        this.mTabMenuControllers = mTabMenuControllers;
        invalidateTabViews();
    }

    private void invalidateTabViews() {
        FilterView.this.removeAllViews();
        for (int position = 0; position < mTabMenuControllers.size(); position++) {
            if (position > 0) {
                //add divider view
                addView(LayoutInflater.from(mContext).inflate(R.layout.filter_tab_divider, FilterView.this, false));
            }

            final ITabMenuController tabMenuController = mTabMenuControllers.get(position);
            final int finalPosition = position;
            tabMenuController.onCreateTabView(FilterView.this);
            tabMenuController.onCreateMenuView(mFilterController.getMaxContentViewHeight());
            mFilterController.addTabChangeListener(new IFilterController.TabChangeListener() {
                @Override
                public void tabStatusChanged(int tabIndex, boolean tabStatus) {
                    if (tabIndex == finalPosition) {
                        tabMenuController.changeTabStatus(tabStatus);
                    } else {
                        tabMenuController.changeTabStatus(false);
                    }
                }
            });
            tabMenuController.getTabView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFilterController.onChangeStatus(tabMenuController.getMenuView(), finalPosition);
                }
            });

            //add tab view
            addView(tabMenuController.getTabView(), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    public void setTabSelected(int tabId, boolean willSelected) {

    }

//    interface Status {
//        boolean UN_SELECT = false;
//        boolean SELECT = true;
//    }

    public interface IFilterController {
        void setAnchor(View anchor);

        int getMaxContentViewHeight();

        void onChangeStatus(View view, int tabIndex);

        interface TabChangeListener {
            void tabStatusChanged(int tabIndex, boolean tabStatus);
        }

        void addTabChangeListener(TabChangeListener listener);

        void notifyTabs(int tabIndex, boolean tabStatus);

        boolean onBackPressed();
    }

    public interface ITabMenuController<D> extends ITab, IMenu {
        void setSelectedData(D d);

        D getSelectedData();
    }

    public interface ITab {
        //Tab 阈值
        int MIN_TAB_COUNT = 1;
        int MAX_TAB_COUNT = 4;

        void onCreateTabView(ViewGroup parent);

        View getTabView();

        boolean getTabStatus();

        void changeTabStatus(boolean tabStatus);
    }

    public interface IMenu {
        void onCreateMenuView(int maxHeight);

        View getMenuView();
    }

    public static class TabAdapter<TM extends ITabMenuController> {
        protected List<TM> tabs;

//        public abstract TabHolder onCreateTabHolder(ViewGroup parent, int position);
//
//        public abstract void onBindTabHolder(TabHolder holder, int position);

//        public abstract TM onCreateTabMenu(ViewGroup parent, int position);

        public void setTabs(List<TM> tabs) {
            this.tabs = tabs;
        }

        public int getTabMenuCount() {
            return tabs.size();
        }

        public TM getTabMenu(int position) {
            return tabs.get(position);
        }
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

    public boolean onBackPressed() {
        return mFilterController.onBackPressed();
    }

    public interface IFilterTab {
        void setSelected(boolean willSelect);
        View getFilterContentView();
    }
}
