package com.meizu.caiweixin.calendar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarActivity extends ActionBarActivity implements View.OnClickListener {

    private CalendarManager mCalendarManager;

    private TextView b1, b2, b3;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarManager = CalendarManager.getInstance();

        b1 = (TextView) findViewById(R.id.b1);
        b2 = (TextView) findViewById(R.id.b2);
        b3 = (TextView) findViewById(R.id.b3);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        calendar = Calendar.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                mCalendarManager.checkCalendar();
//                mCalendarManager.insertCalendar();
                break;
            case R.id.b2:
                long currentTimeMillis = System.currentTimeMillis();
                calendar.setTimeInMillis(currentTimeMillis);

                mCalendarManager.addCalendarEvent("一张价值5.0元的优惠券就快到期啦",new SimpleDateFormat("有效期至：yyyy年MM月dd日E HH:mm").format(calendar.getTime()), CalendarManager.EventType.WELFARE, currentTimeMillis);
//                mCalendarManager.updateCalendar();
                break;
            case R.id.b3:
                mCalendarManager.clearCalendarEvent();
//                mCalendarManager.deleteCalendar();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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
