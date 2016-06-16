package com.auntcai.demos.filter.HorizontalTranslate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.auntcai.demos.filter.R;

import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/16/16.
 */
public class HTAdapter extends HorizontalFlipperView.Adapter<HTViewHolder> {
    private Context mContext;

    private List<String> mDataSet;

    public HTAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataSet(List<String> dataSet) {
        if (null != dataSet && dataSet != mDataSet) {
            this.mDataSet = dataSet;
            notifyDataSetChanged();
        }
    }

    @Override
    public HTViewHolder onCreateViewHolder(ViewGroup parent) {
        return new HTViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ht_item, parent, false));
    }

    @Override
    public void onBindViewHolder(HTViewHolder holder, int position) {
        holder.bindData(getItemData(position));
    }

    @Override
    public int getItemCount() {
        return null == mDataSet ? 0 : mDataSet.size();
    }

    @Override
    public String getItemData(int position) {
        return null == mDataSet ? "" : mDataSet.get(position);
    }
}
