package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterCountBean;

import java.util.List;

/**
 * Created by caiweixin on 5/13/15.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<FilterCountBean> mList;

    public ListAdapter(Context context, List<FilterCountBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.filter_menu_listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindData(viewHolder, mList.get(position));

        return convertView;
    }

    private void bindData(ViewHolder viewHolder, FilterCountBean filterCountBean) {
        viewHolder.name.setText(filterCountBean.text);
        viewHolder.count.setText("" + filterCountBean.count);
    }

    static class ViewHolder {
        TextView name, count;

        ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.listview_item_name);
            count = (TextView) view.findViewById(R.id.listview_item_count);
        }
    }
}
