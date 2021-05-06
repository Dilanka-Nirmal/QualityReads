package com.example.qualityreads.UserSession;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qualityreads.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PaymentConfirmation extends Fragment {

    EditText totalVal, cardHolderNamePay, cardNumberPay, expiryDatePay, cvvPay;
    Button payBtn;

    FirebaseAuth fireAuth;
    FirebaseUser fireUser;
    DatabaseReference dbRef;

    public PaymentConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_confirmation, container, false);

        totalVal = view.findViewById(R.id.totalVal);
        cardHolderNamePay = view.findViewById(R.id.cardHolderNamePay);
        cardNumberPay = view.findViewById(R.id.cardNumberPay);
        expiryDatePay = view.findViewById(R.id.expiryDatePay);

        cvvPay = view.findViewById(R.id.cvvPay);

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid()).child("card");

        //Card details appear on payment confirmation UI-----------------------------------------------------------------------------------------------------------------------------
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    cardHolderNamePay.setText(snapshot.child("cardHolderNameEdt").getValue().toString());
                    cardNumberPay.setText(snapshot.child("cardNumberEdt").getValue().toString());
                    expiryDatePay.setText(snapshot.child("expiryDateEdt").getValue().toString());

                } else {
                    Toast.makeText(getActivity(), "Note : You can store your card details under the account", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------------------

        return view;
    }

    //Form Validations------------------------------------------------------------------------------------------------------------------------
    private boolean validCvv() {
        String val = cvvPay.getText().toString();

        if (val.isEmpty()) {
            cvvPay.setError("This field cannot be Empty");
            return false;
        } else if(val.length() > 3){
            cvvPay.setError("Invalid CVV number");
            return false;
        }else if(val.length() < 3){
            cvvPay.setError("Invalid CVV number");
            return false;
        } else {
            cvvPay.setError(null);
            return true;
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------
}