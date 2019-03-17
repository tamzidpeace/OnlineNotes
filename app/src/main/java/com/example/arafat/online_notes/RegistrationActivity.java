package com.example.arafat.online_notes;

import android.content.Context;
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

public class RegistrationActivity extends AppCompatActivity {

    //constant
    private static final String TAG = "RegistrationActivity";

    //member variable
    private Context mContext;
    EditText userName, userPass, userEmail;
    private String username = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mContext = getApplicationContext();

        userName = findViewById(R.id.user_name);
        userPass = findViewById(R.id.user_pass);
        userEmail = findViewById(R.id.user_email);

        Button signUpBtn = findViewById(R.id.sign_up_btn);
        Button singInBtn = findViewById(R.id.sing_in);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegistration();
            }
        });

        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPref", MODE_PRIVATE);
        username = sharedPreferences.getString("name", null);

        if (username != null)
            startActivity(new Intent(mContext, LoginActivity.class));
    }

    private void userRegistration() {

        // server site

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        //network.notify();

        requestQueue.start();

        String url = "http://192.168.0.103/Notes-Api/user_registration.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        if (response.equals("{\"error\":false,\"message\":\"Registration successful\"}")) {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                String user_name = userName.getText().toString();
                String user_pass = userPass.getText().toString().trim();
                String user_email = userEmail.getText().toString().trim();

                Map<String, String> param = new HashMap<>();

                if (!user_name.isEmpty() && !user_pass.isEmpty() && !user_email.isEmpty()) {
                    param.put("user_name", user_name);
                    param.put("user_pass", user_pass);
                    param.put("user_email", user_email);
                    return param;
                } else {
                    Toast.makeText(RegistrationActivity.this, "Fill all three fields", Toast.LENGTH_SHORT).show();
                }
                return param;
            }
        };

        MySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(stringRequest);
    }
}
