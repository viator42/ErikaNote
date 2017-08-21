package com.viator42.erikanote.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.action.RefAction;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.model.IncomeSpendCategory;
import com.viator42.erikanote.utils.StaticValues;

import java.util.ArrayList;

public class InsertIncomeSpendActivity extends AppCompatActivity {
    private AppContext appContext;
    private EditText commentEditText;
    private EditText moneyEditText;
    private RadioButton incomeRadioButton;
    private RadioButton spendRadioButton;
    private Button confirmBtn;
    private Button cancelBtn;
    private int type;
    private IncomeSpend incomeSpend = null;
    private ProgressDialog progressDialog;
    private ArrayList<IncomeSpendCategory> incomeSpendCategories;
    private RadioGroup incomeSpendCategoriesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_income_spend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            incomeSpend = bundle.getParcelable("incomeSpend");
        }

        appContext = (AppContext) getApplicationContext();
        commentEditText = (EditText) findViewById(R.id.comment);
        moneyEditText = (EditText) findViewById(R.id.money);
        incomeRadioButton = (RadioButton) findViewById(R.id.income);
        incomeRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    type = StaticValues.INCOME;
                }
            }
        });
        spendRadioButton = (RadioButton) findViewById(R.id.spend);
        spendRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    type = StaticValues.SPEND;
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
        confirmBtn = (Button) findViewById(R.id.confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBtn.setEnabled(false);
                try
                {
                    IncomeSpend incomeSpend = new IncomeSpend();
                    incomeSpend.comment = commentEditText.getText().toString();
                    incomeSpend.money = Double.valueOf(moneyEditText.getText().toString());
                    incomeSpend.incomeSpend = type;
                    incomeSpend.type = StaticValues.TYPE_ONCE;

                    if(!incomeSpend.insertValidation(InsertIncomeSpendActivity.this))
                    {
                        Toast.makeText(InsertIncomeSpendActivity.this, incomeSpend.msg, Toast.LENGTH_SHORT).show();
                        confirmBtn.setEnabled(true);
                        return;
                    }

                    new IncomeSpendAction().insert(appContext.eDbHelper, incomeSpend);
                    new RefAction().balanceChange(InsertIncomeSpendActivity.this, incomeSpend.incomeSpend, incomeSpend.money);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    confirmBtn.setEnabled(true);
                }

                finish();
            }
        });
        type = StaticValues.INCOME;
        changeIncomeSpend(type);

        if(incomeSpend != null)
        {
            commentEditText.setText(incomeSpend.comment);
            moneyEditText.setText(String.valueOf(incomeSpend.money));
            switch (incomeSpend.incomeSpend)
            {
                case StaticValues.INCOME:
                    incomeRadioButton.setSelected(true);
                    break;
                case StaticValues.SPEND:
                    spendRadioButton.setSelected(true);
                    break;
            }
        }

        incomeSpendCategoriesContainer = (RadioGroup) findViewById(R.id.categories);

    }

    @Override
    protected void onStart() {
        super.onStart();

        incomeSpendCategories = appContext.incomeSpendCategories;
        for(IncomeSpendCategory incomeSpendCategory: incomeSpendCategories) {
            final RadioButton radioButton = new RadioButton(InsertIncomeSpendActivity.this);
            radioButton.setButtonDrawable(null);
            radioButton.setText(incomeSpendCategory.name);
//            radioButton.setBackgroundResource(incomeSpendCategory.icon);
            radioButton.setPadding(16, 8, 16, 8);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);  // , 1是可选写的
            lp.setMargins(16, 8, 16, 8);
            radioButton.setLayoutParams(lp);

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        radioButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else {
                        radioButton.setBackgroundColor(getResources().getColor(R.color.text_white));
                    }

                }
            });
            incomeSpendCategoriesContainer.addView(radioButton);

        }

    }

    private void changeIncomeSpend(int incomeSpend)
    {
        this.type = incomeSpend;
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

}
