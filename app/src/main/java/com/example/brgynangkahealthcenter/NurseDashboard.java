package com.example.brgynangkahealthcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NurseDashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, dashboard, users, inventory, contactUs, exit;
    Button viewProfile, viewprofile, listfamily, viewUsers, viewAdmin, viewDoctor, viewNurse;

    //SWITCH MODE
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nurse_dashboard);

        //Button for profile
        viewProfile = findViewById(R.id.viewProfile);
        viewprofile = findViewById(R.id.viewprofile);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        dashboard = findViewById(R.id.dashboard);
        users = findViewById(R.id.users);
        inventory = findViewById(R.id.inventory);
        contactUs = findViewById(R.id.contactUs);
        exit = findViewById(R.id.exit);

        //DASHBOARD LIST DIRECTORY
        listfamily = findViewById(R.id.familyLists);
        listfamily.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseFamilyList .class); startActivity(i);}
        });

        viewUsers = findViewById(R.id.viewUsers);
        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseUsers .class); startActivity(i);}
        });
        viewAdmin = findViewById(R.id.viewAdmin);
        viewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseAdminList .class); startActivity(i);}
        });
        viewDoctor = findViewById(R.id.viewDoctor);
        viewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseDoctorList .class); startActivity(i);}
        });

        viewNurse = findViewById(R.id.viewNurse);
        viewNurse.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseNurseList .class); startActivity(i);}
        });


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

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseProfilePi.class); startActivity(i);}
        });

        // Nav Bar
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseDashboard.this, NurseProfilePi.class); startActivity(i);}
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseDashboard.this, NurseHome.class);
            }

        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseDashboard.this, NurseUsers.class);
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseDashboard.this, NurseInventory.class);
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseDashboard.this, NursePreConsultation.class);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NurseDashboard.this, "Logout", Toast.LENGTH_SHORT).show();
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