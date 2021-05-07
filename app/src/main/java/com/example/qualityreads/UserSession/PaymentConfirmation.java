package com.example.qualityreads.UserSession;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillId;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qualityreads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    DatabaseReference dbRef, dbRef2;

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

        payBtn = view.findViewById(R.id.payBtn);

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid()).child("card");
        dbRef2 = FirebaseDatabase.getInstance().getReference("Bank");


        //Card details appear on payment confirmation UI-----------------------------------------------------------------------------------------
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
        //-----------------------------------------------------------------------------------------------------------------------------------

        //Payment validation-----------------------------------------------------------------------------------------------------------------
        payBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!validCvv() | !validTotal() ) {
                    return;
                }

                String cardNumber = cardNumberPay.getText().toString();
                String cvv = cvvPay.getText().toString();
                Integer total = Integer.parseInt(totalVal.getText().toString());

                //dbRef2.child(cardNumber).child("cardNumber").addValueEventListener(new ValueEventListener() {
                dbRef2.child(cardNumber).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            String cvvNo = snapshot.child("cvv").getValue(String.class);

                            if (cvvNo.equals(cvv)) {

                                Integer totalVal = snapshot.child("amount").getValue(Integer.class);

                                if (totalVal.equals(total) || totalVal > total) {

                                    Toast.makeText(getActivity(), "Payment Successfully!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), "Your account balance is not insufficient for complete this payment", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Invalid CVV number, Please check again...", Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(getActivity(), "K"+cvvNo, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Your payment not valid, Please contact the bank!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                //Toast.makeText(getActivity(), "K", Toast.LENGTH_SHORT).show();
            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------------
        return view;
    }

    //Form Validations------------------------------------------------------------------------------------------------------------------------
    private boolean validCvv() {
        String val = cvvPay.getText().toString();

        if (val.isEmpty()) {
            cvvPay.setError("This field cannot be Empty");
            return false;
        } else if (val.length() > 3) {
            cvvPay.setError("Invalid CVV number");
            return false;
        } else if (val.length() < 3) {
            cvvPay.setError("Invalid CVV number");
            return false;
        } else {
            cvvPay.setError(null);
            return true;
        }
    }

    private boolean validTotal() {
        String val = totalVal.getText().toString();

        if (val.isEmpty()) {
            totalVal.setError("This field cannot be Empty");
            return false;
        } else{
            totalVal.setError(null);
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------

}

