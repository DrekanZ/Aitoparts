package com.example.aitoparts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class forgot_password2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);
    }


    public void btnMasukLUpaPassword(View view) {
        Intent baliklogin = new Intent(forgot_password2.this, Login.class);
        startActivity(baliklogin);
    }
}