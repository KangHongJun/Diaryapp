package org.starmine.diaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    static TextView mainTitle;
    static int check=0;
    static String day_st,title_st,cate_st,content_st,year,month,day;
    static String[] day_list,title_list,cate_list,content_list;
    public static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_calendar = findViewById(R.id.Main_calendar);
        main_calendar.setSelectedDate(CalendarDay.today());
        mainTitle = findViewById(R.id.titleText);
        check_button = findViewById(R.id.check_Button);
        check_button.setVisibility(View.INVISIBLE);
        mainTitle.setVisibility(View.INVISIBLE);
        UpdateDB();

        main_calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int cyear = widget.getSelectedDate().getYear();
                int cmonth = widget.getSelectedDate().getMonth();
                int cday = widget.getSelectedDate().getDay();
                String cdate = String.valueOf(cyear+""+cmonth+""+cday);

                if (day_list != null) {
                    for(int i=0;i<day_list.length;i++){
                        if (day_list[i].equals(cdate)){
                            check = i;
                            mainTitle.setVisibility(View.VISIBLE);
                            mainTitle.setText(title_list[i]);
                            check_button.setVisibility(View.VISIBLE);
                            check_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(),Checkdiary.class);
                                    intent.putExtra("day",day_list[check]);
                                    intent.putExtra("title",title_list[check]);
                                    intent.putExtra("cate",cate_list[check]);
                                    intent.putExtra("content",content_list[check]);
                                    startActivityForResult(intent,REQUEST_CODE);
                                }
                            });
                            break;
                        }else if(!day_list[i].equals(cdate)){
                            check_button.setVisibility(View.INVISIBLE);
                            mainTitle.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

        write_button = findViewById(R.id.Write_button);
        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Writemenu.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


    }
    public void UpdateDB(){
        DBHelper Helper;
        SQLiteDatabase sqlDB;
        Helper = new DBHelper(MainActivity.this,"DiaryDB.db",null,1);
        sqlDB = Helper.getReadableDatabase();
        Helper.onCreate(sqlDB);
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from diary",null);


        if(cursor != null && cursor.getCount() != 0){
            while(cursor.moveToNext()){
                day_st += cursor.getString(0) + " ";
                title_st +=cursor.getString(1) + " ";
                cate_st +=cursor.getString(2) + " ";
                content_st +=cursor.getString(3) + " ";

                day_list = day_st.split(" ");
                title_list = title_st.split(" ");
                cate_list = cate_st.split(" ");
                content_list = content_st.split(" ");


                for(int i=1;i<day_list.length;i++){
                    if(day_list[i].length() == 7){
                        year = day_list[i].substring(0,4);
                        month = day_list[i].substring(4,6);
                        day = day_list[i].substring(6,7);

                        int nyear = Integer.parseInt(year);
                        int nmonth = Integer.parseInt(month);
                        int nday = Integer.parseInt(day);

                        main_calendar.addDecorator(new CalendarDecorator(Color.RED,
                                Collections.singleton(CalendarDay.from(nyear,nmonth,nday))));

                    }else{
                        year = day_list[i].substring(0,4);
                        month = day_list[i].substring(4,6);
                        day = day_list[i].substring(6,8);

                        int nyear = Integer.parseInt(year);
                        int nmonth = Integer.parseInt(month);
                        int nday = Integer.parseInt(day);

                        main_calendar.addDecorator(new CalendarDecorator(Color.RED,
                                Collections.singleton(CalendarDay.from(nyear,nmonth,nday))));
                    }

                }
            }
            cursor.close();
            sqlDB.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                UpdateDB();
                break;
            default:
                break;
        }

    }
}

