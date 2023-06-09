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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DoctorConsultationForm extends AppCompatActivity {

    //NAV DRAWER
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, dashboard, consultation, prescription, users, inventory, exit;
    Button viewProfile;

    //SWITCH MODE
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consultation_form);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        //Drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        //view Profile
        viewProfile = findViewById(R.id.viewProfile);
        //Nav Bar
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        dashboard = findViewById(R.id.dashboard);
        consultation = findViewById(R.id.consultation);
        prescription = findViewById(R.id.prescription);
        users = findViewById(R.id.users);
        inventory = findViewById(R.id.inventory);
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
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(DoctorConsultationForm.this, DoctorProfile.class); startActivity(i);}
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
                redirectActivity(DoctorConsultationForm.this, DoctorHome.class);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorConsultationForm.this, DoctorDashboard.class);
            }
        });

        consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorConsultationForm.this, DoctorConsultation.class);
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorConsultationForm.this, DoctorPrescription.class);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorConsultationForm.this, DoctorUsers.class);
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(DoctorConsultationForm.this, DoctorInventory.class);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DoctorConsultationForm.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(),DoctorLogin.class);
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