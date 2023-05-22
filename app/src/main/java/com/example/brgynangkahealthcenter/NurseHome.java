package com.example.brgynangkahealthcenter;

import static com.example.brgynangkahealthcenter.NurseHomeCalendarUtils.daysInMonthArray;
import static com.example.brgynangkahealthcenter.NurseHomeCalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;

public class NurseHome extends AppCompatActivity implements NurseHomeCalendarAdapter.OnItemListener {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, dashboard, users, inventory, contactUs, exit;
    Button viewProfile;

    //HOME MODULES
    Button scan_button, gen_button, queueRegister, viewLists, queuingMonitoring, viewCounter;

    //CALENDAR
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    //SWITCH MODE
    SwitchCompat switchMode;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nurse_home);

        //CALENDAR
        initWidgets();
        NurseHomeCalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        //HOME LIST DIRECTORY

        //QUEUING MONITORING
        queuingMonitoring = findViewById(R.id.queuingMonitor);
        queuingMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, NurseHomeQueuingMonitor.class); startActivity(i);}
        });
        //QUEUING REGISTRATION
        queueRegister = findViewById(R.id.queueingRegistration);
        queueRegister.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, NurseQueuingRegistration.class); startActivity(i);}
        });

        //QR Scanner
        scan_button = findViewById(R.id.scan_btn);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, Nurse_QR_Scanner.class); startActivity(i);}

        });

        //QR GENERATOR
        gen_button = findViewById(R.id.gen_btn);
        gen_button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, Nurse_QR_Generator.class); startActivity(i);}

        });

        viewLists = findViewById(R.id.viewList);
        //VIEW LISTS
        viewLists.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, NurseQRList.class); startActivity(i);}

        });

        viewCounter = findViewById(R.id.viewCounter);
        //VIEW LISTS
        viewCounter.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, NurseHomeCounter.class); startActivity(i);}

        });




        viewProfile = findViewById(R.id.viewProfile);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        dashboard = findViewById(R.id.dashboard);
        users = findViewById(R.id.users);
        inventory = findViewById(R.id.inventory);
        contactUs = findViewById(R.id.contactUs);
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
            @Override public void onClick(View view) { Intent i = new Intent(NurseHome.this, NurseProfilePi.class); startActivity(i);}
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
                recreate();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseHome.this, NurseDashboard.class);
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseHome.this, NurseUsers.class);
            }
        });
        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseHome.this, NurseInventory.class);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(NurseHome.this, NursePreConsultation.class);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NurseHome.this, "Logout", Toast.LENGTH_SHORT).show();
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

    //CALENDAR
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    // WEEKLY CALENDAR

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(NurseHomeCalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(NurseHomeCalendarUtils.selectedDate);

        NurseHomeCalendarAdapter calendarAdapter = new NurseHomeCalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @SuppressLint("NewApi")
    public void previousMonthAction(View view)
    {
        NurseHomeCalendarUtils.selectedDate = NurseHomeCalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @SuppressLint("NewApi")
    public void nextMonthAction(View view)
    {
        NurseHomeCalendarUtils.selectedDate = NurseHomeCalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            NurseHomeCalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, NurseWeeklyViewActivity.class));
    }


    //NAV DRAWER
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

    @Override
    public void onItemClick(int position, String dayText) {

    }
}
