package com.example.shivam.kisanneeti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRW extends AppCompatActivity {
    EditText artist_name;
    Spinner genre;
    Button add_artist;

    ListView listviewartist;
    List<Artist> artistList;
    DatabaseReference databaseArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_rw);
        artist_name=(EditText)findViewById(R.id.name_txt);
        genre=(Spinner)findViewById(R.id.genre_spinner);
        add_artist=(Button)findViewById(R.id.add_btn);

        add_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });

        databaseArtists= FirebaseDatabase.getInstance().getReference("Artists");
        listviewartist=(ListView)findViewById(R.id.listViewArtist);
        artistList=new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();
                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    //Artist artist=artistSnapshot.getValue(Artist.class);
                    Artist artist=new Artist();
                    int i=0;
                    for(DataSnapshot as : artistSnapshot.getChildren()){
                        if(i==0){
                            artist.setArtistGenre(as.getValue(String.class));
                            i++;
                        }
                        else{
                            artist.setArtistName(as.getValue(String.class));
                            i=0;
                        }
                    }
                    artistList.add(artist);
                }
                ArtistList adapter=new ArtistList(FirebaseRW.this,artistList);
                listviewartist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void addArtist(){
        String name=artist_name.getText().toString();
        String genre_type=genre.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id=databaseArtists.push().getKey();
            Artist artist=new Artist(name,genre_type);
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this,"Artist Added!",Toast.LENGTH_LONG).show();
            artist_name.setText("");
        }
        else{
            Toast.makeText(this,"Enter Name!",Toast.LENGTH_SHORT).show();
        }
    }
}
