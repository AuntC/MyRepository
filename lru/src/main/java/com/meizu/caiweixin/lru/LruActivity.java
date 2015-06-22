package com.meizu.caiweixin.lru;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class LruActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView a, b, c, d, e, f, g, h, i, j, textView;

    private SearchManager mSearchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru);

        a = (TextView) findViewById(R.id.textview_a);
        b = (TextView) findViewById(R.id.textview_b);
        c = (TextView) findViewById(R.id.textview_c);
        d = (TextView) findViewById(R.id.textview_d);
        e = (TextView) findViewById(R.id.textview_e);
        f = (TextView) findViewById(R.id.textview_f);
        g = (TextView) findViewById(R.id.textview_g);
        h = (TextView) findViewById(R.id.textview_h);
        i = (TextView) findViewById(R.id.textview_i);
        j = (TextView) findViewById(R.id.textview_j);

        textView = (TextView) findViewById(R.id.textview_result);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);
        j.setOnClickListener(this);

        mSearchManager = SearchManager.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_a:
                mSearchManager.addHistory("a");
                showResult();
                break;
            case R.id.textview_b:
                mSearchManager.addHistory("b");
                showResult();
                break;
            case R.id.textview_c:
                mSearchManager.addHistory("c");
                showResult();
                break;
            case R.id.textview_d:
                mSearchManager.addHistory("d");
                showResult();
                break;
            case R.id.textview_e:
                mSearchManager.addHistory("e");
                showResult();
                break;
            case R.id.textview_f:
                mSearchManager.addHistory("f");
                showResult();
                break;
            case R.id.textview_g:
                mSearchManager.addHistory("g");
                showResult();
                break;
            case R.id.textview_h:
                mSearchManager.addHistory("h");
                showResult();
                break;
            case R.id.textview_i:
                mSearchManager.addHistory("i");
                showResult();
                break;
            case R.id.textview_j:
                mSearchManager.addHistory("j");
                showResult();
                break;
            default:
                break;
        }
    }

    private void showResult() {
        String result = "";
        for (String s : mSearchManager.getHistory()) {
            result = result + s;
        }
        textView.setText(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
