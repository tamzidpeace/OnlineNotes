package com.example.arafat.online_notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Model.MySingleton;

public class AddNote extends AppCompatActivity {

    private static final String TAG = "AddNote";

    private EditText addNote;
    private EditText addTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Log.d(TAG, "onCreate: ");

        //initializing views
        addNote = findViewById(R.id.add_note);
        addTitle = findViewById(R.id.add_title);
        Button saveBtn = findViewById(R.id.save_note);
        Button updateBtn = findViewById(R.id.updateBtn);

        //save note
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        // update note
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String note = intent.getStringExtra("note");
        String status = intent.getStringExtra("status");
        if (status.equals("1")) {
            saveBtn.setVisibility(View.GONE);
        } else {
            updateBtn.setVisibility(View.GONE);
        }


        addNote.setText(note);
        addTitle.setText(title);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                updateNote();
            }
        });

    }

    private void saveNote() {

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        //network.notify();

        requestQueue.start();

        String url = "http://192.168.0.103/Notes-Api/insert-data.php";

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
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                String user_name = sharedPreferences.getString("name", null);

                Map<String, String> param = new HashMap<>();
                param.put("title", title);
                param.put("note", note);
                if (user_name != null) {
                    param.put("user_name", user_name);
                }
                return param;
            }
        };

        MySingleton.getInstance(AddNote.this).addToRequestQueue(stringRequest);

        Intent intent = new Intent(AddNote.this, MainActivity.class);
        startActivity(intent);

    }

    private void updateNote() {

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        String url = "http://192.168.0.103/Notes-Api/update-data.php";

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
                param.put("id", id);
                Log.d(TAG, "getParams: " + title + note + id);
                return param;
            }
        };

        MySingleton.getInstance(AddNote.this).addToRequestQueue(stringRequest);
        startActivity(new Intent(AddNote.this, MainActivity.class));

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

}
