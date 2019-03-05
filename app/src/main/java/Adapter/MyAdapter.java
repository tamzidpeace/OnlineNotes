package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arafat.online_notes.R;

import java.util.ArrayList;


import Model.Model;

public class MyAdapter extends ArrayAdapter<Model> {

    // member variable
    private Context mContext;
    private ArrayList<Model> mInfoList;

    // public constructor
    public MyAdapter(@NonNull Context context, ArrayList<Model> infoList) {
        super(context, R.layout.list_item, infoList);

        mContext = context;
        mInfoList = infoList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //getting the layout inflater
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //creating a view with our xml layout
        View listItem = inflater.inflate(R.layout.list_item, null, true);

        Model model = mInfoList.get(position);

        TextView mName = listItem.findViewById(R.id.title_tv);
        TextView mPassword = listItem.findViewById(R.id.note_tv);

        mName.setText(model.getName());
        mPassword.setText(model.getPassword());

        return listItem;
    }
}
