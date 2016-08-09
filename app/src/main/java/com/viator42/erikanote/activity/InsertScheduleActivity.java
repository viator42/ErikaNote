package com.viator42.erikanote.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.ScheduleAction;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;
import com.viator42.erikanote.widget.DateTimePickerDialog;
import com.viator42.erikanote.widget.PickerCompleteListener;

import java.util.Calendar;

public class InsertScheduleActivity extends AppCompatActivity {
    private AppContext appContext;
    private EditText nameEditText;
    private EditText commentEditText;
    private EditText moneyEditText;
    private TextView alarmTimeTextView;
    private Button alarmTimeSetBtn;
    private DateTimePickerDialog dateTimePickerDialog = null;
    private ViewGroup onceContainer;
    private ViewGroup repeatContainer;
    private RadioButton incomeRadioButton;
    private RadioButton spendRadioButton;
    private RadioButton onceRadioButton;
    private RadioButton repeatRadioButton;
    private RadioButton dailyRadioButton;
    private RadioButton weeklyRadioButton;
    private RadioButton monthlyRadioButton;
    private Button cancelBtn;
    private Button confirmBtn;
    private int incomeSpend = StaticValues.INCOME;
    private int type = StaticValues.TYPE_ONCE;
    private int feq = StaticValues.DAILY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appContext = (AppContext) getApplicationContext();
        nameEditText = (EditText) findViewById(R.id.name);
        commentEditText = (EditText) findViewById(R.id.comment);
        moneyEditText = (EditText) findViewById(R.id.money);
        alarmTimeTextView = (TextView) findViewById(R.id.alarm_time);
        alarmTimeSetBtn = (Button) findViewById(R.id.alarm_time_set);
        alarmTimeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateTimePickerDialog == null)
                {
                    dateTimePickerDialog = new DateTimePickerDialog(InsertScheduleActivity.this);
                    dateTimePickerDialog.setPickerCompleteListener(new PickerCompleteListener() {
                        @Override
                        public void complete() {
                            alarmTimeTextView.setText(dateTimePickerDialog.getDateTimeText());
                        }
                    });
                }
                dateTimePickerDialog.show();

            }
        });

        onceContainer = (ViewGroup) findViewById(R.id.once_container);
        repeatContainer = (ViewGroup) findViewById(R.id.repeat_container);
        incomeRadioButton = (RadioButton) findViewById(R.id.income);
        incomeRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    incomeSpend = StaticValues.INCOME;
                }
            }
        });
        spendRadioButton = (RadioButton) findViewById(R.id.spend);
        spendRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    incomeSpend = StaticValues.SPEND;
                }
            }
        });
        onceRadioButton = (RadioButton) findViewById(R.id.once);
        onceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    changeScheduleType(StaticValues.TYPE_ONCE);
                }
            }
        });
        repeatRadioButton = (RadioButton) findViewById(R.id.repeat);
        repeatRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    changeScheduleType(StaticValues.TYPE_REPEAT);
                }
            }
        });
        dailyRadioButton = (RadioButton) findViewById(R.id.daily);
        dailyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    feq = StaticValues.DAILY;
                }
            }
        });
        weeklyRadioButton = (RadioButton) findViewById(R.id.weekly);
        weeklyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    feq = StaticValues.WEEKLY;
                }
            }
        });
        monthlyRadioButton = (RadioButton) findViewById(R.id.monthly);
        monthlyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    feq = StaticValues.MONTHLY;
                }
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule schedule = new Schedule();
                schedule.name = nameEditText.getText().toString();
                schedule.comment = commentEditText.getText().toString();
                schedule.money = Double.valueOf(moneyEditText.getText().toString());
                schedule.type = type;
                switch (type)
                {
                    case StaticValues.TYPE_ONCE:
                        schedule.alarmTime = dateTimePickerDialog.getTimestamp();

                        break;
                    case StaticValues.TYPE_REPEAT:
                        schedule.feq = feq;

                        break;

                }

                if(!schedule.insertValidation())
                {
                    Snackbar.make(view, schedule.msg, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                //添加Alarm
                Intent intent = new Intent(InsertScheduleActivity.this, ScheduleReceiver.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", schedule);
                intent.putExtras(bundle);
                switch (schedule.type)
                {
                    case StaticValues.TYPE_ONCE:
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                InsertScheduleActivity.this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
                        appContext.alarmManager.set(AlarmManager.RTC,
                                schedule.alarmTime,
                                pendingIntent);

                        break;

                    case StaticValues.TYPE_REPEAT:
//                        PendingIntent pi = PendingIntent.getBroadcast(DevActivity.this,1, intent, 0);
//                        // Schedule the alarm!
//                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//                        am.setRepeating(AlarmManager.RTC_WAKEUP,
//                                cal.getTimeInMillis(),
//                                5*1000, //5 secs
//                                pi);

                        break;
                }

                schedule = new ScheduleAction().insert(appContext.eDbHelper, schedule);
                if(schedule != null && schedule.success)
                {
                    finish();
                }
                else
                {
                    Snackbar.make(view, "添加失败", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    private void changeScheduleType(int type)
    {
        this.type = type;
        switch (type)
        {
            case StaticValues.TYPE_ONCE:
                onceContainer.setVisibility(View.VISIBLE);
                repeatContainer.setVisibility(View.GONE);
                break;

            case StaticValues.TYPE_REPEAT:
                repeatContainer.setVisibility(View.VISIBLE);
                onceContainer.setVisibility(View.GONE);
                break;

        }
    }

}
