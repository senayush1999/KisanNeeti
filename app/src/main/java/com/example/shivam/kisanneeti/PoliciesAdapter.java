package com.example.shivam.kisanneeti;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 31-10-2018.
 */

public class PoliciesAdapter extends ArrayAdapter<String>{
    private Activity context;
    private List<String> policyname;

    public PoliciesAdapter(Activity context,List<String> policyname){
        super(context,R.layout.policies_list_layout,policyname);
        this.context=context;
        this.policyname=policyname;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.policies_list_layout,null,true);


        TextView textViewName=listViewItem.findViewById(R.id.policies_name);

        String name=policyname.get(position);
        textViewName.setText(name);
        return listViewItem;
    }
}
