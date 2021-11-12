package org.starmine.diaryapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Mainmenu main_menu;
    Writemenu write_menu;
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_menu = new Mainmenu();
        write_menu = new Writemenu();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,main_menu).commit();

        bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                switch (Item.getItemId()){
                    case R.id.main:
                        Toast.makeText(getApplicationContext(),"첫번째",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.write:
                        Toast.makeText(getApplicationContext(),"두번째",Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });
    }
    public void onTabSelected(int position){
        if(position == 0){
            bottomNavigation.setSelectedItemId(R.id.main);
        }else if(position == 1){
            bottomNavigation.setSelectedItemId(R.id.write);
        }
    }
}