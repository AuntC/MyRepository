package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.common.widget.PinnedHeaderListView;
import com.meizu.media.life.R;
import com.meizu.media.life.ui.widget.filter.FilterConstant;
import com.meizu.media.life.ui.widget.filter.bean.FilterBaseBean;
import com.meizu.media.life.ui.widget.filter.bean.FilterExpandBean;
import com.meizu.media.life.ui.widget.filter.menu.GridChildAdapter;
import com.meizu.media.life.ui.widget.filter.view.NoScrollGridView;

import java.util.List;

/**
 * Created by caiweixin on 5/19/15.
 */
public class ExpandNoPinnedAdapter extends BaseExpandableListAdapter implements PinnedHeaderListView.PinnedHeaderAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int mTabIndex;
    private List<FilterExpandBean> mList;
    private int mCurrentGroupPosition, mCurrentChildPosition;
    private TextView mSelectedItem;
    private BaseMenuView.MenuOnItemClickListener mMenuOnItemClickListener;

    public ExpandNoPinnedAdapter(Context context, int tabIndex, List<FilterExpandBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mTabIndex = tabIndex;
        mCurrentGroupPosition = mCurrentChildPosition = 0;
    }

    @Override
    public int getGroupCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mList == null || groupPosition < 0 || groupPosition >= getGroupCount()) {
            return null;
        } else {
            return mList.get(groupPosition);
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mList == null || groupPosition < 0 || groupPosition >= getGroupCount() || mList.get(groupPosition).child == null) {
            return null;
        } else {
            return mList.get(groupPosition).child.get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.filter_menu_expand_group_with_divider, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
//        if (groupPosition == 0) {
//            groupViewHolder.groupLine.setVisibility(View.VISIBLE);
//        } else {
//            groupViewHolder.groupLine.setVisibility(View.GONE);
//        }
        groupViewHolder.groupLine.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        groupViewHolder.divider.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        groupViewHolder.title.setText("" + mList.get(groupPosition).title.getText());
        groupViewHolder.expand.setVisibility(groupPosition == 0 ? View.GONE : View.VISIBLE);
        groupViewHolder.expand.setImageResource(isExpanded ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.filter_expand_no_pinned_gridview, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.headBlank.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        childViewHolder.footBlank.setVisibility(groupPosition != 0 ? View.VISIBLE : View.GONE);

        GridChildAdapter gridChildAdapter;
        if (childViewHolder.noScrollGridView.getAdapter() != null) {
            gridChildAdapter = (GridChildAdapter) childViewHolder.noScrollGridView.getAdapter();
            gridChildAdapter.setData(mList.get(groupPosition).child);
        } else {
            gridChildAdapter = new GridChildAdapter(mContext, mList.get(groupPosition).child);
            childViewHolder.noScrollGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            childViewHolder.noScrollGridView.setAdapter(gridChildAdapter);
            childViewHolder.noScrollGridView.setNumColumns(FilterConstant.MenuColumns.GRID);
        }
        childViewHolder.noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                setCurrentPosition(groupPosition, position);
                notifyDataSetChanged();
                if (null != mMenuOnItemClickListener) {
                    FilterBaseBean bean = mList.get(groupPosition).child.get(position);
                    mMenuOnItemClickListener.onFilterMenuItemClick(mTabIndex, bean);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView title;
        ImageView expand;
        View groupLine;
        View divider;

        public GroupViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.group_title);
            expand = (ImageView) view.findViewById(R.id.group_expand);
            groupLine = view.findViewById(R.id.filter_title_line);
            divider = view.findViewById(R.id.group_divider);
        }
    }

    static class ChildViewHolder {
        NoScrollGridView noScrollGridView;
        View headBlank, footBlank;

        public ChildViewHolder(View view) {
            noScrollGridView = (NoScrollGridView) view.findViewById(R.id.filter_expand_gridview);
            headBlank = view.findViewById(R.id.filter_expand_head_blank);
            footBlank = view.findViewById(R.id.filter_expand_foot_blank);
        }
    }


    @Override
    public int getPinnedHeaderCount() {
        return getGroupCount();
    }

    @Override
    public View getPinnedHeaderView(int viewIndex, View convertView, ViewGroup parent) {
        return getGroupView(viewIndex, false, convertView, parent);
    }

    @Override
    public void configurePinnedHeaders(PinnedHeaderListView listView) {

    }

    @Override
    public int getScrollPositionForHeader(int viewIndex) {
        return viewIndex;
    }

    public void setMenuOnItemClickListener(int tabIndex, BaseMenuView.MenuOnItemClickListener menuOnItemClickListener) {
        this.mTabIndex = tabIndex;
        this.mMenuOnItemClickListener = menuOnItemClickListener;
    }

    private void setCurrentPosition(int groupPosition, int childPosition) {
        if (mCurrentGroupPosition != -1 || mCurrentChildPosition != -1) {
            mList.get(mCurrentGroupPosition).child.get(mCurrentChildPosition).isSelected = false;
        }
        mList.get(groupPosition).child.get(childPosition).isSelected = true;
        mCurrentGroupPosition = groupPosition;
        mCurrentChildPosition = childPosition;
    }

}
