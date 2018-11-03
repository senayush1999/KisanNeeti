package com.example.shivam.kisanneeti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PolicyOverview extends AppCompatActivity {

    TextView policyoverview;
    DatabaseReference databasePolicies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_overview);
        policyoverview=(TextView)findViewById(R.id.overviewtextview);
        databasePolicies= FirebaseDatabase.getInstance().getReference("Policies");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databasePolicies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String policy_name=getIntent().getStringExtra("name");
                for(DataSnapshot PolicynameSnapshot : dataSnapshot.getChildren()){
                    if(policy_name.equals(PolicynameSnapshot.getKey()))
                    {
                        for(DataSnapshot as : PolicynameSnapshot.getChildren()) {
                            policyoverview.setText(as.getValue(String.class));
                        }
                    }
                }
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
