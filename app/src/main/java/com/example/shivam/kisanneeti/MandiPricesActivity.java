package com.example.shivam.kisanneeti;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MandiPricesActivity extends AppCompatActivity {

    Spinner mandi,city,crop;
    List<String> mandilist,croplist;
    DatabaseReference databaseMandiPrices;
    String city_selected,mandi_selected,crop_selected;
    TextView prices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandi_prices);
        databaseMandiPrices= FirebaseDatabase.getInstance().getReference("Mandi Prices");
        mandilist=new ArrayList<>();
        mandi=(Spinner)findViewById(R.id.mandi_set);
        city=(Spinner)findViewById(R.id.city_set);
        crop=(Spinner)findViewById(R.id.crop_set);
        prices=(TextView)findViewById(R.id.pricetxt);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onStart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btn=(Button)findViewById(R.id.find);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (city.getSelectedItem().equals("--Select--")) {
                    Toast.makeText(MandiPricesActivity.this, "Please select a city!", Toast.LENGTH_SHORT).show();
                } else if (mandi.getSelectedItem().equals("") || mandi.getSelectedItem().equals("--Select--")) {
                    Toast.makeText(MandiPricesActivity.this, "Please select a mandi!", Toast.LENGTH_SHORT).show();
                } else if (crop.getSelectedItem().equals("--Select--")) {
                    Toast.makeText(MandiPricesActivity.this, "Please select a crop!", Toast.LENGTH_SHORT).show();
                } else {
                    databaseMandiPrices.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            city_selected = city.getSelectedItem().toString();
                            mandi_selected = mandi.getSelectedItem().toString();
                            crop_selected = crop.getSelectedItem().toString();
                            for (DataSnapshot CitySnapshot : dataSnapshot.getChildren()) {
                                if (city_selected.equalsIgnoreCase(CitySnapshot.getKey())) {
                                    for (DataSnapshot MandiSnapshot : CitySnapshot.getChildren()) {
                                        if (mandi_selected.equalsIgnoreCase(MandiSnapshot.getKey())) {
                                            for (DataSnapshot CropSnapshot : MandiSnapshot.getChildren()) {
                                                if (crop_selected.equalsIgnoreCase(CropSnapshot.getKey())) {
                                                    for (DataSnapshot as : CropSnapshot.getChildren()) {
                                                        prices.setText(as.getValue(Object.class).toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseMandiPrices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mandilist.clear();
                city_selected=city.getSelectedItem().toString();
                for(DataSnapshot MandiSnapshot : dataSnapshot.getChildren()){
                    String val= MandiSnapshot.getKey();
                    if(city_selected.equals(val)) {
                        for (DataSnapshot as : MandiSnapshot.getChildren()) {
                            mandilist.add(as.getKey());
                        }
                        break;
                    }
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(MandiPricesActivity.this,android.R.layout.simple_spinner_dropdown_item,mandilist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.notifyDataSetChanged();
                mandi.setAdapter(arrayAdapter);
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
