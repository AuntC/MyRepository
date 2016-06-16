package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.menuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.finalFilterView.FilterView;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/12/16.
 */
public class MenuSingleListAdapter<T extends FilterView.IFilterItem> extends BaseAdapter {

    private Context mContext;

    private List<T> mDataSet;

    public MenuSingleListAdapter(Context context) {
        mContext = context;
    }

    public void setDataSet(List<T> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mDataSet ? 0 : mDataSet.size();
    }

    @Override
    public T getItem(int position) {
        return null == mDataSet ? null : mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_list_single_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        viewHolder.textView.setText(item.getMenuShowText());
        viewHolder.textView.setTextColor(mContext.getResources().getColor(item.isSelected() ? R.color.mz_theme_color_firebrick : R.color.color_black));
        viewHolder.imageView.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.f_menu_list_single_item_tv);
            imageView = (ImageView) itemView.findViewById(R.id.f_menu_list_single_item_iv);
        }
    }
}
