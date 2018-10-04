package com.kzhang1.feelsbook_kzhang1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Home home = new Home();
                    fragmentManager.beginTransaction().replace(R.id.fragment, home).commit();
                    return true;

                case R.id.navigation_history:
                    History history = new History();
                    fragmentManager.beginTransaction().replace(R.id.fragment, history).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        add navigation event listener
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        set home fragment to default
        FragmentManager fragmentManager = getSupportFragmentManager();
        setTitle("kzhang1-FeelsBook");
        Home home = new Home();
        fragmentManager.beginTransaction().replace(R.id.fragment, home).commit();
    }

}
