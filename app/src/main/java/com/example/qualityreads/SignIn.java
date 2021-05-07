package com.example.qualityreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qualityreads.AdminSession.AdminSessions;
import com.example.qualityreads.UserSession.UserSessions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText emailEdt, passwordEdt;
    Button signInBtn, forgotPasswordBtn, signUpBtn;

    FirebaseAuth fireAuth;
    FirebaseDatabase fireDB = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        signInBtn = (Button) findViewById(R.id.signInBtn);
        forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);

        fireAuth = FirebaseAuth.getInstance();


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

        //Login Validation------------------------------------------------------------------------------------------------------------------
        //Sign In Button Activate
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email;
                String password;

                if (!validEmail() | !validPassword()) {
                    return;
                }

                email = emailEdt.getText().toString();
                password = passwordEdt.getText().toString();

                fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = task.getResult().getUser().getUid();

                            fireDB.getReference().child("user").child(uid).child("userType").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    int userType = snapshot.getValue(Integer.class);

                                    if (userType == 1) {
                                        Toast.makeText(SignIn.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignIn.this, AdminSessions.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(SignIn.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SignIn.this, UserSessions.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignIn.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        /*-----------------------------------------------------------------------------------------------------------------------*/

    }

    //Form Validations [signInBtn]---------------------------------------------------------------------------------------------

    boolean validEmail() {
        String val = emailEdt.getText().toString();

        if (val.isEmpty()) {
            emailEdt.setError("This field cannot be Empty");
            return false;
        } else {
            emailEdt.setError(null);
            return true;
        }
    }

    boolean validPassword() {
        String val = passwordEdt.getText().toString();

        if (val.isEmpty()) {
            passwordEdt.setError("This field cannot be Empty");
            return false;
        } else {
            passwordEdt.setError(null);
            return true;
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------

}