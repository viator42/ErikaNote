package com.viator42.erikanote.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.viator42.erikanote.R;
import com.viator42.erikanote.widget.DateTimePickerDialog;
import com.viator42.erikanote.widget.PickerCompleteListener;

public class InsertScheduleActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText commentEditText;
    private EditText moneyEditText;
    private TextView alarmTimeTextView;
    private Button alarmTimeSetBtn;
    private DateTimePickerDialog dateTimePickerDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





    }

}
