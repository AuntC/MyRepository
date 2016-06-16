package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.menuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.auntcai.demos.filter.R;
import com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.Car;

/**
 * Created by caiweixin on 16/6/16.
 */
public class CarAdapter extends BaseListAdapter<Car> {

    public CarAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.filter_menu_car_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Car item = getItem(position);
        viewHolder.textView.setText(item.getMenuShowText());
        viewHolder.textView.setTextColor(mContext.getResources().getColor(item.isSelected() ? R.color.mz_theme_color_firebrick : R.color.color_black));
        return convertView;
    }

    static class ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.f_menu_car_item_tv);
        }
    }
}
