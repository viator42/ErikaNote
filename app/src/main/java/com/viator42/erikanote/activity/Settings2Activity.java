package com.viator42.erikanote.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.MainActivity;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.model.User;
import com.viator42.erikanote.utils.CommonUtils;
import com.viator42.erikanote.widget.PickerCompleteListener;
import com.viator42.erikanote.widget.TimePickerDialog;

public class Settings2Activity extends AppCompatActivity {
    private AppContext appContext;
//    private ViewGroup nameContainer;
//    private ViewGroup balanceContainer;
    private ViewGroup defaultAlarmTimeContainer;
    public EditText nameEditText;
    public EditText balanceEditText;
    private TextView defaultAlarmTimeTextView;
    private User user;
    private Button devBtn;
    private TimePickerDialog timePickerDialog = null;
    public Button resetAll;

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

        nameEditText = (EditText) findViewById(R.id.name);
        balanceEditText = (EditText) findViewById(R.id.balance);

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

        resetAll = (Button) findViewById(R.id.reset_all);
        resetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings2Activity.this);
                builder.setTitle("sdf");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        user = new RefAction().getUser(Settings2Activity.this);

        if(!appContext.firstOpen) {
            nameEditText.setText(user.name);
            balanceEditText.setText(String.valueOf(user.balance));
        }

        defaultAlarmTimeTextView.setText(String.valueOf(user.defaultAlarmHour)+":"+String.valueOf(user.defaultAlarmMinute));

    }

    @Override
    public void onBackPressed() {
        //询问是否保存修改
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings2Activity.this);
        builder.setTitle("是否保存设置");
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmChange();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelChange();
            }
        });
        builder.create().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_confirm_change) {
            confirmChange();
            Settings2Activity.this.finish();
            return true;
        }
        if (id == R.id.action_cancel_change) {
            cancelChange();
            Settings2Activity.this.finish();
            return true;
        }
        if(id == android.R.id.home)
        {
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 确认修改
     */
    private void confirmChange() {
        //判定为空
        if(CommonUtils.isValueEmpty(nameEditText.getText().toString())) {
            Toast.makeText(Settings2Activity.this, getResources().getString(R.string.name_not_null), Toast.LENGTH_SHORT).show();
            return;
        }
        if(CommonUtils.isValueEmpty(balanceEditText.getText().toString())) {
            Toast.makeText(Settings2Activity.this, getResources().getString(R.string.balance_not_null), Toast.LENGTH_SHORT).show();
            return;
        }

        user.name = nameEditText.getText().toString();
        user.balance = Double.valueOf(balanceEditText.getText().toString());

        new RefAction().saveSetings(Settings2Activity.this, user);
        appContext.user = user;
    }

    /**
     * 取消修改
     */
    private void cancelChange() {
        Settings2Activity.this.finish();
    }

}
