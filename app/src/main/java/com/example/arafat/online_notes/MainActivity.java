package com.example.arafat.online_notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Model.Model;
import Adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_view);
        ArrayList<Model> infoList = new ArrayList<>();

        infoList.add(new Model("Arafat", "1234"));

        MyAdapter adapter = new MyAdapter(this, infoList);

        mListView.setAdapter(adapter);
    }
}
