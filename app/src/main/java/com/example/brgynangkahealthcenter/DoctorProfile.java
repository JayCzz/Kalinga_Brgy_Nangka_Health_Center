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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorProfile extends AppCompatActivity {

    //NAV BAR
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, dashboard, consultation, users, inventory, exit;
    Button viewProfile;

    //SWITCH MODE
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
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
        consultation = findViewById(R.id.consultation);
        exit = findViewById(R.id.exit);

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
            @Override public void onClick(View view) { Intent i = new Intent(DoctorProfile.this, DoctorProfile.class); startActivity(i);}
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
                redirectActivity(DoctorProfile.this, DoctorHome.class);
            }

        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorProfile.this, DoctorDashboard.class);
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                redirectActivity(DoctorProfile.this, DoctorUsers.class);
            }
        });
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorProfile.this, DoctorInventory.class);
            }
        });
        consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorProfile.this, DoctorConsultation.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DoctorProfile.this, "Logout", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(this,DoctorProfile.class));
    }


    public void opencitizen_profile_ai(View view) {
        startActivity(new Intent(this,DoctorProfileAi.class));


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