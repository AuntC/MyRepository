package com.example.testtextviewandtags;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by caiweixin on 7/16/15.
 */
public class MyActivity extends Activity implements View.OnClickListener {

    TextView textView, left, right;

    ImageView imageView;

    private static final String text = "100元";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        left = (TextView) findViewById(R.id.left);
        right = (TextView) findViewById(R.id.right);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        textView.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                textView.setText(text);
                break;
            case R.id.right:
                textView.setText(textView.getText() + text);
                break;
            default:
                break;
        }
    }
}
