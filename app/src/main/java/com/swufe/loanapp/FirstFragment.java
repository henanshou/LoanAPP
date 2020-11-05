package com.swufe.loanapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//fragment中很多获取不到的都加getActivity(). ，或直接用getActivity()替换
public class FirstFragment extends Fragment implements View.OnClickListener{
    TextView product;
    SharedPreferences sharedPreferences_2;
    Button button;
    private DBManager_2 dbManager_2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);
        button = view.findViewById(R.id.purchase);
        button.setOnClickListener(this);

        product = (TextView)view.findViewById(R.id.product);
        sharedPreferences_2 = getActivity().getSharedPreferences("purchase",Activity.MODE_PRIVATE);
        product.setText(sharedPreferences_2.getString("purchase",""));

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.purchase && product.getText().toString().length()!=0) {
            dbManager_2 = new DBManager_2(getActivity());
            dbManager_2.add(sharedPreferences_2.getString("username",""),
                    sharedPreferences_2.getString("purchase",""));

            Toast.makeText(getActivity(), "请等待贷款审核与发放", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "尚未选择贷款", Toast.LENGTH_SHORT).show();
        }
    }
}