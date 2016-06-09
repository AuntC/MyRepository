package com.auntcai.demos.filter;

import android.app.Activity;
import android.os.Bundle;

import com.auntcai.demos.filter.filterview.FilterView;
import com.auntcai.demos.filter.filterview.menuViews.Menu1;
import com.auntcai.demos.filter.filterview.tab.ITab;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/8/16.
 */
public class FilterViewActivity extends Activity {

    private FilterView filterView;
    private FTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterview);

        filterView = (FilterView) findViewById(R.id.filterview);
        adapter = new FTabAdapter(this);

        List<MyTab> data = new ArrayList<>();
        MyTab tmp;
        Menu1 tMenuView;
        for (int i = 0; i < 4; i++) {
            tmp = new MyTab(this, i);
            tmp.setStatus(ITab.Status.CLOSE);
            tMenuView = new Menu1(this);
            tMenuView.setText("container-" + i);
            tmp.setMenuView(tMenuView);
            data.add(tmp);
        }
        adapter.setDataSet(data);
        filterView.setAdapter(adapter);
    }
}
