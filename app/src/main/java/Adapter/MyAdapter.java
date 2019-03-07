package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arafat.online_notes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import Model.Model;

public class MyAdapter extends ArrayAdapter<Model> {

    //const

    private static final String TAG = "MyAdapter";

    // member variable
    private Context mContext;
    private ArrayList<Model> mInfoList;
    private String a;

    // public constructor
    public MyAdapter(@NonNull Context context, ArrayList<Model> infoList) {
        super(context, R.layout.list_item, infoList);

        mContext = context;
        mInfoList = infoList;

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //getting the layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //creating a view with our xml layout
        View listItem = inflater.inflate(R.layout.list_item, null, true);

        final Model model = mInfoList.get(position);

        final TextView mName = listItem.findViewById(R.id.title_tv);
        TextView mPassword = listItem.findViewById(R.id.note_tv);

        mName.setText(model.getName());
        mPassword.setText(model.getPassword());

        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String id = model.getId();
                deleteNote(id);
                return false;
            }
        });


        return listItem;
    }

    private void deleteNote(final String id) {

//        RequestQueue requestQueue;
//
//
//        Cache cache = new DiskBasedCache(); // 1MB cap
//
//        Network network = new BasicNetwork(new HurlStack());
//
//        requestQueue = new RequestQueue(cache, network);

        RequestQueue queue = Volley.newRequestQueue(mContext);



        String url = "http://192.168.43.30/Notes-Api/delete-data.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String id1 = id;

                Map<String, String> param = new HashMap<>();
                Log.d(TAG, "getParams: " + id1);
                //Map<String, int> param = new HashMap<>();
                param.put("id", id1);
                return param;
            }
        };
        
        queue.add(stringRequest);
        //MySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
}
