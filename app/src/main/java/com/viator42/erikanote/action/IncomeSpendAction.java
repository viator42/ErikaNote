package com.viator42.erikanote.action;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.EDbHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class IncomeSpendAction {
    public IncomeSpend insert(EDbHelper eDbHelper, IncomeSpend incomeSpend)
    {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", incomeSpend.name);
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
        Cursor cursor = sqLiteDatabase.rawQuery("select * from income_spend where income_spend=?",
                new String[]{Integer.toString(param.incomeSpend)});
        while(cursor.moveToNext()) {
            IncomeSpend incomeSpend = new IncomeSpend();
            incomeSpend.id = cursor.getLong(cursor.getColumnIndex("id"));
            incomeSpend.name = cursor.getString(cursor.getColumnIndex("name"));
            incomeSpend.comment = cursor.getString(cursor.getColumnIndex("comment"));
            incomeSpend.money = cursor.getDouble(cursor.getColumnIndex("comment"));
            incomeSpend.incomeSpend = cursor.getInt(cursor.getColumnIndex("income_spend"));
            incomeSpend.type = cursor.getInt(cursor.getColumnIndex("type"));
            incomeSpend.createTime = cursor.getLong(cursor.getColumnIndex("create_time"));

            result.add(incomeSpend);
        }
        sqLiteDatabase.close();

        return result;
    }

}
