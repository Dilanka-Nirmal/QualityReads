package com.example.qualityreads.UserSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qualityreads.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserSessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //@This Should be change------------------------------------
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, //new Account()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_account:
                    selectedFragment = new Account();
                    break;

            //This nav used for testing payment confirmation
                case R.id.nav_cart:
                    selectedFragment = new PaymentConfirmation();
                    break;

             //For other Fragments
                /*case R.id.nav_account:
                    selectedFragment = new Account();
                    break;*/
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}