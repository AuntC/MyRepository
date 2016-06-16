package com.auntcai.demos.filter.fv;

import android.app.Activity;
import android.os.Bundle;

import com.auntcai.demos.filter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/8/16.
 */
public class FVActivity extends Activity {

    private FilterView filterView;
    private PopController controller;
    private FVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fv);

        filterView = (FilterView) findViewById(R.id.filterview);
        controller = new PopController(this);
        filterView.setFilterController(controller);

        List<TMController> data = new ArrayList<>();
        TMController tmpController;
        for (int i = 0; i < 3; i++) {
            tmpController = new TMController(this);
            data.add(tmpController);
        }
        adapter = new FVAdapter(this);
        adapter.setTabs(data);

    }

    @Override
    public void onBackPressed() {
        if (filterView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
