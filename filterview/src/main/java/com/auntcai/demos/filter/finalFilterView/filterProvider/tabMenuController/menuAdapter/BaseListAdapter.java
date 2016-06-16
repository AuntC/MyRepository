package com.auntcai.demos.filter.finalFilterView.filterProvider.tabMenuController.menuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by caiweixin on 6/11/15.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mDataSet;

    public BaseListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public T getItem(int position) {
        if (mDataSet == null || position < 0 || position >= getCount()) {
            return null;
        } else {
            return mDataSet.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDataSet() {
        return mDataSet;
    }

    public void setDataSet(List<T> dataList) {
        if (null != dataList && mDataSet != dataList) {
            mDataSet = dataList;
        }
        notifyDataSetChanged();
    }
}
