package com.example.shivam.kisanneeti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PoliciesActivity extends AppCompatActivity {
    List<String> policyname;
    ListView policiesList;
    DatabaseReference databasePolicies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        policyname=new ArrayList<>();
        policiesList=(ListView)findViewById(R.id.policieslistview);
        databasePolicies= FirebaseDatabase.getInstance().getReference("Policies");


    }
    @Override
    protected void onStart() {
        super.onStart();
        databasePolicies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                policyname.clear();
                for(DataSnapshot PolicySnapshot : dataSnapshot.getChildren()){
                    String val= PolicySnapshot.getKey();
                    policyname.add(val);
                }
                PoliciesAdapter adapter=new PoliciesAdapter(PoliciesActivity.this,policyname);
                policiesList.setAdapter(adapter);

                policiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(view.getContext(),PolicyOverview.class);
                        intent.putExtra("name",policiesList.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
