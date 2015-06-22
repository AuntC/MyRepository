package com.meizu.caiweixin.mydemo.filter;

/**
 * Created by caiweixin on 5/13/15.
 */
public class FilterConstant {
    public final static String TAG = "filter";

    public interface MenuType {
        public static final int GRID = 1;
        public static final int LIST = 2;
        public static final int EXPANDABLELIST = 3;
    }

    public interface MenuColumns {
        public static final int GRID = 3;
    }

    public interface Height {
        public static final int POPUP_WINDOW = 300;
        public static final int LISTVIEW_ITEM = 56;
        public static final int GRIDVIEW_ITEM = 56;
        public static final int EXPANDVIEW_GROUP = 56;
        public static final int EXPANDVIEW_CHILD = 56;
    }
}
