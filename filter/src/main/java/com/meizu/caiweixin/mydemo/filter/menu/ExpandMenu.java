package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
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
public class ExpandMenu extends BaseMenu implements PinnedHeaderExpandableListView.OnHeaderUpdateListener,
        PinnedHeaderExpandableListView.OnGroupClickListener,
        PinnedHeaderExpandableListView.OnChildClickListener,
        PinnedHeaderExpandableListView.OnGroupExpandListener {
    private PinnedHeaderExpandableListView listView;
    private ExpandAdapter adapter;

    public ExpandMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context, tabIndex, filterMenuBean);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected View initContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand, null);
        listView = (PinnedHeaderExpandableListView) view.findViewById(R.id.filter_menu_expand);
        adapter = new ExpandAdapter(mContext, mTabIndex, mFilterMenuBean.menuList);
        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(this);
        listView.setOnChildClickListener(this);
        listView.setOnHeaderUpdateListener(this);
        listView.setOnGroupExpandListener(this);
        listView.expandGroup(0);
        return view;
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

    @Override
    public View getPinnedHeader() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand_group, null);
        view.setBackgroundColor(mContext.getResources().getColor(R.color.filter_contentview_color));
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return view;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        if (adapter.getGroup(firstVisibleGroupPos) != null) {
            FilterExpandBean groupBean = (FilterExpandBean) adapter.getGroup(firstVisibleGroupPos);
            TextView title = (TextView) headerView.findViewById(R.id.group_title);
            title.setText(groupBean.title.getText());
            ImageView imageView = (ImageView) headerView.findViewById(R.id.group_expand);
            imageView.setVisibility(firstVisibleGroupPos == 0 ? View.INVISIBLE : View.VISIBLE);
            imageView.setImageResource(listView.isGroupExpanded(firstVisibleGroupPos) ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
        } else {

        }
    }

    @Override
    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        super.setMenuOnItemClickListener(menuOnItemClickListener);
        if (adapter != null) {
            adapter.setMenuOnItemClickListener(mTabIndex, menuOnItemClickListener);
        }
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //全部为第一个group 点击其他的group不会收起全部
        for (int i = 1; i < adapter.getGroupCount(); i++) {
            if (groupPosition != i) {
                listView.collapseGroup(i);
            }
        }
    }
}
