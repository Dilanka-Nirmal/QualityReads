package com.example.qualityreads.UserSession;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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


public class CustomerFragment extends Fragment {
    RecyclerView bookRecycler;
    RecyclerView.Adapter bookAdapter;
    RecyclerView.LayoutManager bookLayout;
    ArrayList<Book> books = new ArrayList<>();
    ProgressBar progressBar;
    FirebaseFirestore firestore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        firestore = FirebaseFirestore.getInstance();
        bookRecycler = view.findViewById(R.id.inventoryRV);
        bookRecycler.setHasFixedSize(true);
        bookLayout = new LinearLayoutManager(view.getContext());
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
                        bookAdapter = new BookAdapter(books, false);
                        bookRecycler.setLayoutManager(bookLayout);
                        bookRecycler.setAdapter(bookAdapter);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
