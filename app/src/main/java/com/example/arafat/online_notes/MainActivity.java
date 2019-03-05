package com.example.arafat.online_notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.*;
import Adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Model> infoList = new ArrayList<>();
    private String title, note;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_view);

        showNotes(infoList);

    }


    private void showNotes(final ArrayList<Model> infoList) {


        String url = "http://192.168.43.30/Notes-Api/read-data.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            for (int i = 0; i < response.getJSONArray("DataArray").length(); i++) {
                                title = response.getJSONArray("DataArray").getJSONObject(i).getString("Title");
                                note = response.getJSONArray("DataArray").getJSONObject(i).getString("Note");
                                infoList.add(new Model(title, note));
                            }

                            MyAdapter adapter = new MyAdapter(getApplicationContext(), infoList);
                            mListView.setAdapter(adapter);

                            Log.d(TAG, "onResponse: " + title + note);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse exception: " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }
}
