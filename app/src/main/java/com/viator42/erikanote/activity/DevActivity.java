package com.viator42.erikanote.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.CommonUtils;

import java.util.Calendar;

public class DevActivity extends AppCompatActivity {
    private Button alarmOnceBtn;
    private Button alarmLaterBtn;
    private Button alarmRepeateBtn;
    private AppContext appContext;

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

    }
}
