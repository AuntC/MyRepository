package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterBaseBean;

import java.util.List;

/**
 * Created by caiweixin on 5/12/15.
 */
public class GridAdapter extends BaseAdapter {
    private List<FilterBaseBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public GridAdapter(Context context, List<FilterBaseBean> list) {
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
            convertView = mInflater.inflate(R.layout.filter_menu_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindData(viewHolder, mList.get(position));

        return convertView;
    }

    private void bindData(ViewHolder viewHolder, FilterBaseBean filterBaseBean) {
        viewHolder.mTextView.setText(filterBaseBean.text);
        viewHolder.mTextView.setTextColor(mContext.getResources().getColor(filterBaseBean.isSelected ? R.color.filter_text_selected : R.color.filter_text_normal));
    }

    static class ViewHolder {
        TextView mTextView;

        public ViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.gridview_item_tv);
        }
    }
}
