package com.viator42.erikanote.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.MainActivity;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.ScheduleAction;
import com.viator42.erikanote.model.Schedule;
import com.viator42.erikanote.receiver.ScheduleReceiver;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.utils.StaticValues;
import com.viator42.erikanote.widget.DateTimePickerDialog;
import com.viator42.erikanote.widget.PickerCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private int feq = StaticValues.FEQ_DAILY;
    private int feqValue;
    private long alarmTime = 0;
    private Schedule schedule = null;
    private int actionType;
    private Spinner feqValueSpinner;
    private TextView whenTextView;
    private ArrayList<Map<String, Object>> hourData;
    private ArrayList<Map<String, Object>> weekData;
    private ArrayList<Map<String, Object>> monthData;
    private SimpleAdapter feqValueSpinnerAdapter;
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
                            changeAlarmTime(dateTimePickerDialog.getTimestamp());
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
                    feq = StaticValues.FEQ_DAILY;
                    whenTextView.setText("几点");
                    feqValueSpinnerAdapter = new SimpleAdapter(InsertScheduleActivity.this, hourData, R.layout.spinner_item, new String[] {"name"}, new int[] {R.id.name});
                    feqValueSpinner.setAdapter(feqValueSpinnerAdapter);
                    feqValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            feqValue = (int) ((Map) feqValueSpinner.getItemAtPosition(i)).get("id");

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });
        weeklyRadioButton = (RadioButton) findViewById(R.id.weekly);
        weeklyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    feq = StaticValues.FEQ_WEEKLY;
                    whenTextView.setText("星期几");
                    feqValueSpinnerAdapter = new SimpleAdapter(InsertScheduleActivity.this, weekData, R.layout.spinner_item, new String[] {"name"}, new int[] {R.id.name});
                    feqValueSpinner.setAdapter(feqValueSpinnerAdapter);
                }
            }
        });
        monthlyRadioButton = (RadioButton) findViewById(R.id.monthly);
        monthlyRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    feq = StaticValues.FEQ_MONTHLY;
                    whenTextView.setText("几号");
                    feqValueSpinnerAdapter = new SimpleAdapter(InsertScheduleActivity.this, monthData, R.layout.spinner_item, new String[] {"name"}, new int[] {R.id.name});
                    feqValueSpinner.setAdapter(feqValueSpinnerAdapter);
                }
            }
        });

        feqValueSpinner = (Spinner) findViewById(R.id.feq_value);
        whenTextView = (TextView) findViewById(R.id.when);

        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirmBtn = (Button) findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(schedule == null)
                {
                    schedule  = new Schedule();
                }
                schedule.name = nameEditText.getText().toString();
                schedule.comment = commentEditText.getText().toString();
                schedule.money = Double.valueOf(moneyEditText.getText().toString());
                schedule.type = type;
                schedule.incomeSpend = incomeSpend;
                schedule.alarmTime = alarmTime;
                switch (type)
                {
                    case StaticValues.TYPE_ONCE:
                        if(schedule.alarmTime < CommonUtils.getCurrentTimestamp())
                        {
                            Snackbar.make(view, "不能设置过去的时间", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        }

                        break;
                    case StaticValues.TYPE_REPEAT:
                        schedule.feq = feq;
                        schedule.feqValue = feqValue;
                        break;
                }

                if(!schedule.insertValidation())
                {
                    Snackbar.make(view, schedule.msg, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                switch (actionType)
                {
                    case StaticValues.ACTION_INSERT:
                        schedule = new ScheduleAction().insert(appContext.eDbHelper, schedule);
                        if(schedule != null && schedule.success)
                        {
                            insertAlarm(schedule);

                            Intent intent = new Intent(InsertScheduleActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("schedule", schedule);
                            intent.putExtras(bundle);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                        else
                        {
                            Snackbar.make(view, "添加失败", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        break;
                    case StaticValues.ACTION_UPDATE:
                        schedule = new ScheduleAction().update(appContext.eDbHelper, schedule);
                        if(schedule != null && schedule.success)
                        {
                            Intent intent = new Intent(InsertScheduleActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("schedule", schedule);
                            intent.putExtras(bundle);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                        else
                        {
                            Snackbar.make(view, "修改失败", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        break;
                }

            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            actionType = bundle.getInt("actionType");
            schedule = bundle.getParcelable("schedule");
        }

        if(schedule != null)
        {
            nameEditText.setText(schedule.name);
            commentEditText.setText(schedule.comment);
            moneyEditText.setText(String.valueOf(schedule.money));
            changeIncomeSpend(schedule.incomeSpend);
            changeScheduleType(schedule.type);
            changeSeq(schedule.feq);
            switch (schedule.type)
            {
                case StaticValues.TYPE_ONCE:
                    alarmTime = schedule.alarmTime;
                    alarmTimeTextView.setText(CommonUtils.timestampToDatetime(schedule.alarmTime));
                    break;
                case StaticValues.TYPE_REPEAT:
                    break;
            }

        }

        hourData = new ArrayList<Map<String, Object>>();
        for(int a=0; a<=23; a++)
        {
            Map line = new HashMap();
            line.put("id", a);
            line.put("name", a);

            hourData.add(line);
        }

        weekData = new ArrayList<Map<String, Object>>();
        for(int a=1; a<=7; a++)
        {
            Map line = new HashMap();
            line.put("id", a);
            line.put("name", a);

            weekData.add(line);
        }

        monthData = new ArrayList<Map<String, Object>>();
        for(int a=1; a<=30; a++)
        {
            Map line = new HashMap();
            line.put("id", a);
            line.put("name", a);

            monthData.add(line);
        }

        onceContainer.setVisibility(View.GONE);
        repeatContainer.setVisibility(View.GONE);
    }

    private void changeIncomeSpend(int incomeSpend)
    {
        this.incomeSpend = incomeSpend;
        switch (incomeSpend)
        {
            case StaticValues.INCOME:
                incomeRadioButton.setChecked(true);
                break;
            case StaticValues.SPEND:
                spendRadioButton.setChecked(true);
                break;
        }
    }

    private void changeScheduleType(int type)
    {
        this.type = type;
        switch (type)
        {
            case StaticValues.TYPE_ONCE:
                onceRadioButton.setChecked(true);
                onceContainer.setVisibility(View.VISIBLE);
                repeatContainer.setVisibility(View.GONE);
                break;

            case StaticValues.TYPE_REPEAT:
                repeatRadioButton.setChecked(true);
                repeatContainer.setVisibility(View.VISIBLE);
                onceContainer.setVisibility(View.GONE);
                break;

        }
    }

    private void changeSeq(int feq)
    {
        this.feq = feq;
        switch (feq)
        {
            case StaticValues.FEQ_DAILY:
                dailyRadioButton.setChecked(true);
                break;
            case StaticValues.FEQ_WEEKLY:
                weeklyRadioButton.setChecked(true);
                break;
            case StaticValues.FEQ_MONTHLY:
                monthlyRadioButton.setChecked(true);
                break;
        }

    }

    protected void changeAlarmTime(long alarmTime)
    {
        this.alarmTime = alarmTime;
        alarmTimeTextView.setText(CommonUtils.timestampToDatetime(alarmTime));
    }

    //添加Alarm
    private void insertAlarm(Schedule schedule)
    {
        Intent intent = new Intent(InsertScheduleActivity.this, ScheduleReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("obj", schedule);
        intent.putExtras(bundle);
        switch (schedule.type)
        {
            case StaticValues.TYPE_ONCE:
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        InsertScheduleActivity.this, (int) schedule.id, intent, PendingIntent.FLAG_ONE_SHOT);
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
    }

    private void setFeqValue()
    {

    }

    //更新Alarm
    private void updateAlarm(Schedule schedule)
    {

    }

}
