package com.example.arafat.online_notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Model.MySingleton;

public class AddNote extends AppCompatActivity {

    private static final String TAG = "AddNote";

    EditText addNote;
    EditText addTitle;
    Button saveBtn;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Log.d(TAG, "onCreate: ");

        addNote = findViewById(R.id.add_note);
        addTitle = findViewById(R.id.add_title);
        saveBtn = findViewById(R.id.save_note);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNote.this, MainActivity.class));
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue;


                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

                Network network = new BasicNetwork(new HurlStack());

                requestQueue = new RequestQueue(cache, network);

                //network.notify();

                requestQueue.start();

                String url = "http://192.168.43.30/Notes-Api/insert-data.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                Toast.makeText(AddNote.this, response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                Toast.makeText(AddNote.this, error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onErrorResponse: " + error.toString());
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        String title = addTitle.getText().toString();
                        String note = addNote.getText().toString();

                        Map<String, String> param = new HashMap<>();
                        param.put("title", title);
                        param.put("note", note);
                        return param;
                    }
                };

                MySingleton.getInstance(AddNote.this).addToRequestQueue(stringRequest);


            }
        });

        //finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddNote.this, AddNote.class));
    }
}
