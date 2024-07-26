package com.example.calenderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private int selectedItem = -1; // Index of the selected item

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_cell, parent, false);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = daysOfMonth.get(position);
        holder.dayOfMonth.setText(day);

        // Highlight the background if selected
        if (selectedItem == position) {
            holder.dayOfMonth.setBackgroundResource(R.drawable.selected_day_background);
        } else {
            holder.dayOfMonth.setBackgroundResource(R.drawable.normal_day_background);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedItem = position;
            notifyDataSetChanged();
            onItemListener.onItemClick(position, day);
        });
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        public final TextView dayOfMonth;

        public CalendarViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
        }
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    public void updateData(ArrayList<String> newDaysOfMonth) {
        this.daysOfMonth = newDaysOfMonth; // Update the list of days
        notifyDataSetChanged(); // Notify the adapter of the data change
    }
}
