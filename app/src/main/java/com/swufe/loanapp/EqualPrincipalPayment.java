package com.swufe.loanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//等额本金，每月还的本金固定，而利息越来越少
public class EqualPrincipalPayment extends AppCompatActivity implements View.OnClickListener{
    EditText money;
    EditText yearRate;
    EditText totalMonths;
    EditText month_of_repayment;
    TextView monthly_payments_textView;
    TextView total_interest_textView;
    Button count;

    Double money_1;
    Double yearRate_1;
    int totalMonths_1;
    int month_of_repayment_1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equal_principal_payment);

        initializeView();
    }

    private void initializeView() {
        money = findViewById(R.id.money);
        yearRate = findViewById(R.id.yearRate);
        totalMonths = findViewById(R.id.totalMonths);
        month_of_repayment = findViewById(R.id.month_of_repayment);
        monthly_payments_textView = findViewById(R.id.monthly_payments_textView);
        total_interest_textView = findViewById(R.id.total_interest_textView);
        count = (Button)findViewById(R.id.count);

        count.setOnClickListener(this);
    }

    /**
     * 等额本金类计算
     * @param principal 本金
     * @param monthlyRate 月利率
     * @param totalMonths 还款所需总年份，要*12
     * @param month 还款的第几月
     */
    public void count_1(double principal, double monthlyRate, int totalMonths, int month){
        //每月月供=（贷款本金/还款月数）+（贷款本金-已归还本金累计额）*月利率
        //总利息=（总共所需还款月数+1）×贷款额×月利率/2
        totalMonths = totalMonths * 12;
        monthlyRate = monthlyRate / 1200;
        double hadPaid = (principal / totalMonths * (month - 1));//已归还本金累计额
        double m = (principal / totalMonths) + (principal - hadPaid) * monthlyRate;//每月月供(本金加利息)
        double totalInterest = (totalMonths + 1)*principal*monthlyRate/2;

        monthly_payments_textView.setText("等额本金，第"+month+"月月供额是："+String.format("%.2f", m));
        total_interest_textView.setText("等额本金，总利息是："+String.format("%.2f",totalInterest));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count:
                money_1 = Double.parseDouble(money.getText().toString());
                yearRate_1 = Double.parseDouble(yearRate.getText().toString());
                totalMonths_1 = Integer.parseInt(totalMonths.getText().toString());
                month_of_repayment_1 = Integer.parseInt(month_of_repayment.getText().toString());

                count_1(money_1, yearRate_1, totalMonths_1, month_of_repayment_1);
                break;
            case R.id.back_3:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
