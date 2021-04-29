package com.example.qualityreads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.User;

public class SignUp extends AppCompatActivity {

    EditText firstNameEdt, lastNameEdt, addressEdt, phoneNumberEdt, emailEdtSignUp, passwordEdtSignUp;
    Button continueBtn, signInBtnSignUp;

    FirebaseDatabase fireDB;
    DatabaseReference dbRef;

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



    }

    private boolean validFirstName(){
        String val = firstNameEdt.getText().toString();

        if(val.isEmpty()){
            firstNameEdt.setError("This field cannot be Empty");
            return false;
        }else{
            firstNameEdt.setError(null);
            return true;
        }
    }

    private boolean validLastName(){
        String val = lastNameEdt.getText().toString();

        if(val.isEmpty()){
            lastNameEdt.setError("This field cannot be Empty");
            return false;
        }else{
            lastNameEdt.setError(null);
            return true;
        }
    }

    private boolean validAddress(){
        String val = addressEdt.getText().toString();

        if(val.isEmpty()){
            addressEdt.setError("This field cannot be Empty");
            return false;
        }else{
            addressEdt.setError(null);
            return true;
        }
    }

    private boolean validPhoneNo(){
        String val = phoneNumberEdt.getText().toString();

        if(val.isEmpty()){
            phoneNumberEdt.setError("This field cannot be Empty");
            return false;
        }else{
            phoneNumberEdt.setError(null);
            return true;
        }
    }

    private boolean validEmail(){
        String val = emailEdtSignUp.getText().toString();

        if(val.isEmpty()){
            emailEdtSignUp.setError("This field cannot be Empty");
            return false;
        }else{
            emailEdtSignUp.setError(null);
            return true;
        }
    }

    private boolean validPassword(){
        String val = passwordEdtSignUp.getText().toString();

        if(val.isEmpty()){
            passwordEdtSignUp.setError("This field cannot be Empty");
            return false;
        }else{
            passwordEdtSignUp.setError(null);
            return true;
        }
    }


    public void signUp(View view){
        if(!validFirstName() | !validLastName() | !validAddress() | !validPhoneNo() | !validEmail() | !validPassword()){
            return;
        }

        // Sign In Implementation-------------------------------------------------------------------------------------------
        fireDB = FirebaseDatabase.getInstance();
        dbRef = fireDB.getReference("user");

        continueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Get data
                String firstName = firstNameEdt.getText().toString();
                String lastName = lastNameEdt.getText().toString();
                String Address = addressEdt.getText().toString();
                String PhoneNumber = phoneNumberEdt.getText().toString();
                String Email = emailEdtSignUp.getText().toString();
                String Password = passwordEdtSignUp.getText().toString();


                User user = new User(firstName, lastName, Address, PhoneNumber, Email, Password);

                dbRef.child(PhoneNumber).setValue(user);
                Toast.makeText(SignUp.this, "Your are Registered Successfully!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }

        });
        /*-----------------------------------------------------------------------------------------------------------------------*/
    }


    public void signIn(View view){
        // Navigate to Sign In--------------------------------------------------------------------------------------------------
        signInBtnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });
        /*-----------------------------------------------------------------------------------------------------------------------*/
    }

 }