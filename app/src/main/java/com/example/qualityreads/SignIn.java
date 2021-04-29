package com.example.qualityreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import models.User;

public class SignIn extends AppCompatActivity {

    EditText phoneEdt, passwordEdt;
    Button signInBtn, forgotPasswordBtn, signUpBtn;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Check Database Connection
        //Toast.makeText(SignIn.this,"Firebase connection Success!",Toast.LENGTH_LONG).show();

        phoneEdt = (EditText) findViewById(R.id.phoneEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        signInBtn = (Button) findViewById(R.id.signInBtn);
        forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);


        //SignUp Button Activate
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        //Forgot Password Button Activate
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(i);
            }
        });

    }

    private boolean validPhone(){
        String val = phoneEdt.getText().toString();

        if(val.isEmpty()){
            phoneEdt.setError("This field cannot be Empty");
            return false;
        }else{
            phoneEdt.setError(null);
            return true;
        }
    }

    private boolean validPassword(){
        String val = passwordEdt.getText().toString();

        if(val.isEmpty()){
            passwordEdt.setError("This field cannot be Empty");
            return false;
        }else{
            passwordEdt.setError(null);
            return true;
        }
    }


    //Login Validation------------------------------------------------------------------------------------------------------------------
    String phoneNo;
    String password;
    public void validLogIn(View view) {

        if(!validPhone() | !validPassword()){
            return;
        }

        phoneNo = phoneEdt.getText().toString();
        password = passwordEdt.getText().toString();

        Query checkUser = FirebaseDatabase.getInstance().getReference("user").orderByChild("phoneNumberEdt").equalTo(phoneNo);
        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String sysPassword = snapshot.child(phoneNo).child("passwordEdtSignUp").getValue(String.class);

                    if (sysPassword.equals(password)) {
                        passwordEdt.setError(null);
                        Toast.makeText(SignIn.this, "Login Successful!", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(SignIn.this, "Something went wrong, Try again!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SignIn.this, "User doesn't exist!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignIn.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /*-----------------------------------------------------------------------------------------------------------------------*/

}