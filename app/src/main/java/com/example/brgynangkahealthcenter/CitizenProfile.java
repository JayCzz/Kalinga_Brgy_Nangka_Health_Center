package com.example.brgynangkahealthcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CitizenProfile extends AppCompatActivity {

    TextView ProfileFirstname, ProfileLastname, ProfileBirthday, ProfileAge, ProfileSex, ProfileEmail, ProfilePhone, ProfileUsername, ProfilePassword;
    TextView titleName, titlebrgyId;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_profile);

        getSupportActionBar().setTitle("Kalinga | Profile");
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

        titleName = findViewById(R.id.titleName);
        titlebrgyId = findViewById(R.id.titlebrgyId);

        ProfileFirstname = findViewById(R.id.userFirstname);
        ProfileLastname = findViewById(R.id.userLastname);
        ProfileBirthday = findViewById(R.id.userBirthday);
        ProfileAge = findViewById(R.id.userAge);
        ProfileSex = findViewById(R.id.userSex);
        ProfileEmail = findViewById(R.id.userEmail);
        ProfilePhone = findViewById(R.id.userPhone);
        ProfileUsername = findViewById(R.id.userName);
        ProfilePassword = findViewById(R.id.userPassword);

        // initialize Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");

        // retrieve user data from intent


        // display user data


        // retrieve additional data from Firebase database



    }
}


