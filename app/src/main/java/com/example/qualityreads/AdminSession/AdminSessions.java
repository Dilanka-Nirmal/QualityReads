package com.example.qualityreads.AdminSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qualityreads.AdminSession.AdminAcct;
import com.example.qualityreads.R;
import com.example.qualityreads.UserSession.UserAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminSessions extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNav = findViewById(R.id.admin_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.admin_fragment_container)!=null){

            if(savedInstanceState!=null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DeliveryFragment deliveryFragment= new DeliveryFragment();
            fragmentTransaction.add(R.id.admin_fragment_container, deliveryFragment,null);
            fragmentTransaction.commit();
        }

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


                        case R.id.nav_deliveryMng:
                            selectedFragment = new DeliveryFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, selectedFragment).commit();

                    return true;
                }
            };
}