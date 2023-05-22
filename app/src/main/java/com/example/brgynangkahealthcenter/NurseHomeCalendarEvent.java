package com.example.brgynangkahealthcenter;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NurseHomeCalendarEvent {


    public static ArrayList<NurseHomeCalendarEvent> eventList = new ArrayList<>();

    public static ArrayList<NurseHomeCalendarEvent> eventsForDate(LocalDate date)
    {
        ArrayList<NurseHomeCalendarEvent> events = new ArrayList<>();

        for(NurseHomeCalendarEvent nurseHomeCalendarEvent : eventList)
        {
            if(nurseHomeCalendarEvent.getDate().equals(date))
                events.add(nurseHomeCalendarEvent);
        }

        return events;
    }




    private String name;
    private LocalDate date;
    private LocalTime time;

    public NurseHomeCalendarEvent(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
