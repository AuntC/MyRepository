package com.meizu.caiweixin.mydemo.filter.menu;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterExpandBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/12/15.
 */
public class FilterExpandMenu extends FilterBaseMenu implements PinnedHeaderExpandableListView.OnHeaderUpdateListener,
        PinnedHeaderExpandableListView.OnGroupClickListener,
        PinnedHeaderExpandableListView.OnChildClickListener {
    private PinnedHeaderExpandableListView listView;
    private ExpandAdapter adapter;

    public FilterExpandMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context, tabIndex, filterMenuBean);
    }

    @Override
    protected View initContentViewView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand, null);
        listView = (PinnedHeaderExpandableListView) view.findViewById(R.id.filter_menu_expand);
        adapter = new ExpandAdapter(mContext, mFilterMenuBean.menuList);
        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(this);
        listView.setOnChildClickListener(this);
        listView.setOnHeaderUpdateListener(this);

        //默认展开所有子项
        for (int i = 0, count = listView.getCount(); i < count; i++) {
            listView.expandGroup(i);
        }

        return view;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //TODO
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
        if (parent.isGroupExpanded(groupPosition)) {
            parent.collapseGroup(groupPosition);
        } else {
            parent.expandGroup(groupPosition, false);

//            if (mContext instanceof FilterActivity) {
//                ((FilterActivity) mContext).getHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        View view = listView.getChildAt(listView.getFlatListPosition(listView.getPackedPositionForGroup(groupPosition)) + 1);
//                        playAnimator(view);
//                    }
//                }, 0);
//            }
        }
        return true;
    }

    //添加动画
    private Animator mFragmentShow, mFragmentHide;
    private static final int DURATION = 1000;

    private Animator getAnimator(View view) {
        mFragmentShow = ObjectAnimator.ofFloat(view, "TranslationY", -view.getHeight(), 0.0f).setDuration(DURATION);
//        mFragmentHide = ObjectAnimator.ofFloat(view, "TranslationX", 0.0f, (float) -getScreenWidth()).setDuration(DURATION);
        return mFragmentShow;
    }

    public void playAnimator(View view) {
        getAnimator(view).start();
    }

    public int getScreenWidth() {
        if (mContext == null)
            return 0;
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
    }

    @Override
    public View getPinnedHeader() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand_group, null);
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return view;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        FilterExpandBean groupBean = (FilterExpandBean) adapter.getGroup(firstVisibleGroupPos);
        TextView title = (TextView) headerView.findViewById(R.id.group_title);
        title.setText(groupBean.title.getText());
        ImageView imageView = (ImageView) headerView.findViewById(R.id.group_expand);
        imageView.setImageResource(listView.isGroupExpanded(firstVisibleGroupPos) ? R.drawable.map_expand_up : R.drawable.map_expand_down);
    }
}
