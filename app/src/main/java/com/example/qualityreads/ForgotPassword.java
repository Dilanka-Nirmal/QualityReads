package com.example.qualityreads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    EditText passwordForgotPasswordEdt, newPasswordEdt, confirmPasswordEdt;
    Button continueBtnForgotPwd, signInBtnForgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        passwordForgotPasswordEdt = (EditText) findViewById(R.id.passwordForgotPasswordEdt);
        newPasswordEdt = (EditText) findViewById(R.id.newPasswordEdt);
        confirmPasswordEdt = (EditText) findViewById(R.id.confirmPasswordEdt);

        continueBtnForgotPwd = (Button) findViewById(R.id.continueBtnForgotPwd);
        signInBtnForgotPwd = (Button) findViewById(R.id.signInBtnForgotPwd);


        //SignIn Button Activate
        signInBtnForgotPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword.this, SignIn.class);
                startActivity(i);
            }
        });

        //Continue Button Activate
        continueBtnForgotPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(ForgotPassword.this, SignIn.class);
                startActivity(i);*/
            }
        });

    }


}