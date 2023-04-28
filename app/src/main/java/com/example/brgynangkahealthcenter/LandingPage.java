package com.example.brgynangkahealthcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class LandingPage extends AppCompatActivity {


    private Button Doctor;
    private Button Nurse;
    private Button Citizen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_landing_page);

        Doctor = findViewById(R.id.Doctor);
        Nurse = findViewById(R.id.Nurse);
        Citizen = findViewById(R.id.Citizen);

        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(LandingPage.this, DoctorLogin.class); startActivity(i);}

        });

        Nurse.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(LandingPage.this, NurseLogin.class); startActivity(i);}

        });

        Citizen.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(LandingPage.this, CitizenLogin.class); startActivity(i);}

        });
    }
}