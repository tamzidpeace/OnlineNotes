package com.example.arafat.online_notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.Model;
import Adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = findViewById(R.id.list_view);
        ArrayList<Model> infoList = new ArrayList<>();

        MyAdapter adapter = new MyAdapter(this, infoList);

        mListView.setAdapter(adapter);
        helloWorld(infoList);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void helloWorld(ArrayList<Model> infoList) {
        Toast.makeText(this, "Hello, World!", Toast.LENGTH_SHORT).show();


        infoList.add(new Model("Arafat", "1234"));
        infoList.add(new Model("Tamzid", "Lorem ipsum, dolor sit amet consectetur."));
    }
}
