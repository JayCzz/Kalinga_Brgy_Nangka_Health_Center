package com.example.brgynangkahealthcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NurseHomeCalendarEventAdapter extends ArrayAdapter<NurseHomeCalendarEvent>
{
    public NurseHomeCalendarEventAdapter(@NonNull Context context, List<NurseHomeCalendarEvent> events)
    {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        NurseHomeCalendarEvent nurseHomeCalendarEvent = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_nurse_home_calendar_event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = nurseHomeCalendarEvent.getName() +" "+ NurseHomeCalendarUtils.formattedTime(nurseHomeCalendarEvent.getTime());
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}
