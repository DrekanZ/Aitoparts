package com.example.aitoparts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Forgot_Password_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);
    }


    public void Pindah(View view) {
        Intent intentpagesatunya = new Intent(Forgot_Password_1.this, forgot_password2.class);
        startActivity(intentpagesatunya);
    }

    public void lupaPasswordBackButton (View view) {
        Intent pulanglogin = new Intent(Forgot_Password_1.this, Login.class);
        startActivity(pulanglogin);
    }


}