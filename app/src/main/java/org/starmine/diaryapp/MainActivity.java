package org.starmine.diaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button write_button, check_button;
    static MaterialCalendarView main_calendar;
    static TextView dayyy;
    DBHelper Helper;
    SQLiteDatabase sqlDB;
    static int check;
    static String day_st,title_st,cate_st,content_st;
    static String year;
    static String month;
    static String day;
    static String[] day_list,title_list,cate_list,content_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_calendar = findViewById(R.id.Main_calendar);
        main_calendar.setSelectedDate(CalendarDay.today());

        Helper = new DBHelper(MainActivity.this,"DiaryDB.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from diary",null);

        dayyy = findViewById(R.id.daytext);
        check_button = findViewById(R.id.check_Button);

        if(cursor != null){
            while(cursor.moveToNext()){
                day_st += cursor.getString(0) + " ";
                title_st +=cursor.getString(1) + " ";
                cate_st +=cursor.getString(2) + " ";
                content_st +=cursor.getString(3) + " ";

                title_list = title_st.split(" ");
                cate_list = cate_st.split(" ");
                content_list = content_st.split(" ");

                day_list = day_st.split(" ");

                for(int i=1;i<day_list.length;i++){
                    year = day_list[i].substring(0,4);
                    month = day_list[i].substring(4,6);
                    day = day_list[i].substring(6,8);

                    int nyear = Integer.parseInt(year);
                    int nmonth = Integer.parseInt(month);
                    int nday = Integer.parseInt(day);

                    main_calendar.addDecorator(new CalendarDecorator(Color.RED, Collections.singleton(CalendarDay.from(nyear,nmonth,nday))));
                }
            }
            cursor.close();
            sqlDB.close();
        }


        main_calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int cyear = widget.getSelectedDate().getYear();
                int cmonth = widget.getSelectedDate().getMonth();
                int cday = widget.getSelectedDate().getDay();
                String cdate = String.valueOf(cyear+""+cmonth+""+cday);

                for(int i=0;i<day_list.length;i++){
                    if (day_list[i].equals(cdate)){
                        check = i;
                        dayyy.setText(title_list[i]);
                        check_button.setVisibility(View.VISIBLE);
                        check_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(),Checkdiary.class);
                                intent.putExtra("day",day_list[check]);
                                intent.putExtra("title",title_list[check]);
                                intent.putExtra("cate",cate_list[check]);
                                intent.putExtra("content",content_list[check]);
                                startActivity(intent);
                            }
                        });
                        break;
                    }else if(!day_list[i].equals(cdate)){
                        dayyy.setText("내용없음");
                        check_button.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

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

