package com.viator42.erikanote.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.widget.PickerCompleteListener;
import com.viator42.erikanote.widget.TimePickerDialog;

public class Settings2Activity extends AppCompatActivity {
    private AppContext appContext;
    private ViewGroup nameContainer;
    private ViewGroup balanceContainer;
    private ViewGroup defaultAlarmTimeContainer;
    private TextView nameTextView;
    private TextView balanceTextView;
    private TextView defaultAlarmTimeTextView;
    private User user;
    private Button devBtn;
    private TimePickerDialog timePickerDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        appContext = (AppContext) getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setSubtitle(getResources().getString(R.string.settings_subtitle));

        nameContainer = (ViewGroup) findViewById(R.id.name_container);
        nameContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameEditText = new EditText(Settings2Activity.this);
                nameEditText.setText(user.name);
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings2Activity.this);
                builder.setView(nameEditText);
                builder.setTitle("姓名");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = nameEditText.getText().toString();
                        if(!CommonUtils.isValueEmpty(name))
                        {
                            user.name = name;
                            nameTextView.setText(name);
                            dialogInterface.dismiss();
                        }
                        else
                        {
                            Toast.makeText(Settings2Activity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

        balanceContainer = (ViewGroup) findViewById(R.id.balance_container);
        balanceContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText balanceEditText = new EditText(Settings2Activity.this);
                balanceEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings2Activity.this);
                builder.setView(balanceEditText);
                builder.setTitle("余额");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String balance = balanceEditText.getText().toString();
                        if(!CommonUtils.isValueEmpty(balance))
                        {
                            user.balance = Double.valueOf(balance);
                            balanceTextView.setText(balance);
                            dialogInterface.dismiss();
                        }
                        else
                        {
                            Toast.makeText(Settings2Activity.this, "余额不能为空", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });
        nameTextView = (TextView) findViewById(R.id.name);
        balanceTextView = (TextView) findViewById(R.id.balance);

        defaultAlarmTimeContainer = (ViewGroup) findViewById(R.id.default_alarm_time_container);
        defaultAlarmTimeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timePickerDialog == null)
                {
                    timePickerDialog = new TimePickerDialog(Settings2Activity.this);
                    timePickerDialog.setPickerCompleteListener(new PickerCompleteListener() {
                        @Override
                        public void complete() {
                            defaultAlarmTimeTextView.setText(timePickerDialog.getHour()+":"+timePickerDialog.getMinute());
                            user.defaultAlarmHour = timePickerDialog.getHour();
                            user.defaultAlarmMinute = timePickerDialog.getMinute();
                        }
                    });
                }
                timePickerDialog.show();
            }
        });
        defaultAlarmTimeTextView = (TextView) findViewById(R.id.default_alarm_time);

        devBtn = (Button) findViewById(R.id.dev);
        devBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings2Activity.this, DevActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        user = appContext.user;
        nameTextView.setText(user.name);
        balanceTextView.setText(String.valueOf(user.balance));
        defaultAlarmTimeTextView.setText(String.valueOf(user.defaultAlarmHour)+":"+String.valueOf(user.defaultAlarmMinute));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //setuser
        new RefAction().setUser(Settings2Activity.this, user);
        appContext.user = user;

    }
}
