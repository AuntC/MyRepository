package com.auntcai.demos.filter.finalFilterView.filterProvider;

import android.content.Context;
import android.os.Bundle;

import com.auntcai.demos.filter.finalFilterView.FilterView;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.DistanceController;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.Sort;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.SortController;

import rx.Observable;
import rx.Subscriber;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/15/16.
 */
public class FilterProvider {

    public interface FilterType {
//        int DISTRICT = 1;
//        int CATEGORY = 2;
        int SORT = 3;
        int DISTANCE = 4;
        int CAR = 5;
    }

    public static Observable<FilterView.ITabMenuController> init(final Context context, final Bundle args, final int... filterTypes) {
        return Observable.create(new Observable.OnSubscribe<FilterView.ITabMenuController>() {
            @Override
            public void call(Subscriber<? super FilterView.ITabMenuController> subscriber) {

                for (int filterType : filterTypes) {
                    switch (filterType) {
                        case FilterType.SORT:
                            if (null != args) {
                                subscriber.onNext(SortController.createSortController(context, args.getInt(SortController.ARG_SORT, Sort.SortType.LEAST_DISTANCE)));
                            }
                            break;
                        case FilterType.DISTANCE:
                            subscriber.onNext(DistanceController.createDistanceController(context));
                            break;
                        default:
                            break;
                    }
                }
                subscriber.onCompleted();
            }
        });
    }
}
