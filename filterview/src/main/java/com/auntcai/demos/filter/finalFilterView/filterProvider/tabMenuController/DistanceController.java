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
 * @author caiweixin
 * @since 6/15/16.
 */
public class DistanceController extends BaseTabMenuController<Distance> {

    private ListView mListView;
    private MenuSingleListAdapter<Distance> mAdapter;

    public DistanceController(Context context) {
        super(context, FilterProvider.FilterType.DISTANCE);
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
    public void changeCurrentSelection(Distance selectedItem) {
        changeSelectItem(selectedItem);
        mAdapter.notifyDataSetChanged();
        if (null != mChooseItemListener) {
            mChooseItemListener.onItemChosen(selectedItem);
        }
    }

    public static DistanceController createDistanceController(Context context) {
        DistanceController controller = new DistanceController(context);

        List<Distance> distances = new ArrayList<>();
        Distance distance;

        distance = new Distance("1000", "1千米");
        distances.add(distance);

        distance = new Distance("3000", "3千米");
        distances.add(distance);

        distance = new Distance("5000", "5千米");
        distances.add(distance);

        distance = new Distance("10000", "10千米");
        distances.add(distance);

        controller.setDataSet(distances);
        controller.setDefaultSelection(0, 0);

        return controller;
    }
}
