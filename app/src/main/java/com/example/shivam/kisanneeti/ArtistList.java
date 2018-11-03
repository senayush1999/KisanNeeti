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
 * Created by hp on 28-10-2018.
 */

public class ArtistList extends ArrayAdapter<Artist> {
    private Activity context;
    private List<Artist> artistlist;

    public ArtistList(Activity context,List<Artist> artistlist){
        super(context,R.layout.list_layout,artistlist);
        this.context=context;
        this.artistlist=artistlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);


        TextView textViewName=listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre=listViewItem.findViewById(R.id.textViewGenre);

        Artist artist=artistlist.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());
        return listViewItem;
    }
}
