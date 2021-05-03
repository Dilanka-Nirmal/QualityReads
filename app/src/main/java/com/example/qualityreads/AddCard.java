package com.example.qualityreads;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.Card;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddCard extends Fragment {

    EditText cardHolderNameEdt, cardNumberEdt, expiryDateEdt;

    Button updateCardBtn, deleteCardBtn;

    FirebaseAuth fireAuth;
    FirebaseUser fireUser;
    DatabaseReference dbRef;
    Card Card;

    public AddCard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);

        cardHolderNameEdt = view.findViewById(R.id.cardHolderNameEdt);
        cardNumberEdt = view.findViewById(R.id.cardNumberEdt);
        expiryDateEdt = view.findViewById(R.id.expiryDateEdt);

        updateCardBtn = view.findViewById(R.id.updateCardBtn);
        deleteCardBtn = view.findViewById(R.id.deleteCardBtn);

        Card = new Card();

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();

        //dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid());
        dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid()).child("card");

        //Add card details---------------------------------------------------------------------------------------------------------------------
        updateCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validCardHolderName() | !validCardNumber() | !validExpiryDate()) {
                    return;
                }

                dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid());

                Card.setCardHolderNameEdt(cardHolderNameEdt.getText().toString());
                Card.setCardNumberEdt(cardNumberEdt.getText().toString());
                Card.setExpiryDateEdt(expiryDateEdt.getText().toString());

                dbRef.child("card").setValue(Card).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getActivity(), "Card details added, Successfully!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //---------------------------------------------------------------------------------------------------------------------------------------


        //Delete card----------------------------------------------------------------------------------------------------------------------------

        deleteCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid()).child("card");
                            dbRef.removeValue();
                            //clearControls();
                            Toast.makeText(getActivity(), "Your card details remove from account!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No source to delete...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        //---------------------------------------------------------------------------------------------------------------------------------------


        //Card view -----------------------------------------------------------------------------------------------------------------------------

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    cardHolderNameEdt.setText(snapshot.child("cardHolderNameEdt").getValue().toString());
                    cardNumberEdt.setText(snapshot.child("cardNumberEdt").getValue().toString());
                    expiryDateEdt.setText(snapshot.child("expiryDateEdt").getValue().toString());

                } else {
                    Toast.makeText(getActivity(), "You can enter your card details...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------------------

        return view;
    }


    private boolean validCardHolderName() {
        String val = cardHolderNameEdt.getText().toString();

        if (val.isEmpty()) {
            cardHolderNameEdt.setError("This field cannot be Empty");
            return false;
        } else {
            cardHolderNameEdt.setError(null);
            return true;
        }
    }

    private boolean validCardNumber() {
        String val = cardNumberEdt.getText().toString();

        if (val.isEmpty()) {
            cardNumberEdt.setError("This field cannot be Empty");
            return false;
        } else {
            cardNumberEdt.setError(null);
            return true;
        }
    }

    private boolean validExpiryDate() {
        String val = expiryDateEdt.getText().toString();

        if (val.isEmpty()) {
            expiryDateEdt.setError("This field cannot be Empty");
            return false;
        } else {
            expiryDateEdt.setError(null);
            return true;
        }
    }
}

