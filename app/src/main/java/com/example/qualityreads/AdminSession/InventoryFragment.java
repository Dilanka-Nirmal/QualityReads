package com.example.qualityreads.AdminSession;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.qualityreads.Book;
import com.example.qualityreads.BookAdapter;
import com.example.qualityreads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryFragment#} factory method to
 * create an instance of this fragment.
 */
public class InventoryFragment extends Fragment {

    RecyclerView bookRecycler;
    RecyclerView.Adapter bookAdapter;
    RecyclerView.LayoutManager bookLayout;
    ArrayList<Book> books = new ArrayList<>();
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    public InventoryFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container != null ){
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        firestore = FirebaseFirestore.getInstance();
        bookRecycler = view.findViewById(R.id.inventoryRV);
        bookRecycler.setHasFixedSize(true);
        bookLayout = new LinearLayoutManager(view.getContext());


        FloatingActionButton addbtn;
        addbtn = view.findViewById(R.id.addButton);
        progressBar = view.findViewById(R.id.progressBarIn);
        progressBar.setVisibility(View.VISIBLE);
        books.clear();
        firestore.collection("books").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().size() >0){
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            Book temp = new Book(doc.getId(), doc.getString("name"), doc.getString("isbn"), Double.parseDouble(doc.get("price").toString()), doc.getString("image"));
                            books.add(temp);
                        }
                        //todo set  user type here {true for admin} {false for customer}
                        bookAdapter = new BookAdapter(books, true);
                        bookRecycler.setLayoutManager(bookLayout);
                        bookRecycler.setAdapter(bookAdapter);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("inventory");
                fragmentTransaction.replace(R.id.admin_fragment_container, new fragment_addbook()).commit();
            }
        });

        return view;
    }

}