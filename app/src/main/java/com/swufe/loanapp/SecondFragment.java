package com.swufe.loanapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {
    ListView myList_2;
    DBManager_2 dbManager_2;
    ArrayList<Purchase> arrayList;
    ArrayList<String> arrayList_2;
    ArrayAdapter adapter;
    boolean match;
    SharedPreferences sharedPreferences_2;
    String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);

        myList_2 = view.findViewById(R.id.myList_2);
        sharedPreferences_2 = getActivity().getSharedPreferences("purchase",Activity.MODE_PRIVATE);
        username = sharedPreferences_2.getString("username","");
        arrayList_2 = new ArrayList<>();

        dbManager_2 = new DBManager_2(getActivity());
        arrayList = dbManager_2.getAllData_2();
        match = false;

        for (int i = 0; i < arrayList.size(); i++) {
            Purchase purchase = arrayList.get(i);
            if(username.equals(purchase.getUsername())){
                arrayList_2.add(purchase.getPurchase());
            }
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList_2);
        myList_2.setAdapter(adapter);

        return view;
    }
}
