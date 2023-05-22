package com.example.brgynangkahealthcenter;

import static com.example.brgynangkahealthcenter.NurseHomeCalendarUtils.daysInWeekArray;
import static com.example.brgynangkahealthcenter.NurseHomeCalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class NurseWeeklyViewActivity extends AppCompatActivity implements NurseHomeCalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home_calendar_weekly_view);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        initWidgets();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(NurseHomeCalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(NurseHomeCalendarUtils.selectedDate);

        NurseHomeCalendarAdapter nurseCalendarAdapter = new NurseHomeCalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(nurseCalendarAdapter);

    }


    @SuppressLint("NewApi")
    public void previousWeekAction(View view)
    {
        NurseHomeCalendarUtils.selectedDate = NurseHomeCalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @SuppressLint("NewApi")
    public void nextWeekAction(View view)
    {
        NurseHomeCalendarUtils.selectedDate = NurseHomeCalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        NurseHomeCalendarUtils.selectedDate = date;
        setWeekView();
    }

    @Override
    public void onItemClick(int position, String dayText) {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    private void setEventAdpater()
    {
        ArrayList<NurseHomeCalendarEvent> dailyEvents = NurseHomeCalendarEvent.eventsForDate(NurseHomeCalendarUtils.selectedDate);
        NurseHomeCalendarEventAdapter eventAdapter = new NurseHomeCalendarEventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }


    public void newEventAction(View view)
    {
        startActivity(new Intent(this, NurseHomeCalendarEventEditActivity.class));
    }
}