package com.example.qualityreads.AdminSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qualityreads.AdminSession.AdminAcct;
import com.example.qualityreads.R;
import com.example.qualityreads.UserSession.UserAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminSessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNav = findViewById(R.id.admin_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Admin acct appearance
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, new UserAccount() ).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.nav_adminAcct:
                            selectedFragment = new UserAccount();
                            break;

                        case R.id.nav_stockMng:
                            selectedFragment = new InventoryFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}