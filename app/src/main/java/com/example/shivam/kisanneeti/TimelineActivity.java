package com.example.shivam.kisanneeti;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimelineActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    DatabaseReference databaseCropCalendar;
    String month_selected,crop_selected;
    Spinner crop;
    TextView cropdata;
    Calendar newCalendar;
    int clicked=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        fromDateEtxt = (EditText) findViewById(R.id.date_set);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        fromDateEtxt.setText("Tap to select");

        crop=(Spinner)findViewById(R.id.crop_set);
        cropdata=(TextView)findViewById(R.id.crop_data);

        cropdata.setVisibility(View.INVISIBLE);

        setDateTimeField();

        String crops[]={"--Select--","Wheat","Maize","Paddy","Maize"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,crops);
        crop.setAdapter(arrayAdapter);

        databaseCropCalendar= FirebaseDatabase.getInstance().getReference("Crop Calendar");
        Button btn=(Button)findViewById(R.id.find);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check_date_field=fromDateEtxt.getText().toString();
                if(check_date_field.equals("Tap to select")){
                    Toast.makeText(TimelineActivity.this,"Please select a date!",Toast.LENGTH_SHORT).show();
                }
                else if(crop.getSelectedItem().equals("--Select--"))
                    Toast.makeText(TimelineActivity.this,"Please select a crop!",Toast.LENGTH_SHORT).show();
                else
                    print_data();

            }
        });
    }

    private void print_data(){
        databaseCropCalendar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                crop_selected=crop.getSelectedItem().toString();
                for (DataSnapshot CropSnapshot : dataSnapshot.getChildren()){
                    if(crop_selected.equalsIgnoreCase(CropSnapshot.getKey())){
                        cropdata.setVisibility(View.VISIBLE);
                        cropdata.setText(CropSnapshot.child(month_selected).getValue(String.class));

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
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
                newCalendar = Calendar.getInstance();
                fromDatePickerDialog = new DatePickerDialog(TimelineActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        month_selected=(monthOfYear+1)+"";
                        fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {

        fromDatePickerDialog.show();
    }
}
