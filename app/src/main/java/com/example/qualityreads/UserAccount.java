package com.example.qualityreads;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import models.User;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserAccount extends Fragment {

    EditText firstNameUpdt, lastNameUpdt, addressUpdt, phoneNumberUpdt, emailUpdt, uidUpdt;
    Button updateProfileBtn;

    FirebaseAuth fireAuth;
    FirebaseUser fireUser;
    DatabaseReference dbRef;

    public UserAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        firstNameUpdt = view.findViewById(R.id.firstNameUpdt);
        lastNameUpdt = view.findViewById(R.id.lastNameUpdt);
        addressUpdt = view.findViewById(R.id.addressUpdt);
        phoneNumberUpdt = view.findViewById(R.id.phoneNumberUpdt);
        emailUpdt = view.findViewById(R.id.emailUpdt);
        uidUpdt = view.findViewById(R.id.uidUpdt);

        updateProfileBtn = view.findViewById(R.id.updateProfileBtn);

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(fireUser.getUid());

        //View account details--------------------------------------------------------------------------------------
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    firstNameUpdt.setText(snapshot.child("firstNameEdt").getValue().toString());
                    lastNameUpdt.setText(snapshot.child("lastNameEdt").getValue().toString());
                    addressUpdt.setText(snapshot.child("addressEdt").getValue().toString());
                    phoneNumberUpdt.setText(snapshot.child("phoneNumberEdt").getValue().toString());
                    emailUpdt.setText(snapshot.child("emailEdtSignUp").getValue().toString());
                    uidUpdt.setText(snapshot.child("uid").getValue().toString());
                }else{
                    Toast.makeText(getActivity(), "No source to display!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //-----------------------------------------------------------------------------------------------------------

        //Update profile details-------------------------------------------------------------------------------------
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            User user = new User();
                            user.setFirstNameEdt(firstNameUpdt.getText().toString());
                            user.setLastNameEdt(lastNameUpdt.getText().toString());
                            user.setAddressEdt(addressUpdt.getText().toString());
                            user.setPhoneNumberEdt(phoneNumberUpdt.getText().toString());
                            user.setEmailEdtSignUp(emailUpdt.getText().toString());
                            user.setUID(uidUpdt.getText().toString());

                            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(getActivity(), "Profile data has been changed, Successfully!", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(getActivity(), "Your profile has not been changed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(getActivity(), "No source to update!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //--------------------------------------------------------------------------------------------------------------


        return view;
    }


}