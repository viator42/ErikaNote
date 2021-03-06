package com.viator42.erikanote;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.viator42.erikanote.model.IncomeSpendCategory;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.EDbHelper;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AppContext extends Application {
    public EDbHelper eDbHelper;
    public AlarmManager alarmManager;
    public User user = null;
    public boolean firstOpen = false; //是否首次开启
    public ArrayList<IncomeSpendCategory> incomeSpendCategories;

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库对象
        eDbHelper = new EDbHelper(getApplicationContext(), "erikaNote");
        //alarm对象
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        initDataSet();
    }

    /**
     * 初始化数据集
     */
    private void initDataSet() {
        //类别
        incomeSpendCategories = new ArrayList<IncomeSpendCategory>();
        IncomeSpendCategory incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 1;
        incomeSpendCategory.name = "飞机";
        incomeSpendCategory.icon = R.drawable.ic_category_airplane;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 2;
        incomeSpendCategory.name = "公交";
        incomeSpendCategory.icon = R.drawable.ic_category_bus;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 3;
        incomeSpendCategory.name = "汽车";
        incomeSpendCategory.icon = R.drawable.ic_category_car;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 4;
        incomeSpendCategory.name = "购物";
        incomeSpendCategory.icon = R.drawable.ic_category_shopping;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 5;
        incomeSpendCategory.name = "饮食";
        incomeSpendCategory.icon = R.drawable.ic_category_food;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 6;
        incomeSpendCategory.name = "房租";
        incomeSpendCategory.icon = R.drawable.ic_category_housing;
        incomeSpendCategories.add(incomeSpendCategory);

        incomeSpendCategory = new IncomeSpendCategory();
        incomeSpendCategory.id = 7;
        incomeSpendCategory.name = "电话费";
        incomeSpendCategory.icon = R.drawable.ic_category_phone;
        incomeSpendCategories.add(incomeSpendCategory);

    }


    //浏览器打开特定url
    public void openUrlinBrowser(Context context, String url)
    {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    //添加Alarm
    public void insertAlarm(Context context, Schedule schedule)
    {
        Intent intent = new Intent(context, ScheduleReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("obj", schedule);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, (int) schedule.id, intent, PendingIntent.FLAG_ONE_SHOT);

        switch (schedule.type)
        {
            case StaticValues.TYPE_ONCE:
                //单次提醒
                alarmManager.set(AlarmManager.RTC,
                        schedule.alarmTime,
                        pendingIntent);

                break;

            case StaticValues.TYPE_REPEAT:
                //重复提醒
                switch (schedule.feq)
                {
                    case StaticValues.FEQ_DAILY:
                        alarmManager.set(AlarmManager.RTC,
                                schedule.alarmTime,
                                pendingIntent);
                        break;
                    case StaticValues.FEQ_WEEKLY:
                        alarmManager.set(AlarmManager.RTC,
                                schedule.alarmTime,
                                pendingIntent);
                        break;
                    case StaticValues.FEQ_MONTHLY:
                        alarmManager.set(AlarmManager.RTC,
                                schedule.alarmTime,
                                pendingIntent);
                        break;
                }
                break;
        }
    }

}
