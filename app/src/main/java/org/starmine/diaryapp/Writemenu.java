package org.starmine.diaryapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Writemenu extends AppCompatActivity {
    TextView year, month, day;
    int selectYear, selectMonth, selectDay;
    Button picker_button, save_button, finish_button;
    Spinner category;
    CalendarView calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_write);

        year = findViewById(R.id.Year);
        month = findViewById(R.id.Month);
        day = findViewById(R.id.Day);

        calendar = findViewById(R.id.Calender);
        calendar.setVisibility(View.INVISIBLE);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat MonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat DayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        year.setText(YearFormat.format(date));
        month.setText(MonthFormat.format(date));
        day.setText(DayFormat.format(date));

        final String[] cate = {"일상", "꿈일기", "잡생각", "기타"};

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cate);
        category = findViewById(R.id.spinner_category);
        category.setAdapter(adapter);

        picker_button = findViewById(R.id.Select_date);
        picker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setVisibility(View.VISIBLE);
                finish_button.setVisibility(View.VISIBLE);
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectYear = year;
                selectMonth = month;
                selectDay = dayOfMonth;
            }
        });


        save_button = findViewById(R.id.Save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        finish_button = findViewById(R.id.Finish_button);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year.setText(Integer.toString(selectYear));
                month.setText(Integer.toString(selectMonth+1));
                day.setText(Integer.toString(selectDay));

                calendar.setVisibility(View.INVISIBLE);
                finish_button.setVisibility(View.INVISIBLE);
            }
        });
    }
}
