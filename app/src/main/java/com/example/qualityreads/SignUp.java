package com.example.qualityreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.User;

public class SignUp extends AppCompatActivity {

    EditText firstNameEdt, lastNameEdt, addressEdt, phoneNumberEdt, emailEdtSignUp, passwordEdtSignUp;
    Button continueBtn, signInBtnSignUp;

    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstNameEdt = (EditText) findViewById(R.id.firstNameEdt);
        lastNameEdt = (EditText) findViewById(R.id.lastNameEdt);
        addressEdt = (EditText) findViewById(R.id.addressEdt);
        phoneNumberEdt = (EditText) findViewById(R.id.phoneNumberEdt);
        emailEdtSignUp = (EditText) findViewById(R.id.emailEdtSignUp);
        passwordEdtSignUp = (EditText) findViewById(R.id.passwordEdtSignUp);

        continueBtn = (Button) findViewById(R.id.continueBtn);
        signInBtnSignUp = (Button) findViewById(R.id.signInBtnSignUp);

        fireAuth = FirebaseAuth.getInstance();


        signInBtnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });

        // Sign Up Implementation-------------------------------------------------------------------------------------------
        continueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (!validFirstName() | !validLastName() | !validAddress() | !validPhoneNo() | !validEmail() | !validPassword()) {
                    return;
                }
                //Get data
                String firstName = firstNameEdt.getText().toString();
                String lastName = lastNameEdt.getText().toString();
                String Address = addressEdt.getText().toString();
                String PhoneNumber = phoneNumberEdt.getText().toString();
                String Email = emailEdtSignUp.getText().toString();
                String Password = passwordEdtSignUp.getText().toString();

                fireAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String uid = task.getResult().getUser().getUid();

                            User user = new User(uid, firstName, lastName, Address, PhoneNumber, Email, 0);

                            FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(SignUp.this, "Your are Registered Successfully!", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(SignUp.this, SignIn.class);
                                                startActivity(i);

                                            } else {
                                                Toast.makeText(SignUp.this, "Your registration is Unsuccessful!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });
        /*-----------------------------------------------------------------------------------------------------------------------*/


    }

    //Form Validations-----------------------------------------------------------------------------------------------------------
    private boolean validFirstName() {
        String val = firstNameEdt.getText().toString();

        if (val.isEmpty()) {
            firstNameEdt.setError("This field cannot be Empty");
            return false;
        } else {
            firstNameEdt.setError(null);
            return true;
        }
    }

    private boolean validLastName() {
        String val = lastNameEdt.getText().toString();

        if (val.isEmpty()) {
            lastNameEdt.setError("This field cannot be Empty");
            return false;
        } else {
            lastNameEdt.setError(null);
            return true;
        }
    }

    private boolean validAddress() {
        String val = addressEdt.getText().toString();

        if (val.isEmpty()) {
            addressEdt.setError("This field cannot be Empty");
            return false;
        } else {
            addressEdt.setError(null);
            return true;
        }
    }

    private boolean validPhoneNo() {
        String val = phoneNumberEdt.getText().toString();

        if (val.isEmpty()) {
            phoneNumberEdt.setError("This field cannot be Empty");
            return false;
        } else {
            phoneNumberEdt.setError(null);
            return true;
        }
    }

    private boolean validEmail() {
        String val = emailEdtSignUp.getText().toString();

        if (val.isEmpty()) {
            emailEdtSignUp.setError("This field cannot be Empty");
            return false;
        } else {
            emailEdtSignUp.setError(null);
            return true;
        }
    }

    private boolean validPassword() {
        String val = passwordEdtSignUp.getText().toString();

        if (val.isEmpty()) {
            passwordEdtSignUp.setError("This field cannot be Empty");
            return false;
        } else {
            passwordEdtSignUp.setError(null);
            return true;
        }
    }
    //------------------------------------------------------------------------------------------------------------------

}