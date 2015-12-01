package com.example.dashline;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        Rect rect = new Rect();

        ColorDrawable dividerDrawable = new ColorDrawable(Color.rgb(0x00, 0x00, 0x00));
        dividerDrawable.getPadding(rect);

        listView.setDivider(dividerDrawable);
        listView.setDividerHeight(2);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText("" + position);
                return textView;
            }
        });
    }
}
