package com.example.brgynangkahealthcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class NurseProfilePi extends AppCompatActivity {

    //NAV BAR
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, dashboard, users, inventory, contactUs, exit;
    Button  viewProfile;

    //PROFILE ITEMS
    ImageButton preConsult;

    //FETCHING DATA INFORMATION IN PROFILE
    TextView  profileBirthday, profileAge, profileSex;
    TextView  titleFirstname,titleLastname, titleID;

    //SWITCH MODE
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_profile_pi);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //Drawer
        drawerLayout = findViewById(R.id.drawerLayout);

        //Nav Bar
        //view Profile
        viewProfile = findViewById(R.id.viewProfile);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        dashboard = findViewById(R.id.dashboard);
        users = findViewById(R.id.users);
        inventory = findViewById(R.id.inventory);
        contactUs = findViewById(R.id.contactUs);
        exit = findViewById(R.id.exit);

        //FETCHING DATA INFORMATION IN PROFILE
        profileBirthday = findViewById(R.id.profileBirthday);
        profileAge = findViewById(R.id.profileAge);
        profileSex = findViewById(R.id.profileSex);

        titleFirstname = findViewById(R.id.profileFirstname);
        titleLastname = findViewById(R.id.profileLastname);
        titleID = findViewById(R.id.profileID);
        loadUserData();

        //SWITCH MODE
        switchMode = findViewById(R.id.switchMode);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        if (nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", true);
                }
                editor.apply();
            }
        });

        //Nav Bar

        //view Profile
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseProfilePi.this, NurseProfilePi.class); startActivity(i);}
        });

        //Nav Bar
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseProfilePi.this, NurseHome.class);
            }

        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseProfilePi.this, NurseDashboard.class);
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(NurseProfilePi.this, NurseUsers.class);
            }
        });
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseProfilePi.this, NurseInventory.class);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseProfilePi.this, NursePreConsultation.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NurseProfilePi.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(),NurseLogin.class);
                startActivity(intent);
            }
        });

    }

    //PROFILE ITEMS

    public void opencitizen_profile_pi(View view) {
        startActivity(new Intent(this,NurseProfilePi.class));
    }



    public void opencitizen_profile_ai(View view) {
        startActivity(new Intent(this,NurseProfileAi.class));

    }

    //FETCHING DATA INFORMATION IN PROFILE
    public void loadUserData(){

        Intent intent = getIntent();

        String birthdayUser = intent.getStringExtra("birthday");
        String ageUser = intent.getStringExtra("age");
        String sexUser = intent.getStringExtra("sex");
        String firstnameUser = intent.getStringExtra("firstname");
        String lastnameUser = intent.getStringExtra("lastname");
        String idUser = intent.getStringExtra("IDNumber");

        titleFirstname.setText(firstnameUser);
        titleLastname.setText(lastnameUser);
        titleID.setText(idUser);

        profileBirthday.setText(birthdayUser);
        profileAge.setText(ageUser);
        profileSex.setText(sexUser);

    }


    //Drawer
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);

    }
}