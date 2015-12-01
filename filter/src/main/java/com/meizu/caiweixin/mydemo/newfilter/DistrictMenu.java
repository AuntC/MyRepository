package com.meizu.caiweixin.mydemo.newfilter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.meizu.caiweixin.mydemo.R;
import com.meizu.caiweixin.mydemo.filter.bean.FilterDistrictBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterDistrictChildBean;
import com.meizu.caiweixin.mydemo.filter.bean.FilterMenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:v2.1版本，动态获取商圈菜单；
 *
 * @author caiweixin
 * @since 11/4/15.
 */
public class DistrictMenu extends BaseMenuView implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnGroupExpandListener {

    private ExpandableListView listView;
    private DistrictAdapter adapter;

    public DistrictMenu(Context context, int tabIndex, FilterMenuBean filterMenuBean) {
        super(context, tabIndex, filterMenuBean);
    }

    @Override
    protected void initContentView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_expand_no_pinned, null);
        listView = (ExpandableListView) mView.findViewById(R.id.filter_menu_expand);
        adapter = new DistrictAdapter(mContext, mTabIndex, mFilterMenuBean.menuList);
        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(this);
        listView.setOnChildClickListener(this);
        listView.setOnGroupExpandListener(this);
        listView.expandGroup(0);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void setMenuOnItemClickListener(MenuOnItemClickListener menuOnItemClickListener) {
        super.setMenuOnItemClickListener(menuOnItemClickListener);
        if (null != adapter) {
            adapter.setMenuOnItemClickListener(mTabIndex, menuOnItemClickListener);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
        if (groupPosition == 0) {
            return true;
        }
        if (parent.isGroupExpanded(groupPosition)) {
            parent.collapseGroup(groupPosition);
        } else {
            FilterDistrictBean bean = (FilterDistrictBean) mFilterMenuBean.menuList.get(groupPosition);
            if (FilterDistrictBean.needLoad(bean)) {
//                DataManager.getInstance().requestBusinessCircle((Activity) mContext, bean.getRegionCode(), new ResponseCallback<List<String>>() {
//                    @Override
//                    public void onSuccess(boolean fromCache, List<String> data) {
//                        FilterDistrictBean filterDistrictBean = (FilterDistrictBean) mFilterMenuBean.menuList.get(groupPosition);
//                        List<FilterDistrictChildBean> childBeans = new ArrayList<FilterDistrictChildBean>();
//                        childBeans.addAll(filterDistrictBean.getFilterDistrictChildBean());
//                        FilterDistrictChildBean childBean;
//                        for (String s : data) {
//                            childBean = new FilterDistrictChildBean(filterDistrictBean.getRegionCode(), filterDistrictBean.getRegionName(), s, false, false);
//                            childBeans.add(childBean);
//                        }
//                        filterDistrictBean.setStatus(FilterDistrictBean.Status.LOAD_SUCCESS);
//                        filterDistrictBean.setFilterDistrictChildBean(childBeans);
//                        LifeUtils.getMainThreadHandler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(int errorCode, String message, boolean hasCache) {
//                        FilterDistrictBean filterDistrictBean = (FilterDistrictBean) mFilterMenuBean.menuList.get(groupPosition);
//                        filterDistrictBean.setStatus(FilterDistrictBean.Status.LOAD_FAILED);
//                    }
//                });
            }
            parent.expandGroup(groupPosition, false);
        }
        return true;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        //全部为第一个group 点击其他的group不会收起全部
        for (int i = 1; i < adapter.getGroupCount(); i++) {
            if (groupPosition != i && listView.isGroupExpanded(i)) {
                listView.collapseGroup(i);
            }
        }
    }
}
