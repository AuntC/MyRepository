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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.filter.bean.FilterDistrictBean;

import java.util.List;

/**
 * Description:v2.1版本，动态获取商圈菜单；
 *
 * @author caiweixin
 * @since 11/4/15.
 */
public class DistrictAdapter extends BaseExpandableListAdapter implements PinnedHeaderListView.PinnedHeaderAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int mTabIndex;
    private List<FilterDistrictBean> mList;
    private int mCurrentGroupPosition, mCurrentChildPosition;
    private BaseMenuView.MenuOnItemClickListener mMenuOnItemClickListener;

    public DistrictAdapter(Context context, int tabIndex, List<FilterDistrictBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mTabIndex = tabIndex;
        mCurrentGroupPosition = mCurrentChildPosition = 0;
    }

    public void setData(List<FilterDistrictBean> list) {
        if (null != list && mList != list) {
            mList = list;
            notifyDataSetChanged();
        }
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
        if (mList == null || groupPosition < 0 || groupPosition >= getGroupCount() || mList.get(groupPosition).getFilterDistrictChildBean() == null) {
            return null;
        } else {
            return mList.get(groupPosition).getFilterDistrictChildBean().get(childPosition);
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
            convertView = mInflater.inflate(R.layout.filter_menu_expand_group_with_divider, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.groupLine.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        groupViewHolder.divider.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
        groupViewHolder.title.setText("" + mList.get(groupPosition).getRegionName());
        groupViewHolder.expand.setVisibility(groupPosition == 0 ? View.GONE : View.VISIBLE);
        groupViewHolder.expand.setImageResource(isExpanded ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.filter_dynamic_business_circle_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        FilterDistrictBean filterDistrictBean = mList.get(groupPosition);
        switch (filterDistrictBean.getStatus()) {
            case FilterDistrictBean.Status.NOT_LOADED:
            case FilterDistrictBean.Status.LOADING:
            case FilterDistrictBean.Status.LOAD_SUCCESS:
            case FilterDistrictBean.Status.LOAD_FAILED:

                childViewHolder.headBlank.setVisibility(groupPosition == 0 ? View.VISIBLE : View.GONE);
                childViewHolder.footBlank.setVisibility(groupPosition != 0 ? View.VISIBLE : View.GONE);

                DistrictChildAdapter gridChildAdapter;
                if (childViewHolder.noScrollGridView.getAdapter() != null) {
                    gridChildAdapter = (DistrictChildAdapter) childViewHolder.noScrollGridView.getAdapter();
                    gridChildAdapter.setData(mList.get(groupPosition).getFilterDistrictChildBean());
                } else {
                    gridChildAdapter = new DistrictChildAdapter(mContext, mList.get(groupPosition).getFilterDistrictChildBean());
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
                            FilterDistrictChildBean bean = mList.get(groupPosition).getFilterDistrictChildBean().get(position);
                            mMenuOnItemClickListener.onFilterMenuItemClick(mTabIndex, bean);
                        }
                    }
                });
                break;
        }
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
        //        LoadingView loadingView;
        LinearLayout container;

        public ChildViewHolder(View view) {
            noScrollGridView = (NoScrollGridView) view.findViewById(R.id.filter_expand_gridview);
            headBlank = view.findViewById(R.id.filter_expand_head_blank);
            footBlank = view.findViewById(R.id.filter_expand_foot_blank);
            container = (LinearLayout) view.findViewById(R.id.container);
//            loadingView = (LoadingView) view.findViewById(R.id.loading);
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
            mList.get(mCurrentGroupPosition).getFilterDistrictChildBean().get(mCurrentChildPosition).setIsSelected(false);
        }
        mList.get(groupPosition).getFilterDistrictChildBean().get(childPosition).setIsSelected(true);
        mCurrentGroupPosition = groupPosition;
        mCurrentChildPosition = childPosition;
    }

}
