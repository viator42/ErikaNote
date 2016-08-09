package com.viator42.erikanote.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.viator42.erikanote.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/8/8.
 */
public class DateTimePickerDialog extends Dialog {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button nextBtn;
    private Button prevBtn;
    private final static int PROGESS_PICK_DATE = 1;
    private final static int PROGESS_PICK_TIME = 2;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private PickerCompleteListener pickerCompleteListener;

    public DateTimePickerDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        setContentView(R.layout.date_time_picker_dialog);
        setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);

        datePicker = (DatePicker) findViewById(R.id.date_picker);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        prevBtn = (Button) findViewById(R.id.prev);
        nextBtn = (Button) findViewById(R.id.next);

        setProgess(PROGESS_PICK_DATE);
    }

    private void setProgess(int progess)
    {
        switch (progess)
        {
            case PROGESS_PICK_DATE:
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.GONE);

                prevBtn.setText("关闭");
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                nextBtn.setText("设置时间");
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setProgess(PROGESS_PICK_TIME);
                    }
                });

                break;
            case PROGESS_PICK_TIME:
                timePicker.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.GONE);

                prevBtn.setText("设置日期");
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setProgess(PROGESS_PICK_DATE);
                    }
                });
                nextBtn.setText("完成");
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickerCompleteListener.complete();
                        dismiss();
                    }
                });

                break;
        }

    }

    public int getYear() {
        return datePicker.getYear();
    }

    public int getMonth() {
        return datePicker.getMonth();
    }

    public int getDay() {
        return datePicker.getDayOfMonth();
    }

    public int getHour() {
        return timePicker.getCurrentHour();
    }

    public int getMinute() {
        return timePicker.getCurrentMinute();
    }

    public String getDateTimeText()
    {
        return Integer.toString(datePicker.getYear()) + "年"
                + Integer.toString(datePicker.getMonth()) + "月"
                + Integer.toString(datePicker.getDayOfMonth()) + "日"
                + Integer.toString(timePicker.getCurrentHour()) + ":"
                + Integer.toString(timePicker.getCurrentMinute());
    }

    public void setPickerCompleteListener(PickerCompleteListener pickerCompleteListener)
    {
        this.pickerCompleteListener = pickerCompleteListener;
    }

    public Long getTimestamp()
    {
        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(),
                0);
        return calendar.getTimeInMillis();
    }

}
