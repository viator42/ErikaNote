package com.viator42.erikanote.activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.MainActivity;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;
import com.viator42.erikanote.widget.ErikaWidget;

import java.util.Calendar;

public class DevActivity extends AppCompatActivity {
    private Button alarmOnceBtn;
    private Button alarmLaterBtn;
    private Button alarmRepeateBtn;
    private Button cancelAlarmBtn;
    private Button notificationBtn;
    private Button addTestDataBtn;
    private Button clearAllData;
    private AppContext appContext;
    private ErikaWidget erikaWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        appContext = (AppContext) getApplicationContext();

        alarmLaterBtn = (Button) findViewById(R.id.alarm_later);
        alarmLaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置alarm
                Calendar cal = CommonUtils.getTimeAfterInSecs(10);
                Intent intent =  new Intent(DevActivity.this, ScheduleReceiver.class);
                Bundle bundle = new Bundle();
                bundle.putString("msg", "this is an instance message");
                intent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(DevActivity.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
                appContext.alarmManager.set(AlarmManager.RTC,
                        cal.getTimeInMillis(),
                        pendingIntent);

            }
        });

        alarmOnceBtn = (Button) findViewById(R.id.alarm_once);
        alarmOnceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        alarmRepeateBtn = (Button) findViewById(R.id.alarm_repeate);
        alarmRepeateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = CommonUtils.getTimeAfterInSecs(10);
                Intent intent = new Intent(DevActivity.this, ScheduleReceiver.class);

                PendingIntent pi = PendingIntent.getBroadcast(DevActivity.this,1, intent, 0);
                // Schedule the alarm!
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                am.setRepeating(AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(),
                        5*1000, //5 secs
                        pi);
            }
        });

        cancelAlarmBtn = (Button) findViewById(R.id.cancel_alarm);
        cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(DevActivity.this, ScheduleReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(DevActivity.this, 2, intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.cancel(pi);

            }
        });

        notificationBtn = (Button) findViewById(R.id.notification);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        DevActivity.this);
                mBuilder.setSmallIcon(R.drawable.ic_menu_camera);
                mBuilder.setContentTitle("Title");
                mBuilder.setContentText("Content");
                mBuilder.setTicker("setTicker");//第一次提示消息的时候显示在通知栏上
                mBuilder.setNumber(12);
                mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//                mBuilder.setLargeIcon(btm);
                mBuilder.setAutoCancel(true);//自己维护通知的消失

                Intent resultIntent = new Intent(DevActivity.this, MainActivity.class);
                //封装一个Intent
                PendingIntent resultPendingIntent = PendingIntent.getActivity(
                        DevActivity.this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // 设置通知主题的意图
                mBuilder.setContentIntent(resultPendingIntent);
                //获取通知管理器对象
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

            }
        });

        addTestDataBtn = (Button) findViewById(R.id.add_test_data);
        addTestDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int a=0; a<1000; a++)
                {
                    IncomeSpend incomeSpend = new IncomeSpend();
                    incomeSpend.name = "TEST_DATA_"+a;
                    incomeSpend.comment = "TEST_DATA_COMMENT"+a;
                    incomeSpend.money = a;
                    incomeSpend.incomeSpend = StaticValues.INCOME;
                    if((a%2) == 0)
                    {
                        incomeSpend.type = StaticValues.TYPE_ONCE;
                    }
                    else
                    {
                        incomeSpend.type = StaticValues.SPEND;
                    }

                    new IncomeSpendAction().insert(appContext.eDbHelper, incomeSpend);
                    new RefAction().balanceChange(DevActivity.this, incomeSpend.incomeSpend, incomeSpend.money);

                }
            }
        });

        clearAllData = (Button) findViewById(R.id.clear_all_data);
        clearAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        erikaWidget = (ErikaWidget) findViewById(R.id.erika_widget);
        erikaWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DevActivity.this, "sssssssssss", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
