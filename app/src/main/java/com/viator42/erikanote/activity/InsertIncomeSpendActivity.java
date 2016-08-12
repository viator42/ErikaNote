package com.viator42.erikanote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.viator42.erikanote.AppContext;
import com.viator42.erikanote.R;
import com.viator42.erikanote.action.IncomeSpendAction;
import com.viator42.erikanote.model.IncomeSpend;
import com.viator42.erikanote.utils.StaticValues;

public class InsertIncomeSpendActivity extends AppCompatActivity {
    private AppContext appContext;
    private EditText nameEditText;
    private EditText commentEditText;
    private EditText moneyEditText;
    private RadioButton incomeRadioButton;
    private RadioButton spendRadioButton;
    private Button confirmBtn;
    private Button cancelBtn;
    private int type;
    private IncomeSpend incomeSpend = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_income_spend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            incomeSpend = bundle.getParcelable("incomeSpend");
        }

        appContext = (AppContext) getApplicationContext();
        nameEditText = (EditText) findViewById(R.id.name);
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
                IncomeSpend incomeSpend = new IncomeSpend();
                incomeSpend.name = nameEditText.getText().toString();
                incomeSpend.comment = commentEditText.getText().toString();
                incomeSpend.money = Double.valueOf(moneyEditText.getText().toString());
                incomeSpend.incomeSpend = type;
                incomeSpend.type = StaticValues.TYPE_ONCE;
                new IncomeSpendAction().insert(appContext.eDbHelper, incomeSpend);

                finish();
            }
        });
        type = StaticValues.INCOME;
        if(incomeSpend != null)
        {
            nameEditText.setText(incomeSpend.name);
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

    }

}
