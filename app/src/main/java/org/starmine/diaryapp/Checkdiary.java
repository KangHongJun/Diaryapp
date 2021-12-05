package org.starmine.diaryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class Checkdiary extends AppCompatActivity {
    Spinner category;
    TextView year,month,day;
    static EditText setTitle, diaryContent;
    Button update_button,back_button;
    static String Cdate, Scate;
    static int cateN = 0;
    String Gday,Gtitle,Gcate,Gcontent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_diary);
        Intent Check_intent = getIntent();
        Gday = Check_intent.getStringExtra("day");
        Gtitle = Check_intent.getStringExtra("title");
        Gcate = Check_intent.getStringExtra("cate");
        Gcontent = Check_intent.getStringExtra("content");

        setTitle = findViewById(R.id.TitleText);
        diaryContent = findViewById(R.id.DiaryText);
        category = findViewById(R.id.Scategory);

        year = findViewById(R.id.Year);
        month = findViewById(R.id.Month);
        day = findViewById(R.id.Day);

        year.setText(Gday.substring(0,4)+"년");
        month.setText(Gday.substring(4,6)+"월");
        day.setText(Gday.substring(6,8)+"일");

        setTitle.setText(Gtitle);
        diaryContent.setText(Gcontent);

        final String[] cate = {"일상", "꿈일기", "잡생각", "기타"};
        if(Gcate==cate[0]){
            category.setSelection(0);
        }else if (Gcate==cate[1]){
            category.setSelection(1);
        }else if (Gcate==cate[2]) {
            category.setSelection(2);
        }else if (Gcate==cate[3]) {
            category.setSelection(3);
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cate);
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

        update_button = findViewById(R.id.Update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper Helper;
                SQLiteDatabase sqlDB;
                Helper = new DBHelper(Checkdiary.this,"DiaryDB.db",null,1);
                sqlDB = Helper.getWritableDatabase();
                Helper.onCreate(sqlDB);

                String Title = setTitle.getText().toString();
                String Content = diaryContent.getText().toString();
                sqlDB.execSQL("update diary set setTitle='"+Title+"'where Cdate = "+Gday+"");
                sqlDB.execSQL("update diary set diaryContent='"+Content+"'where Cdate = "+Gday+"");
                sqlDB.close();

                setResult(0);
                finish();
            }
        });
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(1);
                finish();
            }
        });
    }
}
