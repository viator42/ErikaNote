package com.viator42.erikanote.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.MainActivity;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.action.ScheduleAction;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.utils.CommonUtils;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ScheduleReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        Bundle bundle = intent.getExtras();
        Schedule schedule = bundle.getParcelable("obj");

        //弹窗提醒
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        mBuilder.setSmallIcon(R.drawable.icon);
        mBuilder.setContentTitle(schedule.name);
        if(!CommonUtils.isValueEmpty(schedule.comment))
        {
            mBuilder.setContentText(schedule.comment);
        }
//        mBuilder.setTicker("setTicker");//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber((int) schedule.id);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//                mBuilder.setLargeIcon(btm);
        mBuilder.setAutoCancel(true);//自己维护通知的消失

        Intent resultIntent = new Intent(context, MainActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        //删除schedule
        new ScheduleAction().remove(appContext.eDbHelper, schedule.id);

        //添加incomeSpend
        IncomeSpend incomeSpend = new IncomeSpend();
        incomeSpend.name = schedule.name;
        incomeSpend.comment = schedule.comment;
        incomeSpend.incomeSpend = schedule.incomeSpend;
        incomeSpend.money = schedule.money;
        incomeSpend.createTime = CommonUtils.getCurrentTimestamp();
        new IncomeSpendAction().insert(appContext.eDbHelper, incomeSpend);

        //余额计算
        new RefAction().balanceChange(context, incomeSpend.incomeSpend, incomeSpend.money);
    }
}
