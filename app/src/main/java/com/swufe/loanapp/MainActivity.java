package com.swufe.loanapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity  extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private static final String TAG = "MainActivity";

    String product_1 = "个人消费贷款";
    String product_2 = "个人汽车贷款";
    String product_3 = "下岗失业人员小额担保贷款";
    String product_4 = "家装贷";

    Button logout;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter adapter;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences_2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
    }

//    绑定，设置listview显示贷款
    private void initializeView() {
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.myList);

        arrayList = new ArrayList<String>();
        arrayList.add(product_1);
        arrayList.add(product_2);
        arrayList.add(product_3);
        arrayList.add(product_4);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Alert");
                builder.setMessage("点击确认后下次需重新输入用户名及密码！");
                alertDialog = builder.create();
//            点击确定，跳转到登录且删除sharedPreferences
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        删除sharedPreferences，则没有登录信息了
                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
//            点击取消仅关闭提示框
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.look:
                Intent intent_2 = new Intent();
                intent_2.setClass(MainActivity.this, MainActivity_2.class);
                startActivity(intent_2);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: running!!!");
        String click = ((TextView)view).getText().toString().trim();//获取点击的item的内容
        if(click.equals(product_1)){
            Uri uri = Uri.parse("http://www.ccb.com/cn/personal/credit/consumeloan.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(click.equals(product_2)){
            Uri uri = Uri.parse("http://www.ccb.com/cn/personal/credit/carloan.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(click.equals(product_3)){
            Uri uri = Uri.parse("http://www.ccb.com/cn/personal/credit/unemployer.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(click.equals(product_4)){
            Uri uri = Uri.parse("http://www.ccb.com/cn/public/20131127_1385541541.html");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else{
            Log.i(TAG, "onItemClick: -----------");
        }
    }

//    在activity_main创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法给当前活动创建菜单了，
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return true;//true：允许创建的菜单显示出来
    }

//    菜单点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1://equal_principal_payment等额本金
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EqualPrincipalPayment.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu2://equal_loan_payment等额本息
                Intent intent_2 = new Intent();
                intent_2.setClass(MainActivity.this, EqualLoanPayment.class);
                startActivity(intent_2);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

//    长按后选择准备贷的贷款，将用户名、贷款加入sharedPreferences_2
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String click = ((TextView)view).getText().toString().trim();//获取点击的item的内容（即产品名称）
        sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString("username","");

        sharedPreferences_2 = getSharedPreferences("purchase",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_2.edit();
        editor.putString("username",name);
        editor.putString("purchase",click);
        editor.commit();

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MainActivity_2.class);
        startActivity(intent);
        finish();
        return true;
    }
}
