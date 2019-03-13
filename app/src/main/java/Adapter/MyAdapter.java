package Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arafat.online_notes.AddNote;
import com.example.arafat.online_notes.MainActivity;
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
    MainActivity mainActivity = new MainActivity();



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
        final LayoutInflater inflater = LayoutInflater.from(mContext);

        //creating a view with our xml layout
        View listItem = inflater.inflate(R.layout.list_item, null, true);

        final Model model = mInfoList.get(position);

        final TextView mName = listItem.findViewById(R.id.title_tv);
        TextView mPassword = listItem.findViewById(R.id.note_tv);

        mName.setText(model.getName());
        mPassword.setText(model.getPassword());
        
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String title = model.getName();
                final String note = model.getPassword();
                Log.d(TAG, "onClick: single click " + title + " " + note);

                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setTitle(title);
                alertbox.setMessage(note);
                alertbox.setNeutralButton("Edit",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                Log.d(TAG, "onClick: Edit");

                                Intent intent = new Intent(mContext, AddNote.class);
                                intent.putExtra("title", title);
                                intent.putExtra("note", note);
                                intent.putExtra("id", model.getId());
                                mContext.startActivity(intent);

                            }
                        });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                    }
                });
                alertbox.show();
            }
        });

        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                alertbox.setMessage("Do you want to delete this note?");
                alertbox.setTitle("Warning");
                alertbox.setIcon(R.drawable.ic_warning_black_24dp);

                alertbox.setNeutralButton("YES",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                String id = model.getId();
                                deleteNote(id);
                                Toast.makeText(mContext, "Note has been deleted! You need to refresh activity", Toast.LENGTH_SHORT).show();
                                /*finish();
                                startActivity(getIntent());*/

                            }
                        });
                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                    }
                });
                alertbox.show();


                return false;
            }
        });


        return listItem;
    }

    private void deleteNote(final String id) {


        RequestQueue queue = Volley.newRequestQueue(mContext);


        String url = "http://192.168.0.101/Notes-Api/delete-data.php";

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
