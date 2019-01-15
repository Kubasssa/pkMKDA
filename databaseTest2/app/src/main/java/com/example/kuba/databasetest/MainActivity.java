package com.example.kuba.databasetest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
{

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    Fragment selectedFragment = null;

                    switch(item.getItemId())
                    {
                        case R.id.bottom_nav1:
                            selectedFragment = new FragmentEaten();


                            break;
                        case R.id.bottom_nav2:
                            selectedFragment = new FragmentStats();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.ma_fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.ma_bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.ma_fragment_container, new FragmentEaten()).commit();
    }
}
