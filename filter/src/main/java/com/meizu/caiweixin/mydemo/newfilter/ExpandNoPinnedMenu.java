package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.meizu.media.life.R;
import com.meizu.media.life.ui.widget.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/12/15.
 */
public class ExpandNoPinnedMenu extends BaseMenuView implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnGroupExpandListener {
    private ExpandableListView listView;
    private ExpandNoPinnedAdapter adapter;

    public ExpandNoPinnedMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context, tabIndex, filterMenuBean);
    }

    @Override
    protected void initContentView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand_no_pinned, null);
        listView = (ExpandableListView) mView.findViewById(R.id.filter_menu_expand);
        adapter = new ExpandNoPinnedAdapter(mContext, mTabIndex, mFilterMenuBean.menuList);
        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(this);
        listView.setOnChildClickListener(this);
//        listView.setOnHeaderUpdateListener(this);
        listView.setOnGroupExpandListener(this);
        listView.expandGroup(0);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        super.setMenuOnItemClickListener(menuOnItemClickListener);
        if (null != adapter) {
            adapter.setMenuOnItemClickListener(mTabIndex, menuOnItemClickListener);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //TODO
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (groupPosition == 0) {
            return true;
        }
        if (parent.isGroupExpanded(groupPosition)) {
            parent.collapseGroup(groupPosition);
        } else {
            parent.expandGroup(groupPosition, false);
        }
        return true;
    }

//    @Override
//    public View getPinnedHeader() {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand_group_pinned_title, null);
//        view.setBackgroundColor(mContext.getResources().getColor(R.color.filter_contentview_color));
//        view.setLayoutParams(new AbsListView.LayoutParams(LifeUtils.getScreenWidth(), AbsListView.LayoutParams.WRAP_CONTENT));
//        return view;
//    }
//
//    @Override
//    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
//        if (adapter.getGroup(firstVisibleGroupPos) != null) {
//            String text = ((FilterExpandBean) adapter.getGroup(firstVisibleGroupPos)).title.getText();
//            boolean isZeroPosition = firstVisibleGroupPos == 0;
//            headerView.findViewById(R.id.filter_pinned_title_container_with_flag).setVisibility(isZeroPosition ? View.VISIBLE : View.INVISIBLE);
//            headerView.findViewById(R.id.filter_pinned_title_text_without_flag).setVisibility(isZeroPosition ? View.INVISIBLE : View.VISIBLE);
//
//            TextView title = (TextView) headerView.findViewById(isZeroPosition ? R.id.filter_pinned_title_text_with_flag : R.id.filter_pinned_title_text_without_flag);
//            title.setText(text);
//
//            ImageView imageView = (ImageView) headerView.findViewById(R.id.filter_pinned_title_expand);
//            imageView.setVisibility(isZeroPosition ? View.INVISIBLE : View.VISIBLE);
//            imageView.setImageResource(listView.isGroupExpanded(firstVisibleGroupPos) ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
//        } else {
//
//        }
//    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //全部为第一个group 点击其他的group不会收起全部
        for (int i = 1; i < adapter.getGroupCount(); i++) {
            if (groupPosition != i && listView.isGroupExpanded(i)) {
                listView.collapseGroup(i);
            }
        }
    }
}
