package com.meizu.caiweixin.calendar;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.util.Log;

/**
 * Created by caiweixin on 5/26/15.
 */
public class CalendarManager {
    private static final String DEBUG_TAG = "CalendarManager";
    private static CalendarManager mInstance;
    private static Context mContext;

    public static void initContext(Context context) {
        mContext = context;
    }

    public static CalendarManager getInstance() {
        if (null == mInstance) {
            mInstance = new CalendarManager();
        }
        return mInstance;
    }

    private static final String CALENDAR_ACCOUNT_NAME = "com.meizu.media.life";
    private static final String CALENDAR_OWNER_ACCOUNT = "com.meizu.media.life";
    private static final String CALENDAR_NAME = "life";
    private static final String CALENDAR_DISPLAY_NAME = "生活服务";

    public interface EventType {
        public static final String COUPON = "0";
        public static final String WELFARE = "1";
    }

    private static final Uri CALENDAR_URI = Calendars.CONTENT_URI;
    private static final String CALENDAR_SELECTION = "(" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?)";
    private static final String[] CALENDAR_SELECTIONARGS = new String[]{CALENDAR_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};

    // 两天前10:00AM提醒
    private static final long SCHEDULE_MILLIS = 2 * 24 * 60 * 60 * 1000;
    private static final int CLOCK_HOUR = 10;
    private static final int CLOCK_MINUTE = 0;

    //提前提醒时间
    private static final int REMINDER_MINUTES = 0;

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] CALENDAR_PROJECTION = new String[]{
            Calendars._ID, // 0
            Calendars.ACCOUNT_NAME, // 1
            Calendars.CALENDAR_DISPLAY_NAME, // 2
            Calendars.OWNER_ACCOUNT // 3
    };

    // The indices for the projection array above.
    private static final int CALENDAR_PROJECTION_ID_INDEX = 0;
    private static final int CALENDAR_PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int CALENDAR_PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int CALENDAR_PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    static Uri asSyncAdapter(Uri uri, String account, String accountType) {
        return uri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(Calendars.ACCOUNT_NAME, account)
                .appendQueryParameter(Calendars.ACCOUNT_TYPE, accountType)
                .build();
    }

    /**
     * 第一次检查生活账号
     */
    public void checkCalendar() {
        if (lifeAccountIsNotExist()) {
            insertCalendar();
        } else {
            updateCalendar();
        }
    }

    /**
     * 生活账号是否不存在
     */
    private boolean lifeAccountIsNotExist() {
        boolean result = true;
        Cursor cursor = queryCalendarCursor();
        if (cursor != null) {
            result = cursor.getCount() <= 0;
            closeCursor(cursor);
        }
        Log.i(DEBUG_TAG, "lifeAccountIsNotExist: " + result);
        return result;
    }

    private Cursor queryCalendarCursor() {
        return getContentResolver().query(CALENDAR_URI, CALENDAR_PROJECTION, CALENDAR_SELECTION, CALENDAR_SELECTIONARGS, null);
    }

    /**
     * 插入生活账号
     */
    private Uri insertCalendar() {
        Log.d(DEBUG_TAG, "insert life account values to calendars table!");
        return getContentResolver().insert(asSyncAdapter(CALENDAR_URI, CALENDAR_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL), getCalendarContentValues());
    }

    /**
     * 更新生活账号
     */
    private void updateCalendar() {
        Log.d(DEBUG_TAG, "update life account values to calendars table!");
        int rows = getContentResolver().update(asSyncAdapter(Calendars.CONTENT_URI, CALENDAR_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL), getCalendarContentValues(), CALENDAR_SELECTION, CALENDAR_SELECTIONARGS);
        Log.i(DEBUG_TAG, "updateCalendar success ! Rows updated: " + rows);
    }

    /**
     * 删除生活账号
     */
    private void deleteCalendar() {
        Log.d(DEBUG_TAG, "delete life account values to calendars table!");
        int rows = getContentResolver().delete(asSyncAdapter(Calendars.CONTENT_URI, CALENDAR_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL), CALENDAR_SELECTION, CALENDAR_SELECTIONARGS);
        Log.i(DEBUG_TAG, "deleteCalendar success ! Rows updated: " + rows);
    }

    private long getCalendarID() {
        long calID = -1;
        Cursor cursor = queryCalendarCursor();
        if (null != cursor) {
            cursor.moveToFirst();
            calID = cursor.getLong(CALENDAR_PROJECTION_ID_INDEX);
            closeCursor(cursor);
        }
        if (calID == -1) {
            //TODO getCalendarID failed!
            Log.i(DEBUG_TAG, "getCalendarID failed!");
        }
        Log.i(DEBUG_TAG, "getCalendarID calID: " + calID);
        return calID;
    }

    private ContentValues getCalendarContentValues() {
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME);
        values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.OWNER_ACCOUNT, CALENDAR_OWNER_ACCOUNT);
        values.put(Calendars.MAX_REMINDERS, 1);
        values.put(Calendars.SYNC_EVENTS, 1);
        values.put(Calendars.CALENDAR_COLOR, getCalendarColor());
        values.put(Calendars.NAME, CALENDAR_NAME);
        values.put(Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_READ);
        return values;
    }

    public void addCalendarEvent(String title, String description, String eventType, long endTime) {
        Calendar beginTime = Calendar.getInstance();
        beginTime.setTimeInMillis(endTime);
        beginTime.set(Calendar.HOUR_OF_DAY, CLOCK_HOUR);
        beginTime.set(Calendar.MINUTE, CLOCK_MINUTE);
        long startMillis = beginTime.getTimeInMillis() - SCHEDULE_MILLIS;

        Uri uri = insertReminder(insertEvent(startMillis, startMillis, title, description, eventType), REMINDER_MINUTES);
        if (null == uri) {
            Log.i(DEBUG_TAG, "addCalendarEvent failed!");
        } else {
            Log.i(DEBUG_TAG, "addCalendarEvent success!");
        }
    }

    private long insertEvent(long startMillis, long endMillis, String title, String description, String hasExtendedProperties) {
        Log.d(DEBUG_TAG, "insertEvent!");
        long calID = getCalendarID();
        if (calID == -1) {
            Log.d(DEBUG_TAG, "insertEvent failed!");
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        values.put(Events.DTEND, endMillis);
        values.put(Events.TITLE, title);
        values.put(Events.DESCRIPTION, description);
        values.put(Events.HAS_EXTENDED_PROPERTIES, hasExtendedProperties);
        values.put(Events.CALENDAR_ID, calID);
        values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        Uri uri = getContentResolver().insert(Events.CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    private Uri insertReminder(long eventID, int minutes) {
        Log.d(DEBUG_TAG, "insertReminder!");
        if (eventID == -1) {
            Log.d(DEBUG_TAG, "insertReminder failed!");
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(Reminders.MINUTES, minutes);
        values.put(Reminders.EVENT_ID, eventID);
        values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        return getContentResolver().insert(Reminders.CONTENT_URI, values);
    }

    public int clearCalendarEvent() {
        long calID = getCalendarID();
        if (calID == -1) {
            Log.d(DEBUG_TAG, "clearCalendarEvent failed: calendar account not exist!");
            return 0;
        }
        int rows = getContentResolver().delete(Events.CONTENT_URI, Events.CALENDAR_ID + " = ? ", new String[]{"" + calID});
        Log.i(DEBUG_TAG, "clearCalendarEvent success ! rows: " + rows);
        return rows;
    }

    private String getCalendarColor() {
        return Integer.toString(mContext.getResources().getColor(R.color.calendar_color));
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    /**
     * 关闭cursor
     */
    private void closeCursor(Cursor cursor) {
        if (cursor != null) {
            if (cursor.isClosed()) {
                Log.d(DEBUG_TAG, "cursor has closed!");
            } else {
                try {
                    cursor.close();
                    Log.i(DEBUG_TAG, "cursor close successful!");
                } catch (Exception e) {
                    Log.e(DEBUG_TAG, "cursor close failed!");
                }
            }
        } else {
            Log.e(DEBUG_TAG, "cursor is null!");
        }
    }
}
