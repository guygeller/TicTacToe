package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ListViewHistory extends AppCompatActivity {
    ListView listView;
    Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_history);
        listView = findViewById(R.id.LV_id);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("history", "{items:[]}");
        HistoryItemAdapter adapter = new HistoryItemAdapter(gson.fromJson(s1,HistoryHolder.class), this);
        listView.setAdapter(adapter);
    }
}