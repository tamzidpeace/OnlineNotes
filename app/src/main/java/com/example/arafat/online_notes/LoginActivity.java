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

public class LoginActivity extends AppCompatActivity {

    //constants
    private static final String TAG = "LoginActivity";

    //member variable
    private EditText userName, userPass;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();

        userName = findViewById(R.id.user_name_login);
        userPass = findViewById(R.id.editText2);

        Button loginBtn = findViewById(R.id.login_btn_ln);
        Button backToSignUp = findViewById(R.id.back_to_register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        backToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, RegistrationActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPref", MODE_PRIVATE);
        String username = sharedPreferences.getString("name", null);

        if (username != null)
            startActivity(new Intent(mContext, MainActivity.class));
    }

    private void userLogin() {

        RequestQueue requestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();


        // storing data using sharedPreference
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userName.getText().toString()).apply();

        String url = "http://192.168.0.103/Notes-Api/test_login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        //String user_name = userPass.getText().toString();

                        if (response.equals(userName.getText().toString())) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //intent.putExtra("user_name", user_name);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                String user_name = userName.getText().toString();
                String user_pass = userPass.getText().toString().trim();

                Map<String, String> param = new HashMap<>();

                if (!user_name.isEmpty() && !user_pass.isEmpty()) {
                    param.put("user_name", user_name);
                    param.put("user_pass", user_pass);
                    return param;
                } else {
                    Toast.makeText(LoginActivity.this, "Fill all three fields", Toast.LENGTH_SHORT).show();
                }
                return param;
            }
        };

        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }
}
