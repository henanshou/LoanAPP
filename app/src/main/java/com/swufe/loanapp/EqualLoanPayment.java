package com.swufe.loanapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//等额本息，每月偿还同等数额的贷款(包括本金和利息)
public class EqualLoanPayment extends AppCompatActivity implements View.OnClickListener {
    EditText money;
    EditText yearRate;
    EditText totalMonths;

    TextView monthly_payments_textView;
    TextView total_interest_textView;
    Button count;

    Double money_1;
    Double yearRate_1;
    int totalMonths_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_equal_loan_payment);

        initializeView();
    }

    private void initializeView() {
        money = findViewById(R.id.money_2);
        yearRate = findViewById(R.id.yearRate_2);
        totalMonths = findViewById(R.id.totalMonths_2);

        monthly_payments_textView = findViewById(R.id.monthly_payments_textView_2);
        total_interest_textView = findViewById(R.id.total_interest_textView_2);
        count = findViewById(R.id.count_2);
        count.setOnClickListener(this);
    }

//    等额本息类
    public void count_2(double principal, double yearRate, int totalMonths){
//        月供额=贷款金额*月利率*（1+月利率）^还款月数/[（1+月利率）^还款月数-1]
//        总利息=还款月数*每月供额-贷款本金
//        yearRate是年利率，4.0要除以12再除以100
        yearRate = yearRate / 1200;
        totalMonths = totalMonths * 12;
        double m = principal * (yearRate) * (Math.pow((1 + yearRate), totalMonths)) / ((Math.pow((1 + yearRate), totalMonths)) - 1);
//        取两位小数
        monthly_payments_textView.setText("等额本息，月供额（本金+利息）：" + String.format("%.2f", m));
        total_interest_textView.setText("等额本息，总利息：" + String.format("%.2f",(totalMonths * m - totalMonths)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count_2:
                money_1 = Double.parseDouble(money.getText().toString());
                yearRate_1 = Double.parseDouble(yearRate.getText().toString());
                totalMonths_1 = Integer.parseInt(totalMonths.getText().toString());

                count_2(money_1,yearRate_1,totalMonths_1);
                break;
            case R.id.back_2:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
