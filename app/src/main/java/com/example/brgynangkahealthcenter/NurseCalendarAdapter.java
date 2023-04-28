package com.example.brgynangkahealthcenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NurseCalendarAdapter extends RecyclerView.Adapter<NurseCalendarViewHolder>{
    private final ArrayList<String> daysOfMonth;
    private final NurseCalendarAdapter.OnItemListener onItemListener;

    public NurseCalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public NurseCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_nurse_calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new NurseCalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NurseCalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }
    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}
