package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.finalFilterView.filterProvider.FilterProvider;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.menuAdapter.MenuSingleListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author chengsiyu
 * @since 16/6/6.
 */
public class SortController extends BaseTabMenuController<Sort> {

    private ListView mListView;
    private MenuSingleListAdapter<Sort> mAdapter;

    public SortController(Context context) {
        super(context, FilterProvider.FilterType.SORT);
    }

    @Override
    public View getMenuView(int maxHeight) {
        if (null == mMenuView) {
            mMenuView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_list_single, new FrameLayout(mContext), false);
            mListView = (ListView) mMenuView.findViewById(R.id.f_menu_list_single);
            mAdapter = new MenuSingleListAdapter<>(mContext);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setCurrentSelection(position, -1);
                }
            });
            mAdapter.setDataSet(getDataSet());
            measureMenuHeight(maxHeight);
        }
        return mMenuView;
    }

    @Override
    public void measureMenuHeight(int maxHeight) {
        int height = getItemCount() * mContext.getResources().getDimensionPixelSize(R.dimen.filter_menu_list_item_height);
        height = height > maxHeight ? maxHeight : height;
        mMenuView.getLayoutParams().height = height;
    }

    @Override
    public void doNothingMaybeClose() {
        if (null != mChooseItemListener) {
            mChooseItemListener.doNothingMaybeClose();
        }
    }

    @Override
    public void changeCurrentSelection(Sort selectedItem) {
        changeSelectItem(selectedItem);
        mAdapter.notifyDataSetChanged();
        if (null != mChooseItemListener) {
            mChooseItemListener.onItemChosen(selectedItem);
        }
    }

    public static final String ARG_SORT = "arg_sort";

    public static SortController createSortController(Context context, int defaultSortType) {
        //根据视觉,排序筛选条件依次为:离我最近、评分最高、人均最低、人均最高

        SortController controller = new SortController(context);

        List<Sort> sorts = new ArrayList<>();
        Sort sort;

        sort = new Sort(Sort.SortType.LEAST_DISTANCE, context.getString(R.string.search_filter_least_distance));
        sorts.add(sort);

        sort = new Sort(Sort.SortType.HIGHEST_SCORE, context.getString(R.string.search_filter_highest_score));
        sorts.add(sort);

        sort = new Sort(Sort.SortType.LOWEST_PRICE, context.getString(R.string.search_filter_lowest_price));
        sorts.add(sort);

        sort = new Sort(Sort.SortType.HIGHEST_PRICE, context.getString(R.string.search_filter_highest_price));
        sorts.add(sort);

        controller.setDataSet(sorts);

        if (defaultSortType == Sort.SortType.LEAST_DISTANCE) {
            controller.setDefaultSelection(0, 0);
        }
        if (defaultSortType == Sort.SortType.HIGHEST_SCORE) {
            controller.setDefaultSelection(1, 0);
        }
        if (defaultSortType == Sort.SortType.LOWEST_PRICE) {
            controller.setDefaultSelection(2, 0);
        }
        if (defaultSortType == Sort.SortType.HIGHEST_PRICE) {
            controller.setDefaultSelection(3, 0);
        }

        return controller;
    }
}
