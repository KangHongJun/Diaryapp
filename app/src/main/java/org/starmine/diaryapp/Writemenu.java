package org.starmine.diaryapp;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Writemenu extends AppCompatActivity {
    TextView year, month, day;
    EditText setTitle, diaryContent;
    int selectYear, selectMonth, selectDay;
    Button picker_button, save_button,back_button, finish_button;
    Spinner category;
    CalendarView calendar;
    static String Cdate, Scate;
    static int cateN = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_write);

        setTitle = findViewById(R.id.SetTitle);
        diaryContent = findViewById(R.id.Diary_contents);

        year = findViewById(R.id.Year);
        month = findViewById(R.id.Month);
        day = findViewById(R.id.Day);

        calendar = findViewById(R.id.Calender);
        calendar.setVisibility(View.INVISIBLE);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat MonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        SimpleDateFormat DayFormat = new SimpleDateFormat("dd", Locale.KOREA);


        year.setText(YearFormat.format(date) + "년");
        month.setText(MonthFormat.format(date) + "월");
        day.setText(DayFormat.format(date) + "일");
        Cdate = (YearFormat.format(date)+MonthFormat.format(date)+DayFormat.format(date)).toString();


        final String[] cate = {"일상", "꿈일기", "잡생각", "기타"};

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cate);
        category = findViewById(R.id.spinner_category);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cateN = position;
                Scate = cate[cateN];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        finish_button = findViewById(R.id.Finish_button);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year.setText(Integer.toString(selectYear) + "년");
                month.setText(Integer.toString(selectMonth+1) + "월");
                day.setText(Integer.toString(selectDay) + "일");
                Cdate = (Integer.toString(selectYear)+Integer.toString(selectMonth+1)+Integer.toString(selectDay)).toString();

                Toast.makeText(getApplicationContext(),
                        Integer.toString(selectYear)+Integer.toString(selectMonth+1)+Integer.toString(selectDay),
                        Toast.LENGTH_SHORT).show();

                calendar.setVisibility(View.INVISIBLE);
                finish_button.setVisibility(View.INVISIBLE);
            }
        });

        //DB

        save_button = findViewById(R.id.Save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper Helper;
                SQLiteDatabase sqlDB;
                Helper = new DBHelper(Writemenu.this,"DiaryDB.db",null,1);
                sqlDB = Helper.getWritableDatabase();
                Helper.onCreate(sqlDB);

                //cdate를 년 월 일 형식으로로
               Toast.makeText(getApplicationContext(), Cdate+setTitle.getText()+Scate+diaryContent.getText() ,Toast.LENGTH_LONG).show();
                sqlDB.execSQL("insert into diary values( '"+Cdate +"', '"+setTitle.getText().toString()+"', '"+ Scate+"', '"+diaryContent.getText().toString()+"')");

                sqlDB.close();
                finish();
            }
        });
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
