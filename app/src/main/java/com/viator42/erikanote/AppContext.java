package com.viator42.erikanote;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.EDbHelper;
import com.viator42.erikanote.utils.StaticValues;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AppContext extends Application {
    public EDbHelper eDbHelper;
    public AlarmManager alarmManager;
    public User user;
    public boolean firstOpen = false;   //第一次启动

    @Override
    public void onCreate() {
        super.onCreate();

        //数据库对象
        eDbHelper = new EDbHelper(getApplicationContext(), "erikaNote");
        //alarm对象
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

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
