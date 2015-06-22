package com.meizu.caiweixin.mydemo.filter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterCountBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

/**
 * Created by caiweixin on 5/12/15.
 */
public class FilterListMenu extends FilterBaseMenu {
    private ListView mListView;
    private ListAdapter mAdapter;

    public FilterListMenu(Context context, int tabPosition, FilterMenuBean filterMenuBean) {
        super(context, tabPosition, filterMenuBean);
    }

    @Override
    protected View initContentViewView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_listview, null);
        mListView = (ListView) view.findViewById(R.id.filter_menu_listview);
        mAdapter = new ListAdapter(mContext, mFilterMenuBean.menuList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilterCountBean bean = (FilterCountBean) mFilterMenuBean.menuList.get(position);
                if (null != mMenuOnItemClickListener) {
                    mMenuOnItemClickListener.onItemClick(mTabIndex, bean.id, bean.text);
                }
            }
        });
        return view;
    }
}