package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterExpandBean;
import com.meizu.caiweixin.mydemo.filter.view.TagGroup;

import java.util.List;

/**
 * Created by caiweixin on 5/19/15.
 */
public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FilterExpandBean> mList;

    public ExpandAdapter(Context context, List<FilterExpandBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
//        return mList.get(groupPosition).child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).child.get(childPosition);
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
            convertView = mInflater.inflate(R.layout.filter_menu_expand_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.title.setText("" + mList.get(groupPosition).title.getText());
        groupViewHolder.expand.setImageResource(isExpanded ? R.drawable.map_expand_up : R.drawable.map_expand_down);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.filter_menu_expand_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tagGroup.setTags(tags);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView title;
        ImageView expand;

        public GroupViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.group_title);
            expand = (ImageView) view.findViewById(R.id.group_expand);
        }
    }

    static class ChildViewHolder {
        TagGroup tagGroup;

        public ChildViewHolder(View view) {
            tagGroup = (TagGroup) view.findViewById(R.id.taggroup);
        }
    }

    private String[] tags = new String[]{
//            "开心", "开心", "开心", "开心", "开心",
//            "开心", "开心", "开心", "开心", "开心",
//            "开心", "开心", "开心", "开心", "开心",
//            "开心", "开心", "开心", "开心", "开心",
            "开心", "开心", "开心", "开心", "开心",
            "开心", "开心", "开心", "开心", "开心"};
}
