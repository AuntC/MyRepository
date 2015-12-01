package com.meizu.caiweixin.mydemo.newfilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterDistrictChildBean;

import java.util.List;

/**
 * Description:v2.1版本，动态获取商圈菜单；
 *
 * @author caiweixin
 * @since 11/4/15.
 */
public class DistrictChildAdapter extends BaseAdapter {
    private List<FilterDistrictChildBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public DistrictChildAdapter(Context context, List<FilterDistrictChildBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    public void setData(List<FilterDistrictChildBean> list) {
        if (null != list && mList != list) {
            mList = list;
            notifyDataSetChanged();
        }
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
            convertView = mInflater.inflate(R.layout.filter_expand_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindData(viewHolder, mList.get(position));
        return convertView;
    }

    private void bindData(ViewHolder viewHolder, FilterDistrictChildBean childBean) {
        viewHolder.mTextView.setText(childBean.getBusinessCircleName());
        viewHolder.mTextView.setBackground(mContext.getResources().getDrawable(childBean.isSelected() ? R.drawable.oval_shape_pressed : R.drawable.oval_shape_not_pressed));
        viewHolder.mTextView.setTextColor(mContext.getResources().getColor(childBean.isSelected() ? R.color.filter_contentview_color : R.color.filter_text_normal));
    }

    static class ViewHolder {
        TextView mTextView;

        public ViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.gridview_item_expandable);
        }
    }
}
