package com.viator42.erikanote.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import com.viator42.erikanote.R;

/**
 * Created by Administrator on 2016/8/25.
 */
public class TimePickerDialog extends Dialog {
    private TimePicker timePicker;
    private Button confirmBtn;
    private Button cancelBtn;
    private PickerCompleteListener pickerCompleteListener;

    public TimePickerDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //window.setGravity(Gravity.CENTER);
        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        setContentView(R.layout.time_picker_dialog);
        setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);

        timePicker = (TimePicker) findViewById(R.id.time_picker);
        confirmBtn = (Button) findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerCompleteListener.complete();
                dismiss();
            }
        });

        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public int getHour() {
        return timePicker.getCurrentHour();
    }

    public int getMinute() {
        return timePicker.getCurrentMinute();
    }

    public void setPickerCompleteListener(PickerCompleteListener pickerCompleteListener)
    {
        this.pickerCompleteListener = pickerCompleteListener;
    }

}
