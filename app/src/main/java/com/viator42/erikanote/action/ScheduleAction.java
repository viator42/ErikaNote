package com.viator42.erikanote.action;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.EDbHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ScheduleAction {
    public Schedule insert(EDbHelper eDbHelper, Schedule schedule)
    {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", schedule.type);
            contentValues.put("money", schedule.money);
            contentValues.put("name", schedule.name);
            contentValues.put("comment", schedule.comment);
            contentValues.put("create_time", CommonUtils.getCurrentTimestamp());
            contentValues.put("feq", schedule.feq);
            contentValues.put("feq_value", schedule.feqValue);
            contentValues.put("alarm_time", schedule.alarmTime);

            SQLiteDatabase sqLiteDatabase = eDbHelper.getWritableDatabase();
            schedule.id = sqLiteDatabase.insert("schedule", null, contentValues);
            schedule.success = true;

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return schedule;
    }

    public ArrayList<Schedule> list(EDbHelper eDbHelper)
    {
        ArrayList<Schedule> result = new ArrayList<Schedule>();

        SQLiteDatabase sqLiteDatabase = eDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from schedule", null);
        while(cursor.moveToNext()) {
            Schedule schedule = new Schedule();
            schedule.id = cursor.getLong(cursor.getColumnIndex("id"));
            schedule.type = cursor.getInt(cursor.getColumnIndex("type"));
            schedule.money = cursor.getDouble(cursor.getColumnIndex("money"));
            schedule.name = cursor.getString(cursor.getColumnIndex("name"));
            schedule.comment = cursor.getString(cursor.getColumnIndex("comment"));
            schedule.createTime = cursor.getLong(cursor.getColumnIndex("create_time"));
            schedule.feq = cursor.getInt(cursor.getColumnIndex("feq"));
            schedule.feqValue = cursor.getInt(cursor.getColumnIndex("feq_value"));
            schedule.alarmTime = cursor.getInt(cursor.getColumnIndex("alarm_time"));

            result.add(schedule);
        }
        sqLiteDatabase.close();

        return result;
    }

    public boolean remove(EDbHelper eDbHelper, int id)
    {
        return false;
    }

}
