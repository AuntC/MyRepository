package com.auntcai.demos.filter.HorizontalTranslate;

import android.app.Activity;
import android.os.Bundle;

import com.auntcai.demos.filter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author caiweixin
 * @since 6/16/16.
 */
public class HTActivity extends Activity {

    HorizontalFlipperView horizontalFlipperView;
    HTAdapter adapter;

    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ht);

        horizontalFlipperView = (HorizontalFlipperView) findViewById(R.id.hf);
        adapter = new HTAdapter(this);
        horizontalFlipperView.setAdapter(adapter);

        List<String> dataSet = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataSet.add("String-" + i);
        }
        adapter.setDataSet(dataSet);
    }
}
