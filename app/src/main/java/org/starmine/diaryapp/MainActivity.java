package org.starmine.diaryapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button write_button;
    MaterialCalendarView main_calendar;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.caltext);
        textView.setText(CalendarDay.today().toString());

        main_calendar = findViewById(R.id.Main_calendar);
        main_calendar.setSelectedDate(CalendarDay.today());
        //"CalendarDay(2021-11-30)"

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);

        main_calendar.addDecorator(new CalendarDecorator(Color.RED, Collections.singleton(CalendarDay.from(2021,11,03))));



        write_button = findViewById(R.id.Write_button);
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Writemenu.class);
                startActivity(intent);
            }
        });
    }

}

