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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private String realCode;
    private DBHelper dbHelper;
    private Button register;
    private RelativeLayout top;
    private ImageView back;
    private LinearLayout body;
    private EditText username_1;
    private EditText password_1;
    private EditText password_2;
    private EditText input_code;
    private ImageView verification_code;
    private RelativeLayout agreement;

    private DBManager dbManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializingComponents();

        dbHelper = new DBHelper(this);

//        将验证码用图片的形式显示出来
        verification_code.setImageBitmap(VerificationCode.getInstance().createBitmap());
        realCode = VerificationCode.getInstance().getCode().toLowerCase();
    }

    private void initializingComponents(){
        register = findViewById(R.id.register);
        top = findViewById(R.id.top);
        back = findViewById(R.id.back);
        body = findViewById(R.id.body);
        username_1 = findViewById(R.id.username);
        password_1 = findViewById(R.id.password_1);
        password_2 = findViewById(R.id.password_2);
        input_code = findViewById(R.id.input_code);
        verification_code = findViewById(R.id.verification_code);
        agreement = findViewById(R.id.agreement);

//        返回箭头、刷新验证码图片、注册按钮
        back.setOnClickListener(this);
        verification_code.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
//            返回登录页面
            case R.id.back:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
//            改变随机验证码的生成
            case R.id.verification_code:
                verification_code.setImageBitmap(VerificationCode.getInstance().createBitmap());
                realCode = VerificationCode.getInstance().getCode().toLowerCase();
                break;
//            注册按钮
            case R.id.register:
//                获取用户输入的用户名、密码、验证码
                String username = username_1.getText().toString().trim();
                String password_11 = password_1.getText().toString().trim();
                String password_22 = password_2.getText().toString().trim();
                String inputCode = input_code.getText().toString().toLowerCase();//转化为小写
//                验证非空
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password_22)
                        && !TextUtils.isEmpty(password_11) && !TextUtils.isEmpty(inputCode) ) {
//                    密码校验
                    if (password_11.equals(password_22)){//两个密码是否相同
                        if(isPassword(password_11)) {//密码是否有数字和字母，至少6位
                            if (inputCode.equals(realCode)) {//验证码匹配
//                            将用户名和密码加入到数据库中
                                dbManager = new DBManager(RegisterActivity.this);
                                dbManager.add(username, password_22);

//                                获取系统日期（HH:mm:ss也可以获取时分秒）
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date nowDate = new Date(System.currentTimeMillis());
                                String registerDate = simpleDateFormat.format(nowDate);//Date转换为String
//                                保存用户名，密码，注册日期给下次登录使用
                                sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username",username);
                                editor.putString("password",password_11);
                                editor.putString("date",registerDate);
                                editor.commit();

                                Intent intent2 = new Intent();
                                intent2.setClass(this, MainActivity.class);
                                startActivity(intent2);
                                finish();
                                Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, "密码必须有数字和字母，至少6位", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "密码不同(密码必须有数字和字母，至少6位)", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


//    密码必须含数字和字母，至少6位
    public boolean isPassword(String password){
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$).{6,}$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(password);
        boolean match = m.matches();
        Log.i(TAG, "isPassword: 是否密码正则匹配"+match);
        return match;
    }
}
