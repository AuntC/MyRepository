package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.finalFilterView.filterProvider.FilterProvider;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.menuAdapter.CarAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiweixin on 16/6/16.
 */
public class CarController extends BaseTabMenuController<Car> {

    private TextView textView;
    private GridView gridView;
    private CarAdapter adapter;

    public CarController(Context context) {
        super(context, FilterProvider.FilterType.CAR);
    }

    @Override
    public void doNothingMaybeClose() {
        if (null != mChooseItemListener) {
            mChooseItemListener.doNothingMaybeClose();
        }
    }

    @Override
    public void changeCurrentSelection(Car selectedItem) {
        changeSelectItem(selectedItem);
        adapter.notifyDataSetChanged();
        if (null != mChooseItemListener) {
            mChooseItemListener.onItemChosen(selectedItem);
        }
    }

    @Override
    public View getMenuView(int maxHeight) {
        if (null == mMenuView) {
            mMenuView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_car, new FrameLayout(mContext), false);
            gridView = (GridView) mMenuView.findViewById(R.id.f_menu_car_gv);
            adapter = new CarAdapter(mContext);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setCurrentSelection(position, -1);
                }
            });
            adapter.setDataSet(getDataSet());
            textView = (TextView) mMenuView.findViewById(R.id.f_menu_car_bt);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add 3 more
                    getDataSet().addAll(createCars(adapter.getCount(), 3));
                    adapter.notifyDataSetChanged();
                }
            });
            measureMenuHeight(maxHeight);
        }
        return mMenuView;
    }

    @Override
    public void measureMenuHeight(int maxHeight) {
        mMenuView.getLayoutParams().height = maxHeight;
    }

    public static List<Car> createCars(int startIndex, int size) {
        List<Car> cars = new ArrayList<>();
        Car car;
        for (int i = startIndex; i < startIndex + size; i++) {
            car = new Car("Car-" + i);
            cars.add(car);
        }
        return cars;
    }

    public static CarController createCarController(Context context) {
        CarController controller = new CarController(context);

        //first create 9
        controller.setDataSet(createCars(0, 9));
        controller.setDefaultSelection(0, 0);

        return controller;
    }
}
