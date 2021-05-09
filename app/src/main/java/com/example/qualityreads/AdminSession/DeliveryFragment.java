package com.example.qualityreads.AdminSession;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qualityreads.R;


public class DeliveryFragment extends Fragment {

    public DeliveryFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }


        View v1 = inflater.inflate(R.layout.fragment_delivery, container, false);

        Button gotoDriverList;
        gotoDriverList = v1.findViewById(R.id.assign);

        gotoDriverList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v2) {

        AdminSessions.fragmentManager.beginTransaction().replace(R.id.admin_fragment_container, new DriverListFragment(),null).addToBackStack(null).commit();


                //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //fragmentTransaction.replace(R.id.deliveryconstraint, new DriverListFragment()).addToBackStack(null).commit();
            }

        });
        //Inflate the layout for this fragment
        return v1;







/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_add_app, menu);
        MenuItem item = menu.findItem(R.id.action_search_menu_add_app);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) ((MenuItem) item).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Here is where we are going to implement the filter logic
                return true;
            }

        });
    }*/
    }
}