package com.example.calenderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calenderapp.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private CalendarAdapter calendarAdapter;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearText);
    }

    public void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(daysInMonth, this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 7);
            calendarRecyclerView.setLayoutManager(layoutManager);
            calendarRecyclerView.setAdapter(calendarAdapter);
        } else {
            calendarAdapter.updateData(daysInMonth);
        }
    }


    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();

        // Start from the first day of the month
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        // Calculate the day of the week for the 1st (e.g., if the 1st is on Wednesday, the value will be 3)
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Adjust dayOfWeek for Sunday start (Java uses Monday start by default)
        // If your week starts on Sunday and Java's day of week starts on Monday, adjust by subtracting 1
        int firstDayColumn = (dayOfWeek == 7) ? 0 : dayOfWeek; // Sunday as the first day of the week

        // Add blank days for the days before the first of the month
        for (int i = 0; i < firstDayColumn; i++) {
            daysInMonthArray.add("");
        }

        // Add days of the month
        for (int i = 1; i <= daysInMonth; i++) {
            daysInMonthArray.add(String.valueOf(i));
        }

        // Fill the rest of the 42 cells (6 rows * 7 columns)
        while (daysInMonthArray.size() < 42) {
            daysInMonthArray.add("");
        }

        return daysInMonthArray;
    }


    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {

        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}