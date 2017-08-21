package com.viator42.erikanote.action;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.model.Statistics;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.EDbHelper;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IncomeSpendAction {
    /**
     * 添加
     * @param eDbHelper
     * @param incomeSpend
     * @return
     */
    public IncomeSpend insert(EDbHelper eDbHelper, IncomeSpend incomeSpend)
    {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("category", incomeSpend.category);
            contentValues.put("comment", incomeSpend.comment);
            contentValues.put("money", incomeSpend.money);
            contentValues.put("income_spend", incomeSpend.incomeSpend);
            contentValues.put("type", incomeSpend.type);
            contentValues.put("create_time", CommonUtils.getCurrentTimestamp());

            SQLiteDatabase sqLiteDatabase = eDbHelper.getWritableDatabase();
            incomeSpend.id = sqLiteDatabase.insert("income_spend", null, contentValues);
            incomeSpend.success = true;

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return incomeSpend;
    }

    //查询 指定 incomeSpend条件
    public ArrayList<IncomeSpend> list(EDbHelper eDbHelper, IncomeSpend param)
    {
        ArrayList<IncomeSpend> result = new ArrayList<IncomeSpend>();

        SQLiteDatabase sqLiteDatabase = eDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from income_spend where income_spend=? limit ?,?",
                new String[]{Integer.toString(param.incomeSpend), Integer.toString(param.min), Integer.toString(param.max)});
        while(cursor.moveToNext()) {
            IncomeSpend incomeSpend = new IncomeSpend();
            incomeSpend.id = cursor.getLong(cursor.getColumnIndex("id"));
            incomeSpend.category = cursor.getInt(cursor.getColumnIndex("category"));
            incomeSpend.comment = cursor.getString(cursor.getColumnIndex("comment"));
            incomeSpend.money = cursor.getDouble(cursor.getColumnIndex("money"));
            incomeSpend.incomeSpend = cursor.getInt(cursor.getColumnIndex("income_spend"));
            incomeSpend.type = cursor.getInt(cursor.getColumnIndex("type"));
            incomeSpend.createTime = cursor.getLong(cursor.getColumnIndex("create_time"));

            result.add(incomeSpend);
        }
        sqLiteDatabase.close();

        return result;
    }

    //统计
    public Statistics statistics(EDbHelper eDbHelper)
    {
        //统计当天本周本月的总数 余额
        Statistics statistics = new Statistics();
        SQLiteDatabase sqLiteDatabase = eDbHelper.getReadableDatabase();

        //当天
        Cursor cursor = sqLiteDatabase.rawQuery("select * from income_spend where create_time>? and create_time<?",
                new String[]{Long.toString(CommonUtils.getDayStart()), Long.toString(CommonUtils.getDayEnds())});
        while(cursor.moveToNext()) {
            switch (cursor.getInt(cursor.getColumnIndex("income_spend")))
            {
                case StaticValues.INCOME:
                    statistics.incomeToday += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
                case StaticValues.SPEND:
                    statistics.spendToday += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
            }
        }

        //本周
        cursor = sqLiteDatabase.rawQuery("select * from income_spend where create_time>? and create_time<?",
                new String[]{Long.toString(CommonUtils.getTimesWeekStart()), Long.toString(CommonUtils.getTimesWeekEnds())});
        while(cursor.moveToNext()) {
            switch (cursor.getInt(cursor.getColumnIndex("income_spend")))
            {
                case StaticValues.INCOME:
                    statistics.incomeWeekly += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
                case StaticValues.SPEND:
                    statistics.spendWeekly += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
            }
        }

        //本月
        cursor = sqLiteDatabase.rawQuery("select * from income_spend where create_time>? and create_time<?",
                new String[]{Long.toString(CommonUtils.getMonthStart()), Long.toString(CommonUtils.getMonthEnds())});
        while(cursor.moveToNext()) {
            switch (cursor.getInt(cursor.getColumnIndex("income_spend")))
            {
                case StaticValues.INCOME:
                    statistics.incomeMonthly += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
                case StaticValues.SPEND:
                    statistics.spendMonthly += cursor.getDouble(cursor.getColumnIndex("money"));
                    break;
            }
        }

        sqLiteDatabase.close();
        return statistics;

    }

    //清除所有
    public void clearAll(EDbHelper eDbHelper)
    {

    }
}
