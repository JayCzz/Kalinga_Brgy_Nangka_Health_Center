package com.example.brgynangkahealthcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class NurseHomeCalendarEventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home_calendar_event_edit);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + NurseHomeCalendarUtils.formattedDate(NurseHomeCalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + NurseHomeCalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }


    public void saveEventAction(View view) {
    }
}