package com.example.qualityreads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread((Runnable)()->{
           try{
               sleep(2000);
               Intent i = new Intent(SplashScreen.this,SignIn.class);
               startActivity(i);
               finish();
           }catch (InterruptedException e){
               e.printStackTrace();
           }
        });
        thread.start();
    }
}