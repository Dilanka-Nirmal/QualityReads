package com.example.qualityreads.UserSession;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualityreads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_display_book#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_display_book extends Fragment {
    private TextView nameTV, isbnTV, categoryTV, paginationTV, publisherTV, publishDateTV, priceTV, descriptionTV;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private Button addToCart;

    ImageView bookImage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String bookID;
    private String mParam2;

    public fragment_display_book() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_display_book.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_display_book newInstance(String param1, String param2) {
        fragment_display_book fragment = new fragment_display_book();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_book, container, false);
        FloatingActionButton back = view.findViewById(R.id.backBtnDisBook);
        db = FirebaseFirestore.getInstance();
        nameTV = view.findViewById(R.id.nameView);
        isbnTV = view.findViewById(R.id.isbnView);
        categoryTV = view.findViewById(R.id.categoryView);
        paginationTV = view.findViewById(R.id.paginationView);
        publisherTV = view.findViewById(R.id.publisherView);
        publishDateTV = view.findViewById(R.id.publishDateView);
        priceTV = view.findViewById(R.id.priceView);
        descriptionTV = view.findViewById(R.id.descriptionView);
        progressBar = view.findViewById(R.id.progressBar2);
        bookImage = view.findViewById(R.id.imageView);
        addToCart = view.findViewById(R.id.buttonAddToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo handle add to cart
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        if (bookID != null) {
            progressBar.setVisibility(View.VISIBLE);
            db.collection("books").document(bookID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            nameTV.setText(doc.getString("name"));
                            isbnTV.setText(doc.getString("isbn"));
                            categoryTV.setText(doc.getString("category"));
                            paginationTV.setText(doc.getString("pagination"));
                            publisherTV.setText(doc.getString("publisher"));
                            publishDateTV.setText(doc.getString("publishDate"));
                            priceTV.setText(String.format("%.2f", doc.get("price")));
                            descriptionTV.setText(doc.getString("description"));
                            Picasso.get().load(doc.getString("image")).into(bookImage);
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(getContext(), "Please click on a book", Toast.LENGTH_LONG).show();
        }

        return view;
    }
}
