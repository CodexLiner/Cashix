package com.crepaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class add_bank_details extends AppCompatActivity {
    private Button cButton;
    public Spinner spinner;
    public String[] idList = {
            "Select ID" ,
            "Adhaar Card" , "Pan Card" ,
            "Votor ID" , "Bank Passbook"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_details);
        cButton = findViewById(R.id.otp_continue_button);
        spinner =(Spinner) findViewById(R.id.spinnerView);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
                idList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Home_Activity.class));
                Toast.makeText(add_bank_details.this, "Account Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}