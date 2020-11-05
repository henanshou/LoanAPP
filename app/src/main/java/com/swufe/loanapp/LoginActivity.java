package com.swufe.loanapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private DBHelper dbHelper;
    private TextView register_1;
    private RelativeLayout top_1;
    private EditText username_1;
    private EditText password_1;
    private LinearLayout LinearLayout_1;
    private Button login_1;

    private DBManager dbManager;
    SharedPreferences sharedPreferences;
    private String name;
    private String password;
    int flag = 0;//不为0则可以不输入用户名、密码

    SimpleDateFormat simpleDateFormat;
    Date nowDate;
    String string;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializingComponents();

        dbHelper = new DBHelper(this);

//        获取系统日期（HH:mm:ss也可以获取时分秒）
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = new Date(System.currentTimeMillis());
        string = simpleDateFormat.format(nowDate);//Date转换为String

        sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        String pastDate = sharedPreferences.getString("date", "");
        try {
            Date pastDate_2 = simpleDateFormat.parse(pastDate);//String转为Date
//            超过7天无需再次登录，直接读取sharedPreferences中的用户名密码
            if (nowDate.getTime() - pastDate_2.getTime() < 7 * 24 * 60 * 60 * 1000) {
                Log.i(TAG, "handleMessage: 不超过7天无需再次登录");

                name = sharedPreferences.getString("username", "");
                password = sharedPreferences.getString("password", "");

                username_1.setText(name);
                password_1.setText(password);

                dbManager = new DBManager(LoginActivity.this);
                ArrayList<User> data = dbManager.getAllData();
                boolean match = false;
                for (int i = 0; i < data.size(); i++) {
                    User user = data.get(i);
                    if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                        match = true;
                        break;
                    } else {
                        match = false;
                    }
                }
                if (match) {
                    Toast.makeText(this, "欢迎使用！", Toast.LENGTH_SHORT).show();
                    flag++;
                } else {
                    Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //    绑定组件，添加监听
    private void initializingComponents() {
        login_1 = (Button)findViewById(R.id.login_1);//登录按钮
        register_1 = (TextView) findViewById(R.id.register_1);//可点击的注册文本
        top_1 = (RelativeLayout)findViewById(R.id.top_1);
        username_1 = (EditText)findViewById(R.id.username_1);
        password_1 = (EditText)findViewById(R.id.password_1);
        LinearLayout_1 = (LinearLayout)findViewById(R.id.LinearLayout_1);

        login_1.setOnClickListener(this);
        register_1.setOnClickListener(this);
    }

    //    通过id判断点击的按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            跳转到注册界面
            case R.id.register_1:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
//            登录按钮
            case R.id.login_1:
                if(flag > 0){//不超过7天
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                    Intent intent_1 = new Intent();
                    intent_1.setClass(this, MainActivity.class);
                    startActivity(intent_1);
                    finish();//销毁LoginActivity
                }else {//超过7天，需重新登录，登录后更新登录日期进user.xml
                    name = username_1.getText().toString().trim();
                    password = password_1.getText().toString().trim();
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                        dbManager = new DBManager(LoginActivity.this);
                        ArrayList<User> data = dbManager.getAllData();
                        boolean match = false;
                        for (int i = 0; i < data.size(); i++) {
                            User user = data.get(i);
                            if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                                match = true;
                                break;
                            } else {
                                match = false;
                            }
                        }
                        if (match) {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username",name);
                            editor.putString("password",password);
                            editor.putString("date", string);
                            editor.commit();
                            Log.i(TAG, "user.xml中登录日期已经更新");

                            Intent intent_1 = new Intent();
                            intent_1.setClass(this, MainActivity.class);
                            startActivity(intent_1);
                            finish();//销毁此Activity
                        } else {
                            Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
