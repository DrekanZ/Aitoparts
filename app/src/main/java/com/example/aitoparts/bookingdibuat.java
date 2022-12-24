    package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

    public class bookingdibuat extends AppCompatActivity {


        // menu back
        ImageView backtomainmenu;

        //ngambil data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdibuat);

        // back
        backtomainmenu = (ImageView) findViewById(R.id.backBokingbaru);
        backtomainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainBooking = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backToMainBooking);
            }
        });


        //pasing data





    }
}